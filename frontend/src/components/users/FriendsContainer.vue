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
        <p v-if="!friendsData || friendsData.length == 0" class="g-text-a"> No friends found :/ </p>
        <SingleFriend v-else v-for="(friend, index) in friendsData" :key="index" :username="friend?.username"
          @userClickedEmit="onUserChange" :active="activeUser ===
            friend?.id" :userId="friend?.id" :request="false" />
      </div>
      <div v-if="addState" class="add-body">
        <div class="mb-3">
          <label class="form-label g-text-a">Username</label>
          <input class="form-control input-field" placeholder="search" @input="onSearchInput" v-model="searchQuery" />
        </div>
        <div class="add-results">
          <SingleFriend v-for="(friend, index) in searchData" :username="friend?.username"
            @userClickedEmit="onUserChange" :userId="friend?.id" :request="false" />
        </div>
        <div v-if="requestData && requestData.length > 0" style="margin-top: 4rem;">
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
        <UserMessage v-for="(item, index) in messageData[activeUser]" :key="index" :type="item?.type" :text="item?.text"
          :date="item?.date" />
      </div>
      <div class="message-input">
        <textarea class="message-input-area" placeholder="End-to-end-unencrypted" v-model="message"
          @input="resizeTextarea" ref="textarea" />
        <button class="send-button g-button-p" @click="sendMessage(message)">
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
const s = ref(null);

const userId = getCookie('userId');
const friendsData = ref([]);
const requestData = ref([]);
const searchData = ref([]);
const messageData = ref({});
const messageActiveData = ref([]);

const activeUser = ref(0);
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

  activeUser.value = id;
  updateUserMessages(id);
};


// method for getting messages

const updateUserMessages = (id) => {

  if (!id) {
    return;
  }

  if (!messageData.value[id]) {

    messageData.value[id] = [];

    fetch(`${apiUrl}/user/${userId}/message/${id}`)
      .then(result => {
        if (!result.ok) {
          return result.json()
            .then(response => {
              const errorMessage = response?.error;
              throw new Error(errorMessage);
            })
        }
        return result.json()
      })
      .then(response => {
        const messages = Array.isArray(response) ? response : [response];
        messageData.value[id] = messages.map(i => {
          i["date"] = new
            Date(i["date"]).toLocaleDateString("de-DE")
          return i;
        })
      })
      .catch(error => {
        toast.error(error.message);
        console.error(error);
      })
  }
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
        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
          .catch((error) => {
            console.error(error);
            toast.error(error.message);
          })
      }

      toast.success('Successfully added user');
    })
    .catch(error => {
      console.error(error);
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
        return response.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
    })
    .then(() => {
      const message = accept ? 'Successfully accepted friend request' : `Successfully denied friend
        request`
      toast.success(message);
      getRequestData();
      getFriendsData();
    })
    .catch(error => {
      toast.error(error.message);
      console.error(error);
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
// only reveals users, that are no friends yet

const getFriendsQueryData = () => {
  fetch(`${apiUrl}/user/find-user?query=${searchQuery.value}`)
    .then(result => {
      if (!result.ok) {
        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return result.json();
    })
    .then(response => searchData.value = response.filter(r => {
      return r?.id != userId && !friendsData.value.map(f => f?.id).includes(r?.id);
    }))
    .catch(error => {
      console.error(error);
      toast.error(error.message);
    })
}


// method for getting request data

const getRequestData = () => {
  fetch(`${apiUrl}/user/${userId}/friend-requests`)
    .then(result => {
      if (!result.ok) {
        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return result.json();
    })
    .then(response => requestData.value = response)
    .catch(error => {
      console.error(error);
      toast.error(error.message);
    })
};


// method for getting friends data

const getFriendsData = () => {
  fetch(`${apiUrl}/user/${userId}/friends`)
    .then(result => {
      if (!result.ok) {
        return result.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return result.json();
    })
    .then(response => friendsData.value = response)
    .catch(error => {
      console.error(error);
      toast.error(error.message);
    })
};


// method for socket config

s.value = new WebSocket(`ws://localhost:8080/message`);
console.log('Socket connected');


s.value.onopen = () => {
  s.value.send(`REGISTER;${userId}`);
  console.log('init socket');
};


s.value.onmessage = (message) => {
  handleIncomingMessage(message.data);
  console.log(message.data);
}



const handleIncomingMessage = (message) => {

  // message = MESSAGE;id;id;message

  if (!message) {
    console.error('invalid Message');
    toast.error('invalid Message');
    return;
  }

  const data = message.split(';');
  const method = data[0];

  if (!method) {
    console.error('invalid Message');
    toast.error('invalid Message');
  }

  switch (method) {
    case 'MESSAGE':
      handleMessageType(data);
      break;

    default:
      toast.error('invalid message type');
      console.error('invalid message type');
      return;
  }

};


// handling normal incoming message

const handleMessageType = (data) => {

  if (data.length < 4) {
    console.error('invalid message arguments');
    toast.error('invalid message arguments')
    return;
  }

  const friendId = data[1];
  const message = data[3];

  if (!friendId || !message) {
    console.error('invalid message arguments');
    toast.error('invalid message arguments')
    return;
  }


  const messageObject = {
    text: message,
    date: new Date().toLocaleDateString('de-DE'),
    type: 'received'
  };

  if (!messageData.value[friendId]) {
    updateUserMessages(friendId);
  }


  // just to be safe, shouldn't happen 4sure

  if (!messageData.value[friendId]) {
    console.error('this error is impossible');
    toast.error('this error is impossible')
    return;
  }

  messageData.value[friendId].push(messageObject);

  console.log(messageData.value);
  console.log(friendId);
  console.log(activeUser.value);

};



const sendMessage = (m) => {

  const messageObject = {
    text: m,
    date: new Date().toLocaleDateString("de-DE"),
    type: 'sent'
  }

  if (!activeUser.value) {
    console.error('no friend specified');
    toast.error('no friend specified');
    return;
  }

  if (!messageData.value[activeUser.value]) {
    console.error('something went wrong');
    toast.error('something went wrong');
    return;
  }

  if (m.length == 0) {
    console.error('message is empty');
    toast.error('message is empty');
  }

  s.value.send(`MESSAGE;${userId};${activeUser.value};${m}`);
  console.log(`MESSAGE;${userId};${activeUser.value};${m}`);

  messageData.value[activeUser.value].push(messageObject);
  message.value = '';
  resizeTextarea();

  console.log("message sent: ", m);
};


onMounted(() => {
  getFriendsData();
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
