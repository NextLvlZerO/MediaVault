<template>
  <div class="item-page-core">
    <div class="item-page-container">
      <div class="item-page-container-head">
        <div class="item-page-container-left">
          <h1 class="item-page-container-title"> {{ route.params.title }}</h1>
          <span class="item-page-container-details">
            {{ details }}
          </span>
        </div>
        <div class="item-page-container-right">
          <div class="right-details">

            <div class="right-details-media">
            </div>

            <div class="right-details-availability-container">
              <p class="right-details-availability-text g-text-a"> Availability: </p>
              <p class="right-details-availability g-text"> {{ `${available}` }} </p>
            </div>

            <div class="right-details-price-container">
              <p class="right-details-price-text g-text-a line-fix"> Price: </p>
              <p class="right-details-price g-text line-fix"> {{ `starting at ${price.toFixed(2)}€` }} </p>
            </div>
          </div>
          <div class="buttons">
            <button class="item-page-container-watchlist-button" :onclick="onWatchlistPressed">
              <i :class="watchlistAdded ? 'bi bi-check' : 'bi bi-plus'" style="font-size: 24px; color:
              #000" data-bs-toggle="popover" data-bs-trigger="hover" data-bs-placement="bottom"
                data-bs-content="add to watchlist" data-bs-custom-class="popover-dark" data-bs-animation="true"></i>
            </button>
            <button class="item-page-container-lend-button" :onclick="onLendPressed"> lend now </button>
          </div>
        </div>
      </div>
      <HSeperator class="five-rem-distance" />
      <div class="item-page-container-body">
        <MediaContainer :fontSize="3" prename="R" name="elevant" dataType="relevant" :clickable="true" />
        <HSeperator class="five-rem-distance" />
        <RatingsContainer :fontSize="3" :data="ratingData" @toggleWriteEvent="onWriteButtonPressed" />
      </div>
    </div>
    <RatingAddition v-if="currentlyWritingReview" @goBackEvent="currentlyWritingReview = false" />
  </div>

</template>



<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, watch, onMounted } from 'vue';


const route = useRoute();
const router = useRouter();

const details = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
const price = 2.30;
const available = "available";
const watchlistAdded = true;
const currentlyWritingReview = ref(false);
const ratingData = [
  {
    title: 'Best Film ever', user: 'NextLvlZer0', verified: true, rating: 5, details: `Lorem ipsum dolor sit amet, consetetur sadipscing
    elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
    voluptua.At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no
    sea takimata sanctus est Lorem ipsum dolor sit amet.`},
  {
    title: 'Best Film ever', user: 'NextLvlZer0', rating: 5, details: `Lorem ipsum dolor sit amet, consetetur sadipscing
    elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
    voluptua.At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no
    sea takimata sanctus est Lorem ipsum dolor sit amet.`},
  {
    title: 'Best Film ever', user: 'NextLvlZer0', rating: 5, details: `Lorem ipsum dolor sit amet, consetetur sadipscing
    elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
    voluptua.At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no
    sea takimata sanctus est Lorem ipsum dolor sit amet.`},
  {
    title: 'Best Film ever', user: 'NextLvlZer0', rating: 5, details: `Lorem ipsum dolor sit amet, consetetur sadipscing
    elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
    voluptua.At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no
    sea takimata sanctus est Lorem ipsum dolor sit amet.`},
  {
    title: 'Best Film ever', user: 'NextLvlZer0', rating: 5, details: `Lorem ipsum dolor sit amet, consetetur sadipscing
    elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
    voluptua.At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no
    sea takimata sanctus est Lorem ipsum dolor sit amet.`},
];

onMounted(() => {
  window.scrollTo({ top: -100, left: 0 })
});


watch(currentlyWritingReview, (newVal) => {
  document.documentElement.style.overflow = newVal ? 'hidden' : 'auto'
})

const onLendPressed = () => {
  router.push(`/checkout/item/${route.params.title}`);
};

const onWriteButtonPressed = () => {
  currentlyWritingReview.value = true;
};



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
  flex-direction: column;
  justify-content: space-between;
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
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.item-page-container-watchlist-button {
  background-color: #dddddd;
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
  height: 40px;
  width: 40px;
}

.item-page-container-lend-button:hover {
  background-color: #dddddd
}

.item-page-container-watchlist-button:hover {
  background-color: var(--color-primary);
}

.buttons {
  display: flex;
  gap: 0.5rem;
}

.line-fix {
  line-height: 0.2rem;
}

.right-details-price-container {
  display: flex;
  gap: 0.5rem;
}

.right-details-availability-container {
  display: flex;
  gap: 0.5rem;
  margin-top: 0.3rem;
}


.right-details-media {
  height: 200px;
  width: 150px;
  border-radius: 5px;
  border: 1px solid #ffffff44;
}

.five-rem-distance {
  margin-top: 5rem;
}

.item-page-core {
  display: flex;
}
</style>
