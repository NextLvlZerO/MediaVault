<template>
  <div class="media-list-container">
    <Item title="Avatar" rating="4" />
    <Item title="Avatar" rating="4" />
    <Item title="Avatar" rating="4" />
    <Item title="Avatar" rating="4" />
    <Item title="Avatar" rating="4" />
  </div>
</template>



<script>
import Item from './Item.vue'

export default {
  name: 'MediaList',
  components: {
    Item,
  }
}
</script>



<style scoped>
p {
  font-size: 5rem;
  color: #fff;

}

.media-list-container {
  width: 100%;
  display: grid;
  gap: 25px;
  justify-content: center;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
}
</style>
