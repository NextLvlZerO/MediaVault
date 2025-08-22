<template>
  <div class="filter-input-component">
    <p class="filter-title g-text-a"> {{ props.data?.title ? props.data.title : 'Undefined' }}</p>
    <div class="filter-tags">
      <MediaFilterTag v-for="(item, index) in data?.filter" :data="item" @onTagClickedEmit="updateFilterOptionsEmit" />
    </div>
  </div>
</template>



<script setup>
import { defineProps, defineEmits, ref } from 'vue';

const props = defineProps(['data']);
const emits = defineEmits(['onTagClickedEmit']);
const filter = ref([
  {
    id: 0,
    title: 'Action',
    status: true
  },
  {
    id: 1,
    title: 'Horror',
    status: false
  },
  {
    id: 2,
    title: 'Fantasy',
    status: false
  },
  {
    id: 3,
    title: 'Romance',
    status: true
  },
  {
    id: 4,
    title: 'Arthouse',
    status: false
  },
  {
    id: 5,
    title: 'Drama',
    status: false
  },
]);


const updateFilterOptionsEmit = (parameters) => {
  emits('onTagClickedEmit', { ...parameters, componentId: props?.data?.componentId });
};



</script>



<style scoped>
.filter-input-component {
  position: relative;
  display: flex;
  flex-direction: column;
  padding: .5rem;
}

.filter-tags {
  display: flex;
  flex-wrap: wrap;
  gap: .5rem;
}
</style>
