<template>
  <div class="item-page-core">
    <div class="item-page-container">
      <div class="item-page-container-head">
        <div class="item-page-container-left">
          <h1 class="item-page-container-title"> {{ data?.title }}</h1>
          <span class="item-page-container-details">
            {{ data?.details ? data.details : 'No description available.' }}
          </span>
        </div>
        <div class="item-page-container-right">
          <div class="right-details">

            <Item :title="data?.title" :rating="data?.rating" :poster="data?.poster" :clickable="false"
              :id="data?.id" />

            <div class="right-details-availability-container">
              <p class="right-details-availability-text g-text-a"> Availability: </p>
              <p class="right-details-availability g-text"> {{ data?.available ? `available` : `out
                of stock` }} </p>
            </div>

            <div class="right-details-price-container">
              <p class="right-details-price-text g-text-a line-fix"> Price: </p>
              <p class="right-details-price g-text line-fix"> {{ `starting at ${data?.price.toFixed(2)}€` }} </p>
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
        <RatingsContainer ref="reviewContainerRef" :fontSize="3" :movieId="movieId" :key="movieId"
          @toggleWriteEvent="onWriteButtonPressed" />
      </div>
    </div>
    <RatingAddition v-if="currentlyWritingReview" :movieId="movieId" @goBackEvent="currentlyWritingReview = false"
      @reviewSubmitEmit="onReviewSubmit" />
  </div>

</template>



<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { useRoute, useRouter } from 'vue-router';
import { ref, watch, onMounted } from 'vue';
import { useToast } from 'vue-toast-notification';
import { getCookie } from './../../components/utility/cookies.js';

const route = useRoute();
const router = useRouter();
const toast = useToast();
const reviewContainerRef = ref(null);

const data = ref(null);
const currentlyWritingReview = ref(false);
const userId = getCookie('userId');
const watchlistAdded = ref(false);
const movieId = ref(route.params?.id);


// load data when mounted and scroll to top

onMounted(() => {
  window.scrollTo({ top: -100, left: 0 })
  getItemData(route.params?.id);
  getWatchlistStatus(route.params?.id);
});


// check if review writing is active and disable scroll if the case

watch(currentlyWritingReview, (newVal) => {
  document.documentElement.style.overflow = newVal ? 'hidden' : 'auto'
})


// check if other items need to be loaded

watch(() => route.params.id, (newVal) => {
  window.scrollTo({ top: -100, left: 0 })
  getItemData(newVal);
  getWatchlistStatus(newVal);
  movieId.value = newVal;
})


// method for loading item data aligned with the params id

const getItemData = (id) => {
  fetch(`${apiUrl}/media/item/${id}`)
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }
      return result.json();
    })
    .then(response => data.value = response)
    .catch(error => {
      toast.error('Item data connection error')
      console.error(error);
    })
};


// method for loading watchlist button

const getWatchlistStatus = (movieId) => {
  fetch(`${apiUrl}/user/${userId}/media/${movieId}/watchlist`)
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }
      return result.json();
    })
    .then(response => {
      watchlistAdded.value = response?.exists ? true : false;
    })
    .catch(error => {
      console.error(error);
      toast.error('connection error watchlist');
    })
};


// methods for button pressed

const onLendPressed = () => {
  router.push(`/checkout/item/${route.params?.id}`);
};

const onWatchlistPressed = () => {
  fetch(`${apiUrl}/user/${userId}/watchlist/media/${route.params?.id}`, {
    method: 'PUT',
  })
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }

      watchlistAdded.value = !watchlistAdded.value;

      const message = watchlistAdded.value ? 'successfully added to watchlist' : `successfully removed from
        watchlist`;

      toast.success(message);
    })
    .catch(error => {
      console.error(error);
      toast.error('connection watchlist error');
    })
};

const onWriteButtonPressed = () => {
  currentlyWritingReview.value = true;
};

const onReviewSubmit = () => {
  currentlyWritingReview.value = false;
  reviewContainerRef.value?.getMediaReviews();
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
  height: 300px;
  width: 200px;
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
