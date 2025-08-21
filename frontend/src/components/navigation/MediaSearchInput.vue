<template>
  <div class="search-input-component">
    <input ref="inputRef" class="search-component" placeholder="Search" type="text" @focus="onInputFocus"
      @blur="active = false" />
    <div class="search-results" v-if="active" @mousedown.prevent>
      <MediaSearchResultItem v-for="(item, index) in data" :key="index" :title="item?.title" :id="item?.id"
        @onChildPressedEvent="unfocusInput" />
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue';

const active = ref(false);
const inputRef = ref(null);

const data = [{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
}
]


// when focused update list

const onInputFocus = () => {
  active.value = true;
}


// get media query data from backend

const getMediaQueryData = () => {
  return;
};


const unfocusInput = () => {
  active.value = false;
  inputRef.value?.blur();
};

</script>


<style scoped>
.search-input-component {
  position: relative;
  display: flex;
}

.search-component {
  background-color: var(--color-background);
  color: #fff;
  border-radius: 100px;
  padding-left: 15px;
  border: 1px solid #ffffff44;
  outline: none;
}

.search-component:focus {
  border: 1px solid var(--color-primary);
}

.search-results {
  z-index: 10;
  position: absolute;
  bottom: 1;
  transform: translateY(50px);
  border: solid 1px #ffffff44;
  border-radius: 10px;
  background-color: var(--color-background);
  width: 100%;
  padding: .5rem;
}
</style>
