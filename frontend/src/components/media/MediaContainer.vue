<template>

  <div class="mediaC-container">
    <div class="mediaC-container-title-space">
      <div :style="{
        fontSize: `${props.fontSize ? props.fontSize : 5}rem`, marginTop:
          `${props.fontSize ? props.fontSize : 5}rem`
      }" class="mediaC-container-title">
        <p class="mediaC-container-title-media">{{ `${props?.prename}` }}</p>
        <p class="mediaC-container-title-vault"> {{ props?.name }}</p>
      </div>
    </div>
    <MediaList :data="data" :clickable="props?.clickable" />
    <button class="g-button-s" style="margin-top: 2rem" @click="onLoadMoreButtonClick"> load more </button>
  </div>

</template>


<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { ref, onMounted } from 'vue';
import { useToast } from 'vue-toast-notification';
import { getCookie } from '../utility/cookies.js';

const props = defineProps(['prename', 'name', 'dataType', 'clickable', 'fontSize', 'pageSize']);
const toast = useToast();

const data = ref([null, null, null, null, null]);
const pageCount = ref(0);
const userId = getCookie('userId');



const getMediaItemData = (append) => {


  // fetch only the requested type

  let fetchUrl = `${apiUrl}/media/movies/all?page=${pageCount.value}&page-size=${props?.pageSize ?
    props.pageSize : 5}`;

  switch (props?.dataType) {
    case 'rating':
      fetchUrl = `${apiUrl}/media/movies/best-rated?page=${pageCount.value}&page-size=${props?.pageSize ?
        props.pageSize : 5}`;
      break;

    case 'watchlist':
      fetchUrl = `${apiUrl}/user/${userId}/watchlist`;
      break;

    case 'history':
      fetchUrl = `${apiUrl}/user/${userId}/history`;
      break;
  }


  // real fetch process

  fetch(`${fetchUrl}`)
    .then(response => {
      if (!response.ok) { throw new Error('error') }
      return response.json()
    })
    .then(json => {
      if (append) {
        data.value = [...data.value, ...json];
      }
      else {
        data.value = json
      }
    })
    .catch(error => {
      toast.error('Failed to load data');
      console.error('Failed to fetch data: ', error)
    })
};


const onLoadMoreButtonClick = () => {
  pageCount.value += 1;
  getMediaItemData(true);
};


onMounted(() => {
  getMediaItemData();
})


</script>


<style scoped>
.mediaC-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.mediaC-container-title {
  position: relative;
  display: flex;
  justify-content: center;
  font-family: var(--font-family-main);
}

.mediaC-container-title::after {
  position: absolute;
  content: '';
  width: 30%;
  height: 0px;
  background: linear-gradient(to right, #ffffff00, #ffffff77, #ffffff00);
  bottom: 25%;
  left: 50%;
  transform: translateX(-50%);
}

.mediaC-container-title-space {
  padding: 0rem 0rem;
}

.mediaC-container-title-media {
  font-weight: 500;
  color: var(--color-primary);
}

.mediaC-container-title-vault {
  font-weight: 500;
  color: #ffffffdd;
}
</style>
