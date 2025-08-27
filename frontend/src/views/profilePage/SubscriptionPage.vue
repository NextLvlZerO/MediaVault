<template>
  <div class="background">
    <div class="subscription-container">
      <div class="subscription-header">
        <h1 class="subscription-title g-text">
          Choose your plan
        </h1>
      </div>
      <div class="subscription-body">
        <SubscriptionItem v-for="(item, index) in data" :id="item?.subscriptionTypeId" :title="item?.name"
          :price="item?.price" :details="item?.details" />
      </div>
    </div>
  </div>
</template>

<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { useRoute, useRouter } from 'vue-router';
import { useToast } from 'vue-toast-notification';
import { ref, onMounted } from 'vue';

const route = useRoute();
const router = useRouter();
const toast = useToast();

const data = ref([]);

const freeTierDetails = [
  { eigenschaft: "Zugriff auf Basis-Inhalte", available: true },
  { eigenschaft: "HD-Streaming", available: false },
  { eigenschaft: "Offline-Modus", available: false },
  { eigenschaft: "Werbefrei", available: false },
  { eigenschaft: "Exklusive Premium-Inhalte", available: false }
];

const premiumTierDetails = [
  { eigenschaft: "Zugriff auf Basis-Inhalte", available: true },
  { eigenschaft: "HD-Streaming", available: true },
  { eigenschaft: "Offline-Modus", available: true },
  { eigenschaft: "Werbefrei", available: true },
  { eigenschaft: "Exklusive Premium-Inhalte", available: false }
];

const deluxeTierDetails = [
  { eigenschaft: "Zugriff auf Basis-Inhalte", available: true },
  { eigenschaft: "HD-Streaming", available: true },
  { eigenschaft: "Offline-Modus", available: true },
  { eigenschaft: "Werbefrei", available: true },
  { eigenschaft: "Exklusive Premium-Inhalte", available: true }
];


const getSubscriptionData = () => {
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
    .then(response => data.value = response.map(item => ({
      ...item,
      details: [
        { property: `Lend quantity: ${item?.quantity}`, available: true },
        { property: `Price reduction: ${((1 - item?.priceReduction) * 100).toFixed(0)}%`, available: true },
      ]
    })))
    .catch(error => {
      console.error(error);
      toast.error(error.message);
    });
};

onMounted(() => {
  getSubscriptionData();
})

</script>


<style scoped>
.background {
  display: flex;
  width: 100vw;
  flex: 1;
  background: radial-gradient(ellipse 600px 500px at 50% 500px, #ffffff22, transparent);
}

.subscription-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1280px;
  min-width: 800px;
  width: 100%;
  min-height: max(100vh, 1000px);
  margin: 0 auto;
  padding: 0rem 2rem;
  font-weight: normal;
}

.subscription-header {
  margin-top: 5rem;
}

.subscription-body {
  display: flex;
  justify-content: center;
  gap: 2rem;
  width: 100%;
  margin-top: 5rem;
}

.subscription-title {
  font-size: 3rem !important;
  font-weight: 500;
}

.five-rem-distance {
  margin-top: 2rem;
  margin-bottom: 2rem;
}

.body-header {
  color: #fff;
  font-family: var(--font-family);
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 1rem;
}
</style>
