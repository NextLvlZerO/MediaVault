<template>
  <div class="friends-c-container">
    <div class="left">
      <div class="friends-header">
        <h1 class="friends-title g-text">
          Friends
        </h1>
      </div>
      <SingleFriend username="Lars" @userClickedEmit="onUserChange" :active="activeUser === 'Lars'" />
      <SingleFriend username="Julian Hensel" @userClickedEmit="onUserChange" :active="activeUser === 'Julian Hensel'" />
      <SingleFriend username="Fortniteenjoyer123" @userClickedEmit="onUserChange"
        :active="activeUser === 'Fortniteenjoyer123'" />
      <SingleFriend username="NextLvlZer0" @userClickedEmit="onUserChange" :active="activeUser === 'NextLvlZer0'" />
    </div>
    <div class="right">
      <div class="right-messages">
        <UserMessage type="received" />
        <UserMessage type="sent" />
        <UserMessage />
        <UserMessage />
      </div>
      <div class="message-input">
        <textarea class="message-input-area" placeholder="message" v-model="message" @input="resizeTextarea"
          ref="textarea" />
        <button class="send-button g-button-p">
          <i class="bi bi-send-fill" style="color: #000; font-size: 18px" />
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


const resizeTextarea = () => {
  const el = textarea.value;
  if (!el) return;
  el.style.height = "auto";
  el.style.height = Math.min(el.scrollHeight + 10, 300) + "px";
};


const onUserChange = (username) => {
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
  justify-content: center;
  margin-bottom: 1rem;
}

.friends-title {
  display: flex;
  font-size: 1.6rem !important;
  font-weight: 500;
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
</style>
