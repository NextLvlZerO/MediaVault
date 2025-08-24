<template>
  <div class="friends-c-container">
    <div class="left">
      <div class="friends-header">
        <h1 class="friends-title g-text">
          Friends
        </h1>
        <button class="g-button-p add-button" :onclick="onAddButtonClicked">
          {{ addState ? 'back' : 'add' }}
        </button>
      </div>
      <div v-if="!addState" class="friends-body">
        <SingleFriend username="Lars" @userClickedEmit="onUserChange" :active="activeUser === 'Lars'" />
        <SingleFriend username="Julian Hensel" @userClickedEmit="onUserChange"
          :active="activeUser === 'Julian Hensel'" />
        <SingleFriend username="Fortniteenjoyer123" @userClickedEmit="onUserChange"
          :active="activeUser === 'Fortniteenjoyer123'" />
        <SingleFriend username="NextLvlZer0" @userClickedEmit="onUserChange" :active="activeUser === 'NextLvlZer0'" />
      </div>
      <div v-if="addState" class="add-body">
        <div class="mb-3">
          <label class="form-label g-text-a">Username</label>
          <input class="form-control input-field" placeholder="search" @input="onSearchInput" v-model="searchQuery" />
        </div>
        <div class="add-results">
          <SingleFriend v-for="(friend, index) in searchData" :username="friend?.username"
            @userClickedEmit="onUserChange" :userId="friend?.id" />
        </div>
        <div style="margin-top: 4rem;">
          <label class="form-label g-text-a">Requests</label>
        </div>
        <div class="request-results">
          <SingleFriend v-for="(friend, index) in requestData" :username="friend?.username" :userId="friend?.id"
            :request="true" @userRequestsClickedEmit="handleUserRequestEvent" />
        </div>
      </div>
    </div>
    <div class="right">
      <div class="right-messages">
        <UserMessage type="received" />
        <UserMessage type="sent" />
        <UserMessage />
        <UserMessage />
      </div>
      <div class="message-input">
        <textarea class="message-input-area" placeholder="End-to-end-unencrypted" v-model="message"
          @input="resizeTextarea" ref="textarea" />
        <button class="send-button g-button-p">
          <i class="bi bi-send-fill" style="color: #000; font-size: 18px; margin: 0px" />
        </button>
      </div>
    </div>
  </div>
</template>


<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { ref, onMounted } from 'vue'
import { getCookie } from '../utility/cookies.js';
import { useToast } from 'vue-toast-notification';

const toast = useToast();

const userId = getCookie('userId');
const friendsData = ref([]);
const requestData = ref([]);
const searchData = ref([]);

const activeUser = ref('Lars');
const message = ref("");
const textarea = ref(null);

const debounceTimeout = ref(null);
const searchQuery = ref('');
const addState = ref(false);


const resizeTextarea = () => {
  const el = textarea.value;
  if (!el) return;
  el.style.height = "auto";
  el.style.height = Math.min(el.scrollHeight + 10, 300) + "px";
};


const onAddButtonClicked = () => {
  activeUser.value = null;
  addState.value = !addState.value;
};


const onUserChange = (username, id) => {

  if (addState.value) {
    handleUserAddition(id);

    return;
  }
  activeUser.value = username;
};


// method for adding user friend request

const handleUserAddition = (id) => {
  fetch(`${apiUrl}/user/${userId}/add-user/${id}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }
      toast.success('successfully added user');
    })
    .catch(error => {
      console.error(error);
      toast.error('connection add error');
    })
}


// method for handling request when one of the request buttons is pressed

const handleUserRequestEvent = (accept, user2Id) => {
  const method = accept ? 'accept-user' : 'deny-user';

  fetch(`${apiUrl}/user/${userId}/${method}/${user2Id}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('connection request error');
        console.error(error);
      }
    })
    .then(() => {
      const message = accept ? 'Successfully accepted friend request' : `Successfully denied friend
        request`
      toast.success(message);
    })
    .catch(error => {
      toast.error(error.message);
    })

};


// method for waiting .5sec till getting  data

const onSearchInput = () => {
  clearTimeout(debounceTimeout.value);

  debounceTimeout.value = setTimeout(() => {
    getFriendsQueryData();
  }, 500);
};

// method for getting query search data

const getFriendsQueryData = () => {
  fetch(`${apiUrl}/user/find-user?query=${searchQuery.value}`)
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }
      return result.json();
    })
    .then(response => searchData.value = response)
    .catch(error => {
      console.error(error);
      toast.error('connection search error');
    })
}


// method for getting request data

const getRequestData = () => {
  fetch(`${apiUrl}/user/${userId}/friend-requests`)
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }
      return result.json();
    })
    .then(response => requestData.value = response)
    .catch(error => {
      console.error(error);
      toast.error('connection request data error');
    })
};


onMounted(() => {
  getRequestData();
});



</script>


<style scoped>
.friends-c-container {
  display: flex;
  height: 80vh;
  width: 100%;
  max-height: 1000px;
  border: 1px solid #ffffff44;
  border-radius: 10px;
}

.friends-header {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.friends-title {
  display: flex;
  font-size: 1.6rem !important;
  font-weight: 500;
}

.friends-body {
  display: flex;
  flex-direction: column;
  gap: .2rem;
}

.left {
  display: flex;
  flex-direction: column;
  flex: 1 0 30%;
  gap: .2rem;
  padding: 2rem;
  overflow-y: auto;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  background-color: var(--color-background-lighter);
}

.right {
  display: flex;
  flex-direction: column;
  flex: 1 0 70%;
  padding: 1.3rem;
}

.right-messages {
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 1rem;
  overflow-y: auto;
  padding: .7rem;
}

.message-input {
  display: flex;
  margin-top: .5rem;
}

.message-input-area {
  background-color: var(--color-background);
  color: #fff;
  border: 1px solid #ffffff44;
  border-radius: 10px;
  width: 100%;
  padding: 1rem;
  resize: none;
  outline: none;
}

.message-input-area:focus {
  border-color: var(--color-primary);
}

.send-button {
  height: min-content;
  width: min-content;
  margin-left: 1rem;
  align-self: flex-end;
  padding: 0.4rem 0.7rem !important;
}

.add-button {
  transform: translateY(-4px);
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

.add-results {
  display: flex;
  flex-direction: column;
  gap: .2rem;
}

.request-results {
  display: flex;
  flex-direction: column;
  gap: .2rem;
}
</style>
