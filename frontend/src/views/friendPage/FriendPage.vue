<template>
  <div class="background">
    <div class="friends-container">
      <div class="friends-body">
        <FriendsContainer />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();


</script>


<style scoped>
.background {
  display: flex;
  width: 100vw;
  flex: 1;
}

.friends-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1280px;
  width: 100%;
  min-height: 100vh;
  margin: 0 auto;
  padding: 0rem 2rem;
  font-weight: normal;
}

.friends-header {
  margin-top: 5rem;
}

.friends-body {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-top: 3rem;
}

.friends-title {
  font-size: 3rem !important;
  font-weight: 500;
}

.five-rem-distance {
  margin-top: 2rem;
  margin-bottom: 2rem;
}
</style>
