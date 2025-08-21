<template>
  <div class="search-item-component" :onclick="onItemClick">
    <p class="item-title">{{ props?.title }}</p>
  </div>
</template>


<script setup>
import { defineProps, defineEmits } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps(['title', 'id']);
const emits = defineEmits(['onChildPressedEvent']);
const router = useRouter();



const onItemClick = () => {
  router.push(`/media/item/${props?.id}`)
  emits('onChildPressedEvent');
};

</script>


<style scoped>
.search-item-component {
  display: flex;
  border-radius: 8px;
  cursor: pointer;
  align-items: center;
}

.search-item-component:hover {
  background-color: var(--color-background-lighter);
}


.item-title {
  display: flex;
  align-self: center;
  font-family: var(--font-family-main);
  margin-left: 1rem;
  color: #fff;
  transform: translateY(7px);
}
</style>
