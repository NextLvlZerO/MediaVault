<template>
  <div class="item-container" :onClick="onItemPressed">
    <div class="item-container-border">
      <div class="item-container-border-body">
        <!-- <div class="item-container-header">
          <h1 class="item-container-title">
            {{ props.title }}
          </h1>
        </div>
        <div class="item-container-body">
          <div class="item-container-details">
            <span class="item-container-rating"> {{ props.rating }}</span>
          </div>
        </div>
-->
        <img v-if="props.poster" style="border-radius: 4px; width: 198px; height: 298px"
          :src="`${baseUrl}${props?.poster}`" alt="Poster">
        <div v-else class="spinner-border text-secondary" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
        <div v-if="expireDate" class="lend-item-body">
          <button v-if="expand" class="g-button-s" @click="onExpandPressed"> expand </button>
          <p class="expire-text"> expires in {{ getTimespan() }} days </p>
        </div>
      </div>
    </div>
  </div>
</template>



<script setup>
import { useRouter } from 'vue-router'
import { defineProps, defineEmits } from 'vue';

const props = defineProps(['title', 'rating', 'poster', 'clickable', 'id', 'expireDate', 'expand']);
const router = useRouter();

const baseUrl = 'https://image.tmdb.org/t/p/w500';

const onItemPressed = () => {
  if (!props.clickable) { return; }
  router.push(`/media/item/${props.id}`)
};


const onExpandPressed = () => {
  router.push(`/checkout/expand/${props?.id}`)
};


const getTimespan = () => {
  const currentDate = new Date()
  const expireDate = new Date(props?.expireDate);

  const difference = expireDate - currentDate;
  return Math.ceil(difference / (1000 * 60 * 60 * 24));
}

</script>


<style scoped>
.item-container {
  height: 300px;
  width: 200px;
  perspective: 800px;
}

.item-container:hover {
  cursor: pointer;
}

.item-container-border {
  position: relative;
  height: 100%;
  width: 100%;
  padding: 1px;
  border-radius: 5px;
  background: linear-gradient(-45deg, #9eadba, #4b5359);
  transform-origin: center center -1cm;
  transform: scale(1) rotateY(0deg);
  transform-style: preserve-3d;
  transition: all ease-in-out 0.15s;
  box-shadow: 0 0 80px rgba(0, 0, 0, 0.4);
}

.item-container-border:hover {
  transform: scale(1) rotateX(6deg) rotateY(6deg);
  box-shadow: 0 0 80px rgba(255, 255, 255, 0.1);
}

.item-container-border-body {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  background-color: var(--color-background-lighter);
  border-radius: 4px;
}

.item-container-header {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.item-container-body {
  display: flex;
  flex: 1;
  flex-direction: column-reverse;
}

.item-container-title {
  color: #fff;
  width: 85%;
  font-family: var(--font-family-main);
  font-weight: 500;
  margin-top: 1rem;
  font-size: 1.3rem;
  text-align: center;
}

.item-container-details {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.item-container-rating {
  color: var(--color-primary);
  font-family: var(--font-family-main);
  font-size: 1.1rem;
  margin-bottom: 1rem;
}

.lend-item-body {
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  bottom: 0;
  width: 100%;
  height: 30%;
}

.lend-item-bottom {
  background-color: #000;
}

.expire-text {
  font-family: var(--font-family-main);
  font-size: 1rem;
  color: #000;
  font-weight: 600;
  background-color: var(--color-primary);
  text-align: center;
  width: 100%;
}
</style>
