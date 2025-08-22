<template>
  <div class="filter-tag-component" :class="props.data?.status ? 'filter-active' : ''" @click="onTagClicked">
    <p class="g-text filter-text" :class="props.data?.status ? 'filter-text-active' : ''"> {{ props.data?.title }} </p>
  </div>
</template>



<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps(['data']);
const emits = defineEmits(['onTagClickedEmit']);


// method that handles tag click with status = !status

const onTagClicked = () => {
  emits('onTagClickedEmit', { id: props.data?.id, status: !props.data?.status });
};

</script>



<style scoped>
.filter-tag-component {
  position: relative;
  border-radius: 100px;
  border: 1px solid #ffffff44;
  cursor: pointer;
  transition: .2s ease-in-out all;
}

.filter-tag-component:hover {
  background-color: #ffffff22;
}

.filter-active {
  background-color: var(--color-primary);
  border-color: var(--color-primary);
}

.filter-active:hover {
  background-color: #ffc58e;
}


.filter-text {
  margin-left: .6rem;
  margin-right: .6rem;
  transform: translateY(7px);
}

.filter-text-active {
  color: #000;
}
</style>
