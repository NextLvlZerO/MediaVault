<template>
  <div class="media-list-container">
    <Item v-for="(item, index) in data" :key="index" :title="item?.title" :rating="item?.rating" :poster="item?.poster"
      :clickable="props?.clickable" :id="item?.id" />
  </div>
</template>



<script setup>

const props = defineProps(['data', 'clickable']);

</script>



<style scoped>
p {
  font-size: 5rem;
  color: #fff;
}

.media-list-container {
  width: 100%;
  display: grid;
  gap: 25px;
  justify-content: center;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
}
</style>
