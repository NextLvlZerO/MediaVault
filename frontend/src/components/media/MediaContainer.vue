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
  </div>

</template>


<script setup>
import { ref, onMounted } from 'vue';
import { useToast } from 'vue-toast-notification';
const apiUrl = import.meta.env.VITE_API_URL;

const props = defineProps(['prename', 'name', 'clickable', 'fontSize']);
const toast = useToast();

const data = ref([null, null, null, null, null]);




const getMediaItemData = () => {
  fetch(`${apiUrl}/media/movies/best-rated?amount=10`)
    .then(response => {
      if (!response.ok) { throw new Error('error') }
      return response.json()
    })
    .then(json => {
      data.value = json
    })
    .catch(error => {
      toast.error('Failed to load data');
      console.error('Failed to fetch data: ', error)
    })
};


onMounted(() => {
  getMediaItemData();
})


</script>


<style scoped>
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
