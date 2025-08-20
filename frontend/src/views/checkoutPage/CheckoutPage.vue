<template>
  <div class="background">
    <div class="lend-container">
      <div class="lend-header">
        <h1 class="lend-title g-text">
          Checkout
        </h1>
      </div>
      <div class="lend-body">
        <p class="g-text"> {{ route.params.item }}</p>
        <button class="go-back-button" :onclick="onBackClick">Back</button>
        <button class="lend-button" :onclick="onPurchaseClick">Confirm purchase</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const onBackClick = () => {
  router.back();
};


const onPurchaseClick = () => {
  router.push('/media/item/lend/purchase-confirmation');
};


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
  align-items: center;
}

.lend-title {
  font-size: 2rem !important;
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
</style>
