<template>
  <div class="background">
    <div class="lend-container">
      <div class="lend-header">
        <h1 class="lend-title g-text">
          Checkout
        </h1>
      </div>
      <div class="lend-body">
        <div class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Item: </p>
          <p class="g-text"> {{ routeType == 'subscription' ? `${itemData?.name} Tier` : itemData?.title }}</p>
        </div>
        <HSeperator v-if="routeType == 'item' || routeType == 'expand'" class="five-rem-distance" />
        <div v-if="routeType == 'item' || routeType == 'expand'" class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Price per day: </p>
          <p class="g-text"> {{ itemData?.price ? `${itemData.price.toFixed(2)}€` : '0€' }}</p>
        </div>
        <div v-if="routeType == 'item' || routeType == 'expand'" class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Discount: </p>
          <p class="g-text"> {{ subscriptionData ? `${((1 -
            subscriptionData?.type?.priceReduction) * 100).toFixed(0)}%` : 'None' }}</p>
          <p class="g-text-a" style="margin-left: .5rem;"> {{ `(${(totalPriceWithoutDiscount -
            totalPrice).toFixed(2)}€ saved)` }} </p>
        </div>
        <div v-if="routeType == 'item' || routeType == 'expand'" class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Lending span: </p>
          <input class="days-input" v-model="lendingDays" @input="onInput" />
          <p class="g-text"> days </p>
        </div>
        <HSeperator class="five-rem-distance" />
        <div class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Total: </p>
          <p class="g-text"> {{ itemData?.price ? `${totalPrice.toFixed(2)}€` : '0€' }}</p>
        </div>
        <div class="lend-buttons">
          <button class="go-back-button" @click="onBackClick">Back</button>
          <button class="lend-button" @click="onPurchaseClick">Confirm purchase</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { useRoute, useRouter } from 'vue-router';
import { getCookie } from '../../components/utility/cookies.js'
import { useToast } from 'vue-toast-notification';
import { ref, onMounted, watch } from 'vue';
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

const route = useRoute();
const router = useRouter();
const userId = getCookie('userId');
const toast = useToast();

const routeType = route.params?.type;
const routeItem = route.params?.item;


const itemData = ref(null);
const subscriptionData = ref(null);
const lendingDays = ref(7);
const totalPrice = ref(0);
const totalPriceWithoutDiscount = ref(0);




watch(itemData, (newValue, oldValue) => {
  getTotalPrice();
});

watch(subscriptionData, (newValue, oldValue) => {
  getTotalPrice();
});


const onInput = (event) => {
  lendingDays.value = event.target.value.replace(/\D/g, "");
  getTotalPrice();
}


const onBackClick = () => {
  router.back();
};


const onPurchaseClick = async () => {
  if (await handleItemPurchase()) {
    router.push('/media/item/lend/purchase-confirmation');
  }
};


const getTotalPrice = () => {
  if (!itemData.value?.price) { return; }
  const discount = subscriptionData.value?.type?.priceReduction ?
    subscriptionData.value.type.priceReduction : 1;

  if (routeType == 'subscription') {
    totalPrice.value = itemData.value.price;
    totalPriceWithoutDiscount.value = itemData.value.price;
  }
  else {
    totalPrice.value = itemData.value.price * lendingDays.value * discount;
    totalPriceWithoutDiscount.value = itemData.value.price * lendingDays.value;
  }
}



const handleItemPurchase = async () => {
  try {
    let result = null;
    if (routeType == 'item') {
      result = await handleMediaPurchase();
      console.log(result);
    }

    else if (routeType == 'expand') {
      result = await handleMediaExpansion();
    }

    else if (routeType == 'subscription') {
      result = await handleSubscriptionPurchase();
    }

    if (result && result?.text()) {
      handlePayment(result.text());

      let message = ''
      if (routeType == 'item') message = 'Successfully lent media';
      else if (routeType == 'expand') message = 'Successfully expanded item';
      else if (routeType == 'subscription') message = 'Successfully subscribed';

      toast.success(message);
      return true;
    }
    return false;

  }
  catch (error) {
    console.error(error);
    toast.error(error.message);
    return false;
  }
}



const handleMediaPurchase = async () => {
  const result = await fetch(`${apiUrl}/user/${userId}/lend/media/${routeItem}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      days: lendingDays.value
    }),
    credentials: 'include'
  });

  if (!result.ok) {
    const response = await result.json();
    const errorMessage = response?.error;
    throw new Error(errorMessage);
  }
  return result;
};


const handleMediaExpansion = async () => {
  const result = await fetch(`${apiUrl}/user/${userId}/expand/media/${routeItem}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      days: lendingDays.value
    }),
    credentials: 'include'
  });

  if (!result.ok) {
    const response = await result.json();
    const errorMessage = response?.error;
    throw new Error(errorMessage);
  }
  return result;
};


const handleSubscriptionPurchase = async () => {
  const result = await fetch(`${apiUrl}/subscription/update/${itemData.value?.name}/user/${userId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  });

  if (!result.ok) {
    const response = await result.json();
    const errorMessage = response?.error;
    throw new Error(errorMessage);
  }
  return result;
};



const handlePayment = (sessionId) => {
  const client = new Client({
    webSocketFactory: () => new SockJS("http://localhost:8080/ws/payment"),
    reconnectDelay: 5000,
  });

  client.onConnect = () => {
    console.log("STOMP connected");
    client.subscribe("/topic/payment-status/" + sessionId, (message) => {
      console.log("Payment update:", message.body);
    });
  };

  client.onStompError = (frame) => {
    console.error("Broker error: ", frame.headers["message"]);
    console.error("Details: ", frame.body);
  };

  client.activate();
};



const getCheckoutData = () => {

  getCheckoutItem(routeType, routeItem);
  getSubscription();
}


const getCheckoutItem = (type, id) => {

  if (routeType == 'item' || routeType == 'expand') {

    fetch(`${apiUrl}/media/item/${id}`)
      .then(result => {
        if (!result.ok) {
          return result.json()
            .then(response => {
              const errorMessage = response?.error;
              throw new Error(errorMessage);
            })
        }
        return result.json();
      })
      .then(response => itemData.value = response)
      .catch(error => {
        toast.error(error.message)
        console.error(error);
      })
  }

  else if (routeType == 'subscription') {
    fetch(`${apiUrl}/subscription/types`)
      .then(result => {
        if (!result.ok) {
          return result.json()
            .then(response => {
              const errorMessage = response?.error;
              throw new Error(errorMessage);
            })
        }
        return result.json();
      })
      .then(response => itemData.value = response.filter(item => item.subscriptionTypeId ==
        routeItem)[0])
      .catch(error => {
        toast.error(error.message)
        console.error(error);
      })
  }
}


const getSubscription = () => {
  fetch(`${apiUrl}/subscription/user/${userId}`)
    .then(result => {
      if (!result.ok) {
        if (result.status === 404) {
          subscriptionData.value = null;
          return;
        }

        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return result.json()
    })
    .then(response => subscriptionData.value = response)
    .catch(error => {
      console.error(error);
      toast.error(error.message);
    })
}


onMounted(() => {
  getCheckoutData();
});


</script>


<style scoped>
.background {
  display: flex;
  width: 100vw;
  flex: 1;
}

.lend-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1280px;
  width: 100%;
  height: 100vh;
  margin: 0 auto;
  padding: 0rem 2rem;
  font-weight: normal;
}

.lend-header {
  margin-top: 8rem;
  margin-bottom: 3rem;
}

.lend-body {
  display: flex;
  flex-direction: column;
  margin-top: 2rem;
  width: 50%;
}

.body-item {
  display: flex;
}

.lend-title {
  font-size: 3rem !important;
  font-weight: 500;
}

.lend-button {
  background-color: var(--color-primary);
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.lend-button:hover {
  background-color: #dddddd;
}

.go-back-button {
  background-color: #dddddd;
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
  margin-right: .5rem;
}

.go-back-button:hover {
  background-color: var(--color-primary);
}

.five-rem-distance {
  margin-bottom: .6rem;
}

.lend-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
}

.days-input {
  background-color: transparent;
  color: #fff;
  border: 1px solid #ffffff44;
  border-radius: 8px;
  width: 80px;
  margin-right: .8rem;
  transform: translateY(-5px);
  padding-left: .5rem;
}
</style>
