<template>
  <div class="information-component">
    <div class="information-container-left">
      <div class="information-container-title">
        <h1 class="information-container-title-media text">MEDIA</h1>
        <h1 class="information-container-title-vault text">VAULT</h1>
      </div>
    </div>
    <div class="information-container-right">
      <span class="information-container-right-text">your gateway to unforgettable entertainment
        experiences.
      </span>
      <button class="get-started-button" @click="scrollToBody">Get started</button>
    </div>
  </div>
</template>



<script setup>

import { motion } from 'motion-v';

const scrollToBody = () => {
  document.getElementById('body').scrollIntoView({ behavior: 'smooth' });
};

</script>



<style scoped>
.information-component {
  display: flex;
  gap: 5rem;
  justify-content: space-between;
  width: 100%;
  height: 300px;
  margin-top: 8rem;
  margin-bottom: 8rem;
}

.information-container-left {
  display: flex;
  align-items: center;
  border-right: 1px solid var(--color-primary);
  padding-right: 5rem;
}

.information-container-right {
  display: flex;
  flex-direction: column;
  flex: 1;
  justify-content: center;
}

.information-container-right-text {
  color: #ffffffdd;
  opacity: 1;
  font-family: var(--font-family-main);
  font-weight: 300;
  font-size: 1.8rem;
  line-height: 2rem;
}

.information-container-title {
  display: flex;
  flex-direction: column;
  margin-left: 3rem;
  margin-bottom: 0;
  height: 15rem;
}

.information-container-title-media {
  font-weight: 400;
  color: var(--color-primary);
  margin-bottom: -50px;
  line-height: 10rem;
}

.information-container-title-vault {
  font-weight: 400;
  background: linear-gradient(to top, #87aad4, white);
  background-clip: text;
  color: transparent;
  margin-left: 5rem;
  line-height: 10rem;
}

.text {
  font-size: 8rem;
  font-family: var(--font-family-title);
}

.get-started-button {
  background-color: var(--color-primary);
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  margin-top: 1.6rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.get-started-button:hover {
  background-color: #dddddd;
}
</style>
