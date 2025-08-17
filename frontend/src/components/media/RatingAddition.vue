<template>
  <div class="add-background">
    <div class="add-container">
      <form class="input-forms">
        <h1 class="add-title"> Add review</h1>
        <div class="mb-3">
          <label for="exampleInputEmail1" class="form-label g-text">Title</label>
          <input class="form-control input-field">
        </div>
        <div class="mb-3 content-input-container">
          <label for="exampleInputPassword1" class="form-label g-text">Content</label>
          <textarea class="form-control input-field content-input" />
        </div>
        <div class="add-buttons">
          <button class="cancel-button" :onclick="onCancelButtonPressed"> cancel </button>
          <button class="confirm-button" :onclick="onCancelButtonPressed"> confirm </button>
        </div>
      </form>
    </div>
  </div>
</template>


<script setup>

import { defineEmits } from 'vue';
const emit = defineEmits(['goBackEvent']);

const onCancelButtonPressed = () => {
  emit('goBackEvent');
};

</script>


<style scoped>
.add-background {
  z-index: 1000;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 110vh;
  transform: translateY(-100px);
  background-color: #00000088;
  backdrop-filter: blur(10px);
}

.add-container {
  width: 700px;
  height: 450px;
  max-width: 70%;
  max-height: 70%;
  border: 1px solid #ffffff44;
  border-radius: 10px;
  background-color: #ffffff11;
}

.input-forms {
  display: flex;
  flex: 1;
  height: 100%;
  flex-direction: column;
  padding: 1.5rem;
}

.input-field {
  background-color: var(--background-color-ligher);
  border: 1px solid #ffffff44;
  color: #fff;
}

.input-field:focus {
  background-color: var(--background-color-ligher);
  color: #fff;
}

.add-title {
  font-family: var(--font-family-main);
  font-size: 2rem;
  font-weight: 600;
  color: #fff;
  margin-bottom: 1.2rem;
}

.add-buttons {
  display: flex;
  align-self: flex-end;
  justify-content: flex-end;
  gap: 0.5rem;
}

.cancel-button {
  display: flex;
  background-color: #dddddd;
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  padding: 8px 13px;
  border-radius: 100px;
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.confirm-button {
  display: flex;
  background-color: var(--color-primary);
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.confirm-button:hover {
  background-color: #dddddd;
}

.cancel-button:hover {
  background-color: var(--color-primary);
}

.content-input {
  display: flex;
  flex: 1;
}


.content-input-container {
  display: flex;
  flex-direction: column;
  flex: 1;
}
</style>
