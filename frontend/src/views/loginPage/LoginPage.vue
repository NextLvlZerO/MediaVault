<template>
  <div class="background">
    <div class="login-container">
      <div class="left-container">
        <div class="left-body">
          <div class="login-container-title">
            <h1 class="login-container-title-media text">MEDIA</h1>
            <h1 class="login-container-title-vault text">VAULT</h1>
          </div>
          <div class="login-container-subtitle">
            <span class="subtitle-text">your gateway to unforgettable entertainment
              experiences.
            </span>
          </div>
        </div>
      </div>
      <div class="right-container">
        <LoginForm @onFormSubmitEmit="handleFormSubmit" />
      </div>
    </div>
  </div>
</template>



<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { useRoute, useRouter } from 'vue-router';
import { useToast } from 'vue-toast-notification';

const route = useRoute();
const router = useRouter();
const toast = useToast();


// method that tries to log in or to register a new account

const handleFormSubmit = (parameters) => {
  if (parameters?.type === 'login') {
    handleLoginSubmit(parameters?.username, parameters?.password);
  }

  else if (type === 'register') {
    handleRegisterSubmit(parameters?.username, parameters?.password, parameters?.confirmedPassword);
  }

  else {
    console.error('invalid form input emit type')
    return;
  }
};


const handleLoginSubmit = (username, password) => {
  const loginBody = {
    username: username,
    password: password
  };

  fetch(`${apiUrl}/login`, {
    method: 'POST',
    header: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(loginBody),
  })
    .then(result => {
      if (!result.ok) {
        throw new Error('login failed');
      }
      router.push('/');
    })
    .catch(error => {
      toast.error('Login denied but we dont have endpoints so move forward carefully');
      console.error(error);
      router.push('/');
    });
};


const handleRegisterSubmit = (username, password, confirmedPassword) => {
  return;
};



</script>


<style scoped>
.background {
  display: flex;
  width: 100vw;
  flex: 1;
}

.login-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100vw;
  height: 100vh;
  margin: 0 auto;
  font-weight: normal;
}

.left-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1 0 65%;
  height: 100%;
}

.left-body {
  display: flex;
  justify-content: center;
}

.right-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1 0 35%;
  max-width: 600px;
  height: 100%;
  background-color: var(--color-primary);
}

.login-title {
  font-size: 2rem !important;
  font-weight: 500;
}


.login-container-title {
  display: flex;
  flex-direction: column;
  margin-bottom: 0;
  height: 15rem;
  border-right: 1px solid var(--color-primary);
  padding-right: 3rem;
}

.login-container-subtitle {
  display: flex;
  font-family: var(--font-family-main);
  color: #fff;
  padding-left: 3rem;
  align-items: center;
}

.login-container-title-media {
  font-weight: 400;
  color: var(--color-primary);
  margin-bottom: -50px;
  line-height: 10rem;
}

.login-container-title-vault {
  font-weight: 400;
  background: linear-gradient(to top, #87aad4, white);
  background-clip: text;
  color: transparent;
  margin-left: 5rem;
  line-height: 10rem;
}

.text {
  font-size: 8rem;
  font-family: var(--font-family-title);
}

.subtitle-text {
  font-size: 1.8rem;
  line-height: 2rem;
  font-weight: 300;
  width: 400px;
}
</style>
