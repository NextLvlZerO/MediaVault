<template>
  <div class="message-container" :class="props?.type === 'received' ? 'received' : 'sent'">
    <p :class="props?.type === 'received' ? 'g-text' : 'g-text-d'" style="margin-bottom: 1.6rem;">
      {{ props?.text }}
    </p>
    <p class="container-time" :class="props?.type === 'received' ? 'g-text-a' :
      'g-text-d-a'">{{ props?.date }}</p>
  </div>

</template>


<script setup>
import { defineProps } from 'vue';

const props = defineProps(['date', 'text', 'type']);


const text = `
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
`;

</script>


<style scoped>
.message-container {
  position: relative;
  border-radius: 10px;
  width: 65%;
  padding: 1rem;
}

.container-time {
  position: absolute;
  left: 0;
  bottom: 1;
  transform: translate(1rem, -1rem);
}

.sent {
  align-self: flex-end;
  background-color: var(--color-primary);
}

.received {
  align-self: flex-start;
  background-color: var(--color-background-lighter);
}
</style>
