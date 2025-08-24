<template>
  <div class="add-background">
    <div class="add-container">
      <form class="input-forms" @submit.prevent="handleReviewSubmit">
        <h1 class="add-title"> Review</h1>
        <div class="mb-3">
          <label class="form-label g-text-a">Title</label>
          <input class="form-control input-field" v-model="reviewTitle">
        </div>
        <div class="mb-3 content-input-container">
          <label class="form-label g-text-a">Content</label>
          <textarea class="form-control input-field content-input" v-model="reviewContent" />
        </div>
        <div class="footer">


          <div>
            <label class="form-label g-text-a">Rating</label>
            <select class="form-select rating-select" v-model="reviewRating" aria-label="Rating
              selection">
              <option class="rating-select-item" selected value="5">5</option>
              <option value="4">4</option>
              <option value="3">3</option>
              <option value="2">2</option>
              <option value="1">1</option>
            </select>
          </div>

          <div class="add-buttons">

            <button class="cancel-button" :onclick="onCancelButtonPressed"> cancel </button>
            <button class="confirm-button" type="submit"> confirm </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>


<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { defineEmits, defineProps, ref } from 'vue';
import { useToast } from 'vue-toast-notification';

const emits = defineEmits(['goBackEvent']);
const props = defineProps(['movieId']);
const toast = useToast();

const reviewTitle = ref('');
const reviewContent = ref('');
const reviewRating = ref(5);


const onCancelButtonPressed = () => {
  emits('goBackEvent', 'reviewSubmitEmit');
};


// function, that tries to put review into backend database

const handleReviewSubmit = () => {

  if (!reviewTitle.value?.trim() || !reviewContent.value?.trim()) {
    toast.error('Invalid input');
    return;
  }

  const reviewBody = {
    'title': reviewTitle.value,
    'details': reviewContent.value,
    'rating': reviewRating.value
  }

  fetch(`${apiUrl}/media/${props?.movieId}/review/create`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(reviewBody),
    credentials: 'include'
  })
    .then(result => {
      if (!result.ok) throw new Error('error');
      toast.success('Successfully posted review!');
      emits('reviewSubmitEmit');
    })
    .catch(error => {
      toast.error('Failed to load data');
      console.error('Failed to fetch data: ', error)
    })
};

</script>



<style scoped>
.add-background {
  z-index: 1000;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 110vh;
  transform: translateY(-100px);
  background-color: #00000088;
  backdrop-filter: blur(10px);
}

.add-container {
  width: 700px;
  height: 450px;
  max-width: 70%;
  max-height: 70%;
  border: 1px solid #ffffff44;
  border-radius: 10px;
  background-color: #202020cc;
}

.input-forms {
  display: flex;
  flex: 1;
  height: 100%;
  flex-direction: column;
  padding: 1.5rem;
}

.input-field {
  background-color: var(--background-color-ligher);
  border: 1px solid #ffffff44;
  color: #fff;
}


.input-field:focus {
  background-color: var(--background-color-ligher);
  color: #fff;
}

.add-title {
  font-family: var(--font-family-main);
  font-size: 2rem;
  font-weight: 600;
  color: #fff;
  margin-bottom: 1.2rem;
}

.add-buttons {
  display: flex;
  align-self: flex-end;
  justify-content: flex-end;
  gap: 0.5rem;
}

.cancel-button {
  display: flex;
  background-color: #dddddd;
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  padding: 8px 13px;
  border-radius: 100px;
  margin-top: 1rem;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.confirm-button {
  display: flex;
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
  transition: 0.1s ease-in-out all;
}

.confirm-button:hover {
  background-color: #dddddd;
}

.cancel-button:hover {
  background-color: var(--color-primary);
}

.content-input {
  display: flex;
  flex: 1;
}


.content-input-container {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.footer {
  display: flex;
  justify-content: space-between;
}

.rating-select {
  width: min-content;
  background-color: transparent;
  border: 1px solid #ffffff44;
  color: #fff;
}
</style>
