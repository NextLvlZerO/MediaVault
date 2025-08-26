<template>
  <div class="media-list-container">
    <Item v-for="(item, index) in data" :key="index" :title="item?.title" :rating="item?.rating" :poster="item?.poster"
      :clickable="props?.clickable" :id="item?.id" :expireDate="item?.endDate" :expand="props?.expand" />
  </div>
</template>



<script setup>

import { defineProps } from 'vue';

const props = defineProps(['data', 'clickable', 'expand']);

</script>



<style scoped>
p {
  font-size: 5rem;
  color: #fff;
}

.media-list-container {
  width: 100%;
  display: grid;
  gap: 35px;
  justify-content: center;
  grid-template-columns: repeat(auto-fit, 200px);
}
</style>
