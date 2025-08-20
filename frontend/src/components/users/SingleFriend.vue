<template>
  <div class="single-friend-container" :style="{ backgroundColor: props.active ? '#ffffff08' : none }"
    :onclick="onUserClick">
    <i class="bi bi-person-fill" style="color: #fff; font-size: 18px" :style="{
      color: props.active ? 'var(--color-primary)' :
        '#ffffffaa'
    }" />
    <p class="username-text g-text-a" :style="{
      color: props.active ? 'var(--color-primary)' :
        none
    }"> {{ props.username
      }} </p>
  </div>
</template>

<script setup>

import { ref, watch, defineProps, defineEmits } from 'vue';

const emits = defineEmits(['userClickedEmit']);
const props = defineProps(['username', 'active']);

const active = ref(false);


watch(active, (newVal, oldVal) => {

});


const onUserClick = () => {
  emits('userClickedEmit', props?.username);
  console.log('active');
}

</script>



<style scoped>
.single-friend-container {
  display: flex;
  align-items: center;
  width: 100%;
  padding: .5rem;
  padding-left: 1rem;
  cursor: pointer;
  transition: 0.15s ease-in-out all;
  border-radius: 10px;
}

.single-friend-container:hover {
  background-color: #ffffff08;
}

.username-text {
  font-size: 1.1rem !important;
  transform: translateY(9px);
  margin-left: .5rem;
}
</style>
