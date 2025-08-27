<template>
  <div class="login-form-container">
    <div class="header">
      <h1>
        {{ registerLayout ? 'Register' : 'Login' }}
      </h1>
    </div>
    <form class="needs-validation login-form" novalidate @submit="onSubmit">
      <div class="mb-3">
        <label class="form-label g-text-d-a" for="username">Username</label>
        <input class="form-control input-field" type="text" id="username" v-model="username" required>
        <div class="invalid-feedback">
          enter a valid username!
        </div>
      </div>
      <div class="mb-3">
        <label class="form-label g-text-d-a">Password</label>
        <input class="form-control input-field input-password" type="password" v-model="password" required>
        <div class="invalid-feedback">password can't be empty!</div>
      </div>
      <div class="mb-3" v-if="registerLayout">
        <label class="form-label g-text-d-a">Confirm password</label>
        <input class="form-control input-field input-password" type="password" ref="confirmedPasswordInputRef"
          v-model="confirmedPassword" :required="registerLayout">
        <div class="invalid-feedback">passwords do not match</div>
      </div>
      <div v-if="!registerLayout" style="align-self: center;">
        <label style="align-self: center" class="form-label-forgot-password g-text-d-a">Forgot
          password?</label>
        <label style="margin-left: 0.5rem; font-weight:
          500; cursor: pointer;" class="g-text-d">Unlucky</label>
      </div>
      <button class="login-button" type="submit">
        {{ registerLayout ? 'Register' : 'Login' }}
      </button>
      <div class="register-now-container" v-if="!registerLayout">
        <label class="g-text-d-a">Don't have an account?</label>
        <label :onclick="() => { registerLayout = true }" style="margin-left: 0.5rem; font-weight:
          500; cursor: pointer;" class="g-text-d">
          Register</label>
      </div>
      <div class="login-now-container" v-if="registerLayout">
        <label class="g-text-d-a">Have an account?</label>
        <label :onclick="() => { registerLayout = false }" style="margin-left: 0.5rem; font-weight:
          500; cursor: pointer;" class="g-text-d">
          Login</label>
      </div>
    </form>
  </div>
</template>



<script setup>

import { ref, defineEmits } from 'vue';
import { useRouter } from 'vue-router';

const Router = useRouter();
const emits = defineEmits(['onFormSubmitEmit']);
const confirmedPasswordInputRef = ref(null);

const username = ref('');
const password = ref('');
const confirmedPassword = ref('');

const registerLayout = ref(false);



const onSubmit = (e) => {
  e.preventDefault();
  e.stopPropagation();

  if (!checkValidation(e)) return;
  emits('onFormSubmitEmit', {
    type: registerLayout.value ? 'register' : 'login',
    username: username.value,
    password: password.value,
    confirmedPassword: confirmedPassword.value
  });
};


const checkValidation = (e) => {
  let result = true;
  const form = e.target;

  if (registerLayout.value && (password.value !== confirmedPassword.value)) {
    console.error('passwords do not match');
    result = false;

    const confirmInput = confirmedPasswordInputRef.value
    if (confirmedPasswordInputRef.value) {
      confirmedPasswordInputRef.value.setCustomValidity("Passwords do not match");
      confirmedPasswordInputRef.value.reportValidity();
    }
  }
  else {

    const confirmInput = confirmedPasswordInputRef.value
    if (confirmedPasswordInputRef.value) {
      confirmedPasswordInputRef.value.setCustomValidity("");
    }
  }

  if (!form.checkValidity()) {
    console.error('invalid inputs');
    result = false;
  }
  form.classList.add("was-validated");

  return result;
}

</script>



<style scoped>
.login-form-container {
  display: flex;
  flex-direction: column;
  padding: 2.3rem;
  border-radius: 10px;
}

.input-field {
  background-color: var(--background-color-ligher);
  width: 350px;
  border: 1px solid #00000033;
  color: #000;
}

.input-field:focus {
  background-color: var(--background-color-ligher);
  color: #000;
}


h1 {
  font-size: 2.5rem;
  font-family: var(--font-family-main);
  font-weight: 500;
  color: #000;
}

.header {
  display: flex;
  width: 100%;
  justify-content: center;
  margin-bottom: 2rem;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.login-button {
  background-color: #dddddd;
  color: #000000dd;
  font-family: var(--font-family-main);
  font-size: 1rem;
  font-weight: 600;
  border: 0px;
  border-radius: 100px;
  padding: 8px 13px;
  margin-top: 1rem;
  width: 100%;
  align-self: flex-start;
  cursor: pointer;
  transition: 0.2s ease-in-out all;
}

.login-button:hover {
  background-color: #eeeeee;
}

.form-label-forgot-password {
  margin-top: 0.5rem;
}

.register-now-container {
  margin-top: .5rem;
  align-self: center;
}

.login-now-container {
  margin-top: .5rem;
  align-self: center;
}
</style>
