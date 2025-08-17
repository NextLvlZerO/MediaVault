<template>
  <div class="rating-container">
    <div class="rating-header">
      <div class="rating-header-left">
        <p class="rating-title">
          {{ props?.title }}
        </p>
        <p class="rating-user">
          {{ `\b - ${props?.user}` }}
        </p>
        <i v-if="props?.verified" class="bi bi-check-circle-fill verified-icon" data-bs-toggle="popover"
          data-bs-trigger="hover" data-bs-placement="top" data-bs-content="verified" data-bs-custom-class="popover-dark"
          data-bs-animation="true"> </i>
      </div>
      <p class="rating-rating">
        {{ `${props?.rating}/5` }}
      </p>
    </div>
    <div class="rating-body">
      <p class="rating-details g-text-a">
        {{ props?.details }}
      </p>
    </div>
  </div>
</template>


<script setup>

const props = defineProps(['title', 'details', 'rating', 'user', 'verified']);



</script>


<style scoped>
.rating-container {
  display: flex;
  flex-direction: column;
  width: 70%;
  border-top: 1px solid #ffffff44;
  padding-top: 1rem;
  padding-bottom: 0.5rem;
}

.rating-header {
  display: flex;
  justify-content: space-between;
  margin-top: 5px;
  margin-left: 1rem;
  margin-right: 1rem;
  height: 2.2rem;
}

.rating-header-left {
  display: flex;
}

.rating-title {
  font-size: 1.2rem;
  font-family: var(--font-family-main);
  font-weight: 600;
  color: #fff;
}

.rating-body {
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-left: 1rem;
  margin-right: 1rem;
}

.rating-user {
  font-family: var(--font-family-main);
  color: #ffffffaa;
  font-size: 1.2rem;
}

.rating-details {}

.rating-rating {
  font-family: var(--font-family-main);
  color: var(--color-primary);
  font-weight: 500;
}

.verified-icon {
  color: var(--color-primary);
  margin-left: 5px;
  transform: translateY(2px);
}

.popover-dark {
  --bs-popover-bg: #333;
  --bs-popover-border-color: #444;
  --bs-popover-color: #fff;
}
</style>
