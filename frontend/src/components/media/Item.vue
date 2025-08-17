<template>
  <div class="item-container" :onClick="onItemPressed">
    <div class="item-container-border">
      <div class="item-container-border-body">
        <div class="item-container-header">
          <h1 class="item-container-title">
            {{ props.title }}
          </h1>
        </div>
        <div class="item-container-body">
          <div class="item-container-details">
            <span class="item-container-rating"> {{ props.rating }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<script setup>
import { useRouter } from 'vue-router'

const props = defineProps(['title', 'rating', 'clickable']);
const router = useRouter();

const onItemPressed = () => {
  if (!props.clickable) { return; }
  router.push(`/media/item/${props.title}`)
};

</script>


<style scoped>
.item-container {
  height: 270px;
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
  box-shadow: 0 0 80px rgba(255, 255, 255, 0);
}

.item-container-border:hover {
  transform: scale(1) rotateX(6deg) rotateY(6deg);
  box-shadow: 0 0 80px rgba(255, 255, 255, 0.1);
}

.item-container-border-body {
  display: flex;
  flex-direction: column;
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
</style>
