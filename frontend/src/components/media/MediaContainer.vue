<template>

  <div class="mediaC-container" v-if="data && data.length > 0">
    <div class="mediaC-container-title-space">
      <div :style="{
        fontSize: `${props.fontSize ? props.fontSize : 5}rem`, marginTop:
          `${props.fontSize ? props.fontSize : 5}rem`
      }" class="mediaC-container-title">
        <p class="mediaC-container-title-media">{{ `${props?.prename}` }}</p>
        <p class="mediaC-container-title-vault"> {{ props?.name }}</p>
      </div>
    </div>
    <MediaList :data="data" :expand="props?.expand" :clickable="props?.clickable" />
    <button class="g-button-s" style="margin-top: 2rem" @click="onLoadMoreButtonClick"> load more </button>
  </div>

</template>


<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { ref, defineProps, onMounted, watch } from 'vue';
import { useToast } from 'vue-toast-notification';
import { getCookie } from '../utility/cookies.js';

const props = defineProps(['prename', 'name', 'dataType', 'clickable', 'fontSize', 'pageSize',
  'filterData', 'expand'])
const toast = useToast();

const data = ref([null, null, null, null, null]);
const pageCount = ref(0);
const userId = getCookie('userId');




watch(() => props.filterData, (newVal) => {
  getMediaItemData();
}
);



const getMediaItemData = (append) => {

  if (props?.filterData) {
    getMediaFilterData(props.filterData, append);
    return;
  }


  // fetch only the requested type, if filter is disabled

  let fetchUrl = `${apiUrl}/media/movie/all?page=${pageCount.value}&page-size=${props?.pageSize ?
    props.pageSize : 5}`;

  switch (props?.dataType) {
    case 'media-rating':
      fetchUrl = `${apiUrl}/media/movie/best-rated?page=${pageCount.value}&page-size=${props?.pageSize ?
        props.pageSize : 5}`;
      break;

    case 'series-rating':
      fetchUrl = `${apiUrl}/media/series/best-rated?page=${pageCount.value}&page-size=${props?.pageSize ?
        props.pageSize : 5}`;
      break;

    case 'series-all':
      fetchUrl = `${apiUrl}/media/series/all?page=${pageCount.value}&page-size=${props?.pageSize ?
        props.pageSize : 5}`;
      break;

    case 'watchlist':
      fetchUrl = `${apiUrl}/user/${userId}/watchlist`;
      break;

    case 'history':
      fetchUrl = `${apiUrl}/history/user/${userId}`;
      break;

    case 'lending':
      fetchUrl = `${apiUrl}/user/${userId}/currently-lending`;
  }


  // real fetch process

  fetch(`${fetchUrl}`)
    .then(request => {
      if (!request.ok) {
        return request.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return request.json()
    }
    )
    .then(json => {
      if (append) {
        data.value = [...data.value, ...json];

        if (!json || json.length == 0) { throw new Error('No more data available'); }
      }
      else {
        data.value = json
      }
    })
    .catch(error => {
      toast.error(error.message);
      console.error('Failed to fetch data: ', error)
    })
};


// getting media filter data

const getMediaFilterData = (currentFilterData, append) => {
  fetch(`${apiUrl}/media/movie/filter?page=${pageCount.value}&page-size=${props?.pageSize ?
    props.pageSize : 5}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(currentFilterData)
  })
    .then(result => {
      if (!result.ok) {
        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return result.json();
    })
    .then(json => {

      if (append) {
        data.value = [...data.value, ...json];

        if (!json || json.length == 0) { throw new Error('No more data available'); }
      }
      else {
        data.value = json
      }
    })
    .catch(error => {
      toast.error(error);
      console.error('Error while searching media');
    })
}



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
