<template>
  <div class="filter-input-component">
    <p class="filter-title g-text-a"> {{ props.data?.title ? props.data.title : 'Undefined' }}</p>
    <div v-if="props.data?.type === 'tag'" class="filter-tags">
      <MediaFilterTag v-for="(item, index) in data?.filter" :data="item" @onTagClickedEmit="updateFilterOptionsEmit" />
    </div>
    <div v-if="props.data?.type === 'input'" class="filter-input">
      <div class="mb-3">
        <input class="form-control input-field" v-model="inputValue" type="text" inputmode="numeric" @input="onInput" />
      </div>
    </div>
  </div>
</template>



<script setup>
import { defineProps, defineEmits, ref } from 'vue';

const props = defineProps(['data']);
const emits = defineEmits(['onTagClickedEmit', 'onInputChangedEmit']);

const inputValue = ref(null);

const onInput = (event) => {
  inputValue.value = event.target.value.replace(/\D/g, "");
  emits('onInputChangedEmit', { componentId: props?.data?.componentId, filter: event.target.value });
};


const updateFilterOptionsEmit = (parameters) => {
  emits('onTagClickedEmit', {
    ...parameters, componentId: props?.data?.componentId, onlyOne:
      props?.data?.onlyOne
  });
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

.input-field {
  position: relative;
  background-color: var(--background-color-ligher);
  border: 1px solid #ffffff44;
  color: #fff;
}


.input-field:focus {
  background-color: var(--background-color-ligher);
  color: #fff;
}
</style>
