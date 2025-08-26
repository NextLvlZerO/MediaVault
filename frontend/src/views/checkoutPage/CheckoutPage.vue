<template>
  <div class="background">
    <div class="lend-container">
      <div class="lend-header">
        <h1 class="lend-title g-text">
          Checkout
        </h1>
      </div>
      <div class="lend-body">
        <HSeperator class="five-rem-distance" />
        <div class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Item: </p>
          <p class="g-text"> {{ itemData?.title }}</p>
        </div>
        <HSeperator class="five-rem-distance" />
        <div class="body-item">
          <p class="g-text-a" style="margin-right: .5rem;"> Discount: </p>
          <p class="g-text"> {{ subscriptionData ? '' : 'None' }}</p>

        </div>
        <button class="go-back-button" :onclick="onBackClick">Back</button>
        <button class="lend-button" :onclick="onPurchaseClick">Confirm purchase</button>
      </div>
    </div>
  </div>
</template>

<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { useRoute, useRouter } from 'vue-router';
import { getCookie } from '../../components/utility/cookies.js'
import { useToast } from 'vue-toast-notification';
import { ref, onMounted } from 'vue';

const route = useRoute();
const router = useRouter();
const userId = getCookie('userId');
const toast = useToast();

const routeType = route.params?.type;
const routeItem = route.params?.item;

const itemData = ref(null);
const subscriptionData = ref(null);


const onBackClick = () => {
  router.back();
};


const onPurchaseClick = async () => {
  if (await handleItemPurchase()) {
    router.push('/media/item/lend/purchase-confirmation');
  }
};



const handleItemPurchase = async () => {

  try {
    const result = await fetch(`${apiUrl}/user/${userId}/lend/media/${routeItem}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        days: 100
      }),
      credentials: 'include'
    });

    if (!result.ok) {
      return result.json()
        .then(response => {
          const errorMessage = response?.error;
          throw new Error(errorMessage);
        })
    }
    else {
      toast.success('successfully lent media');
      return true;
    }
  }
  catch (error) {
    console.error(error);
    toast.error(error.message);
    return false;
  }
};


const getCheckoutData = () => {

  getCheckoutItem(routeType, routeItem);
  getSubscription();
}


const getCheckoutItem = (type, id) => {

  if (routeType == 'item') {

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
}


const getSubscription = () => {
  fetch(`${apiUrl}/user/${userId}`)
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
  margin-top: 5rem;
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
  margin-right: 2rem;
}

.go-back-button:hover {
  background-color: var(--color-primary);
}

.five-rem-distance {
  margin-bottom: .6rem;
}
</style>
