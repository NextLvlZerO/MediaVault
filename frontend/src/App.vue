<template>
  <div class="app-component">
    <Header v-if="checkHeaderVisibility()" @filterChangedEmit="updateFilter" />
    <RouterView v-slot="{ Component }">
      <component :is="Component" :filter="filter" />
    </RouterView>
    <Footer v-if="checkFooterVisibility()" />
  </div>
</template>


<script setup>
import { RouterLink, RouterView, useRoute, useRouter } from 'vue-router'
import Header from './components/navigation/Header.vue'
import Footer from './components/navigation/Footer.vue'
import { ref, watch, onMounted } from 'vue'
import { getCookie } from './components/utility/cookies.js';

const Route = useRoute();
const Router = useRouter();

const filter = ref(null);



const updateFilter = (newVal) => {
  filter.value = newVal;
};

const checkHeaderVisibility = () => {
  return !(Route.path.startsWith('/media/item/lend')) && !(Route.path.startsWith('/login'));
};

const checkFooterVisibility = () => {
  return !(Route.path.startsWith('/login'));
};

const checkForLogin = () => {
  if (!getCookie('username') || !getCookie('userId')) {
    Router.push('/login');
  }
};


watch(() => Route.path, newVal => {
  checkForLogin();
})

onMounted(() => {
  checkForLogin();
});


</script>


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

.g-text-d {
  color: #000;
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

.g-text-d-a {
  color: #000000aa;
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

.g-button-p {
  display: flex;
  background-color: var(--color-primary);
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.g-button-p:hover {
  background-color: #dddddd;
}

.g-button-s {
  display: flex;
  background-color: #dddddd;
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.g-button-s:hover {
  background-color: var(--color-primary);
}

.g-button-b {
  display: flex;
  background-color: transparent;
  color: #ffffffdd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px solid #ffffff44;
  border-radius: 100px;
  padding: 8px 13px;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.g-button-b:hover {
  background-color: #ffffff22;
}
</style>
