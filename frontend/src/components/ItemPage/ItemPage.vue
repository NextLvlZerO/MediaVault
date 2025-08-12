<template>
  <div class="item-page-container">
    <div class="item-page-container-head">
      <div class="item-page-container-left">
        <h1 class="item-page-container-title"> {{ route.params.title }}</h1>
        <span class="item-page-container-details">
          {{ details }}
        </span>
      </div>
      <div class="item-page-container-right">
        <button class="item-page-container-lend-button"> lend now </button>
      </div>
    </div>
  </div>

</template>



<script setup>
import { useRoute } from 'vue-router'
import { onMounted } from 'vue'

const details = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
const route = useRoute();




onMounted(() => {
  window.scrollTo({ top: -100, left: 0 })
})
</script>


<style scoped>
.item-page-container {
  display: flex;
  flex-direction: column;
  max-width: 1280px;
  min-height: 100vh;
  width: 100%;
  margin: 0 auto;
  padding: 0rem 2rem;
  font-weight: normal;
}

.item-page-container-head {
  display: flex;
  justify-content: space-between;
  gap: 5rem;
  margin-top: 5rem;
}

.item-page-container-left {
  display: flex;
  flex: 1 0 50%;
  flex-direction: column;
  border-right: 1px solid var(--color-primary);
  padding-right: 5rem;
}

.item-page-container-right {
  display: flex;
  flex: 1 0 50%;
}

.item-page-container-title {
  font-family: var(--font-family-main);
  font-size: 3rem;
  color: #fff;
}

.item-page-container-details {
  color: #ffffffaa;
  font-family: var(--font-family-main);
  line-height: 1.2rem;
  font-size: 1rem;
  border-left: 1px solid var(--color-primary);
  padding-left: 1rem;
  width: 85%;
}

.item-page-container-lend-button {
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

.item-page-container-lend-button:hover {
  background-color: #dddddd;
}
</style>
