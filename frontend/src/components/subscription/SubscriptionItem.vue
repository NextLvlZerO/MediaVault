<template>
  <div class="subscription-item-container">
    <div class="header">
      <h2 class="header-title">
        {{ props.title }}
      </h2>
    </div>
    <div class="body">
      <div class="body-items">
        <div class="body-item" v-for="(item, index) in props.details">
          <p class="body-item-text g-text-a" :key="index"> {{ item.eigenschaft }}</p>
          <i :class="item?.available ? 'bi bi-check' : 'bi bi-x'" style="color: #fff; font-size: 20px;" />
        </div>
      </div>
      <div class="body-footer">
        <p class="item-price"> {{ props?.price ? props.price.toFixed(2) : 0 }} € </p>
        <button class="g-button-p item-continue-button"> Continue </button>
      </div>
    </div>
  </div>
</template>


<script setup>
import { defineProps } from 'vue';

const props = defineProps(['title', 'details', 'price', 'type']);

</script>


<style scoped>
.subscription-item-container {
  display: flex;
  flex-direction: column;
  height: 550px;
  width: 380px;
  background-color: #ffffff08;
  border: 1px solid #ffffff44;
  border-radius: 10px;
}

.header {
  display: flex;
  width: 100%;
  justify-content: center;
}

.body {
  display: flex;
  flex: 1;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-top: 1rem;
  padding-bottom: 2rem;
}

.body-footer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100%;
}

.header-title {
  display: flex;
  font-family: var(--font-family-main);
  font-size: 1.6rem;
  font-weight: 400;
  color: #fff;
  margin-top: 2rem;
}

.body-item {
  display: flex;
  align-items: center;
  margin-left: 2rem;
}

.body-item-text {
  transform: translateY(6px);
  margin-right: .2rem;
}

.item-price {
  color: #fff;
  font-family: var(--font-family-main);
  font-size: 1.6rem;
  font-weight: 600;
  align-self: center;
  margin-bottom: 1.5rem;
}

.item-continue-button {
  align-self: center;
  width: max-content !important;
}
</style>
