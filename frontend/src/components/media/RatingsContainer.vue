<template>
  <div class="ratings-container">
    <div class="ratings-container-title-space">
      <div class="header">
        <div :style="{
          fontSize: `${props.fontSize ? props.fontSize : 5}rem`, marginTop:
            `${props.fontSize ? props.fontSize : 5}rem`
        }" class="ratings-container-title">
          <p class="ratings-container-title-media">{{ `R` }}</p>
          <p class="ratings-container-title-vault">eviews</p>
        </div>
        <button class="ratings-page-write-button" :onclick="onWritePressed"> write review </button>
      </div>
      <Rating v-for="(item, index) in data" :key="index" :user="item?.username" :title="item?.title"
        :details="item?.details" :rating="item?.rating" :verified="item?.verified" />
      <p v-if="!data || data.length == 0" class="g-text-a" style="width:
        70%;"> No reviews found. Be the first to post a review!</p>
    </div>
  </div>
</template>


<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { defineEmits, defineExpose, onMounted, ref } from 'vue';
import { useToast } from 'vue-toast-notification'

const emit = defineEmits(['toggleWriteEvent']);
const props = defineProps(['movieId', 'fontSize']);
const toast = useToast();


const data = ref([]);



// method for getting the reviews of the media id

const getMediaReviews = () => {
  fetch(`${apiUrl}/media/${props?.movieId}/reviews?page=0&page-size=10`)
    .then(result => {
      if (!result.ok) throw new Error('error');
      return result;
    })
    .then(response => response.json())
    .then(jsonData => data.value = jsonData)

    .catch(error => {
      toast.error('reviews data connection error');
      console.error('Failed to fetch data: ', error)
    })
};

defineExpose({
  getMediaReviews
});

const onWritePressed = () => {
  emit('toggleWriteEvent');
};


onMounted(() => {
  getMediaReviews();
});

</script>



<style scoped>
.ratings-container-title {
  position: relative;
  display: flex;
  justify-content: center;
  font-family: var(--font-family-main);
}

.ratings-container-title::after {
  position: absolute;
  content: '';
  width: 30%;
  height: 0px;
  background: linear-gradient(to right, #ffffff00, #ffffff77, #ffffff00);
  bottom: 25%;
  left: 50%;
  transform: translateX(-50%);
}

.ratings-container-title-space {
  display: flex;
  flex-direction: column;
  padding: 0rem 0rem;
  align-items: center;
}

.ratings-container-title-media {
  font-weight: 500;
  color: var(--color-primary);
}

.ratings-container-title-vault {
  font-weight: 500;
  color: #ffffffdd;
}

.header {
  width: 70%;
  display: flex;
  justify-content: space-between;
}

.ratings-page-write-button {
  background-color: var(--color-primary);
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  margin-top: 1rem;
  align-self: center;
  flex: 0 0 auto;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
  margin-top: 1.8rem;
}

.ratings-page-write-button:hover {
  background-color: #dddddd;
}
</style>
