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

const props = defineProps(['prename', 'name', 'clickable', 'fontSize']);

const data = [
  { title: 'Avatar', rating: 4 },
  { title: 'Avengers: Endgabe', rating: 5 },
  { title: 'Interstellar', rating: 2 },
  { title: 'Stranger Things', rating: 3 },
  { title: 'Game Of Thrones', rating: 4 }
];

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
