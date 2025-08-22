<template>
  <div class="friends-c-container">
    <div class="left">
      <div class="friends-header">
        <h1 class="friends-title g-text">
          Friends
        </h1>
        <button class="g-button-p add-button" :onclick="onAddButtonClicked">
          {{ addState ? 'back' : 'add' }}
        </button>
      </div>
      <div v-if="!addState" class="friends-body">
        <SingleFriend username="Lars" @userClickedEmit="onUserChange" :active="activeUser === 'Lars'" />
        <SingleFriend username="Julian Hensel" @userClickedEmit="onUserChange"
          :active="activeUser === 'Julian Hensel'" />
        <SingleFriend username="Fortniteenjoyer123" @userClickedEmit="onUserChange"
          :active="activeUser === 'Fortniteenjoyer123'" />
        <SingleFriend username="NextLvlZer0" @userClickedEmit="onUserChange" :active="activeUser === 'NextLvlZer0'" />
      </div>
      <div v-if="addState" class="add-body">
        <div class="mb-3">
          <label class="form-label g-text-a">Username</label>
          <input class="form-control input-field" placeholder="search" />
        </div>
        <div class="add-results">
          <SingleFriend username="Lars" @userClickedEmit="onUserChange" :active="activeUser === 'Lars'" />
          <SingleFriend username="Larss" @userClickedEmit="onUserChange" :active="activeUser ===
            'Larss'" />
          <SingleFriend username="Larsss" @userClickedEmit="onUserChange" :active="activeUser ===
            'Larsss'" />
        </div>
      </div>
    </div>
    <div class="right">
      <div class="right-messages">
        <UserMessage type="received" />
        <UserMessage type="sent" />
        <UserMessage />
        <UserMessage />
      </div>
      <div class="message-input">
        <textarea class="message-input-area" placeholder="End-to-end-unencrypted" v-model="message"
          @input="resizeTextarea" ref="textarea" />
        <button class="send-button g-button-p">
          <i class="bi bi-send-fill" style="color: #000; font-size: 18px; margin: 0px" />
        </button>
      </div>
    </div>
  </div>
</template>


<script setup>

import { ref } from 'vue'

const activeUser = ref('Lars');
const message = ref("");
const textarea = ref(null);

const addState = ref(false);


const resizeTextarea = () => {
  const el = textarea.value;
  if (!el) return;
  el.style.height = "auto";
  el.style.height = Math.min(el.scrollHeight + 10, 300) + "px";
};


const onAddButtonClicked = () => {
  activeUser.value = null;
  addState.value = !addState.value;
};


const onUserChange = (username) => {


  if (addState.value) {

    return;
  }
  activeUser.value = username;
};




</script>


<style scoped>
.friends-c-container {
  display: flex;
  height: 80vh;
  width: 100%;
  max-height: 1000px;
  border: 1px solid #ffffff44;
  border-radius: 10px;
}

.friends-header {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.friends-title {
  display: flex;
  font-size: 1.6rem !important;
  font-weight: 500;
}

.friends-body {
  display: flex;
  flex-direction: column;
  gap: .2rem;
}

.left {
  display: flex;
  flex-direction: column;
  flex: 1 0 30%;
  gap: .2rem;
  padding: 2rem;
  overflow-y: auto;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  background-color: var(--color-background-lighter);
}

.right {
  display: flex;
  flex-direction: column;
  flex: 1 0 70%;
  padding: 1.3rem;
}

.right-messages {
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 1rem;
  overflow-y: auto;
  padding: .7rem;
}

.message-input {
  display: flex;
  margin-top: .5rem;
}

.message-input-area {
  background-color: var(--color-background);
  color: #fff;
  border: 1px solid #ffffff44;
  border-radius: 10px;
  width: 100%;
  padding: 1rem;
  resize: none;
  outline: none;
}

.message-input-area:focus {
  border-color: var(--color-primary);
}

.send-button {
  height: min-content;
  width: min-content;
  margin-left: 1rem;
  align-self: flex-end;
  padding: 0.4rem 0.7rem !important;
}

.add-button {
  transform: translateY(-4px);
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

.add-results {
  display: flex;
  flex-direction: column;
  gap: .2rem;
}
</style>
