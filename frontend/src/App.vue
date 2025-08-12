<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import Main from './components/Main.vue'


</script>

<template>
  <div class="app-component">
    <Header />
    <RouterView />
    <Footer />
  </div>
</template>


<style scoped>
.app-component {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  margin: 0;
  background-color: var(--color-background);
}
</style>
