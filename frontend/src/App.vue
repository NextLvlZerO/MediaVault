<script setup>
import { RouterLink, RouterView, useRoute } from 'vue-router'
import Header from './components/navigation/Header.vue'
import Footer from './components/navigation/Footer.vue'

const Route = useRoute();

const checkHeaderVisibility = () => {
  return !(Route.path.startsWith('/media/item/lend'));
};


</script>

<template>
  <div class="app-component">
    <Header v-if="checkHeaderVisibility()" />
    <RouterView />
    <Footer />
  </div>
</template>


<style>
.app-component {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  margin: 0;
  background-color: var(--color-background);
}

.g-text {
  color: #fff;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.2rem;
}

.g-text-a {
  color: #ffffffaa;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.2rem;
}


.g-text-b {
  color: #fff;
  font-family: var(--font-family-main);
  font-size: 1.1rem;
  font-weight: 400;
  line-height: 1.2rem;
}

.g-text-b-a {
  color: #ffffffaa;
  font-family: var(--font-family-main);
  font-size: 1.1rem;
  font-weight: 400;
  line-height: 1.2rem;
}
</style>
