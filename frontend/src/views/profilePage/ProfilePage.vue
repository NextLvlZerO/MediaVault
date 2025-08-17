<template>
  <div class="background">
    <div class="profile-container">
      <div class="profile-header">
        <h1 class="profile-title g-text">
        </h1>
      </div>
      <div class="profile-body">
        <div class="user-information">
          <h2 class="body-header">
            General
          </h2>
          <div class="user-info-item user-information-username">
            <p class="g-text-b-a"> {{ `Username: \b` }} </p>
            <p class="g-text-b"> NextLvlZer0</p>
          </div>
        </div>
        <HSeperator class="five-rem-distance" />
        <div class="user-subscription">
          <h2 class="body-header">
            Subscription
          </h2>
          <div class="user-info-item user-information-subscription-status">
            <div style="display: flex;">
              <p class="g-text-b-a"> {{ `Status: \b` }} </p>
              <p class="g-text-b"> Gold</p>
            </div>
            <button class="item-page-container-lend-button" :onclick="onLendPressed"> cancel</button>
          </div>
        </div>
        <HSeperator class="five-rem-distance" />
        <MediaContainer :fontSize="3" prename="H" name="istory" dataType="history" :clickable="false" />
        <HSeperator style="margin-top: 5rem;" class="five-rem-distance upper-distance" />
        <MediaContainer :fontSize="3" prename="W" name="atchlist" dataType="watchlist" :clickable="true" />
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

.profile-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1000px;
  width: 100%;
  min-height: 100vh;
  margin: 0 auto;
  padding: 0rem 2rem;
  font-weight: normal;
}

.profile-header {
  margin-top: 5rem;
}

.profile-body {
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-top: 3rem;
}

.profile-title {
  font-size: 3rem !important;
  font-weight: 500;
}

.five-rem-distance {
  margin-top: 2rem;
  margin-bottom: 2rem;
}

.user-info-item {
  display: flex;
}

.user-information-subscription-status {
  align-items: center;
  justify-content: space-between;
}

.body-header {
  color: #fff;
  font-family: var(--font-family);
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.item-page-container-lend-button {
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
</style>
