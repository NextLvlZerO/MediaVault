<template>
  <div class="single-friend-container" :style="{
    backgroundColor: props?.active ? '#ffffff08' :
      '#ffffff00'
  }" @click="onUserClick">
    <div class="left">
      <i class="bi bi-person-fill" style="color: #fff; font-size: 18px" :style="{
        color: props.active ? 'var(--color-primary)' :
          '#ffffffaa'
      }" />
      <p class="username-text g-text-a" :style="{
        color: props.active ? 'var(--color-primary)' :
          '#ffffffaa'
      }"> {{ props.username
      }} </p>
    </div>
    <div class="request-buttons" v-if="props?.request">
      <button class="g-button-b request-button-item" @click="() => requestButtonPressed(false)">
        <i class="bi bi-x button-icon" />
      </button>
      <button class="g-button-b request-button-item" @click="() => requestButtonPressed(true)">
        <i class="bi bi-check button-icon" />
      </button>
    </div>
    <div class="delete-button" v-if="props?.friend">
      <button class="g-button-b delete-button-item" @click.stop="deleteButtonPressed">
        <i class="bi bi-x button-icon" />
      </button>
    </div>
  </div>
</template>

<script setup>

const apiUrl = import.meta.env.VITE_API_URL;

import { ref, watch, defineProps, defineEmits } from 'vue';
import { getCookie } from '../utility/cookies.js';
import { useToast } from 'vue-toast-notification';

const emits = defineEmits(['userClickedEmit', 'userRequestsClickedEmit', 'reloadEmit']);
const props = defineProps(['username', 'userId', 'active', 'request', 'friend']);
const id = getCookie('userId');
const toast = useToast();

const active = ref(false);


watch(active, (newVal, oldVal) => {

});


const onUserClick = () => {
  emits('userClickedEmit', props?.username, props?.userId);
};


const requestButtonPressed = (value) => {
  emits('userRequestsClickedEmit', value, props?.userId);
};


const deleteButtonPressed = () => {
  fetch(`${apiUrl}/user/${id}/remove-user/${props?.userId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(result => {
      if (!result.ok) {
        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
          .catch((error) => {
            console.error(error);
            toast.error(error.message);
          })
      }

      toast.success('Successfully removed user');
      emits('reloadEmit');
    })
    .catch(error => {
      console.error(error);
    })
};

</script>



<style scoped>
.single-friend-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: .5rem;
  padding-left: 1rem;
  cursor: pointer;
  transition: 0.15s ease-in-out all;
  border-radius: 10px;
}

.single-friend-container:hover {
  background-color: #ffffff08 !important;
}

.left {
  display: flex;
  align-items: center;
}

.username-text {
  font-size: 1.1rem !important;
  transform: translateY(9px);
  margin-left: .5rem;
}

.request-buttons {
  display: flex;
  gap: .5rem;
  border-radius: 10px;
}

.request-button-item {
  display: flex;
  position: relative;
  height: 35px;
  width: 35px;
}

.delete-button-item {
  display: flex;
  position: relative;
  height: 35px;
  width: 35px;
  z-index: 100;
}

.button-icon {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
}
</style>
