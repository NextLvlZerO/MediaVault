<template>
  <div class="search-filter-component">
    <div class="search-input-component">
      <input ref="inputRef" class="search-component" v-model="inputText" placeholder="Search" type="text"
        @focus="onInputFocus" @blur="status = 0" />
      <div class="search-results" v-if="status === 1" @mousedown.prevent>
        <MediaSearchResultItem v-for="(item, index) in data" :key="index" :title="item?.title" :id="item?.id"
          @onChildPressedEvent="unfocusInput" />
      </div>
      <div class="filter-options" v-if="status === 2">
        <MediaFilterComponent v-for="(item, index) in filterOptions" :key="index" :data="item"
          @onTagClickedEmit="updateFilterOptions" @onInputChangedEmit="updateFilterInput" />
      </div>
    </div>
    <div class="filter-component" @click="status = (status === 2 ? 0 : 2)">
      <i class="bi bi-filter filter-icon" />
    </div>
  </div>
</template>


<script setup>

const apiUrl = import.meta.env.VITE_API_URL;
import { ref } from 'vue';
import { useToast } from 'vue-toast-notification'


const toast = useToast();

const status = ref(0);
const inputText = ref('');
const inputRef = ref(null);


const filterOptions = ref([{
  componentId: 0,
  type: 'tag',
  onlyOne: false,
  title: 'Genres',
  filter: [
    {
      id: 0,
      title: 'Action',
      status: true
    },
    {
      id: 1,
      title: 'Horror',
      status: false
    },
    {
      id: 2,
      title: 'Fantasy',
      status: false
    },
    {
      id: 3,
      title: 'Romance',
      status: true
    },
    {
      id: 4,
      title: 'Arthouse',
      status: false
    },
    {
      id: 5,
      title: 'Drama',
      status: false
    },
  ]
},
{
  componentId: 1,
  type: 'input',
  title: 'Min rating',
  filter: null
},
{
  componentId: 2,
  type: 'input',
  title: 'Max price',
  filter: null
}
]);


const data = [{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
},
{
  "title": "Avengers Infinity war",
  "id": 2
}
]


// when focused update list

const onInputFocus = () => {
  status.value = 1;
  getMediaQueryData();
}


// get media query data from backend

const getMediaQueryData = () => {
  fetch(`${apiUrl}/media/search?query=Superman`)
    .then(result => {
      if (!result.ok) {
        throw new Error('error');
      }
      return result.json();
    })
    .then(response => data.value = response)
    .catch(error => {
      toast.error('Connection error');
      console.error('Error while searching media');
    })
};


// update filter when tag is pressed

const updateFilterOptions = (parameters) => {

  if (typeof (parameters.componentId) === 'undefined') {
    return;
  }

  filterOptions.value[parameters.componentId].filter = filterOptions.value[parameters.componentId].filter.map(item => {
    if (parameters?.onlyOne) {
      const updatedFilterItem = item.id === parameters?.id ? { ...item, status: parameters.status }
        : { ...item, status: false };
      return updatedFilterItem;

    }
    else {
      const updatedFilterItem = item.id === parameters?.id ? { ...item, status: parameters.status } : item;
      return updatedFilterItem;
    }
  })
};


// update filter when input is changed

const updateFilterInput = (parameters) => {
  filterOptions.value[parameters.componentId].filter = parameters?.filter;
  console.log(filterOptions);
};


const unfocusInput = () => {
  status.value = 0;
  inputRef.value?.blur();
};

</script>


<style scoped>
.search-input-component {
  position: relative;
  display: flex;
}

.search-component {
  background-color: var(--color-background);
  color: #fff;
  border-radius: 100px;
  padding-left: 15px;
  padding-right: 50px;
  border: 1px solid #ffffff44;
  outline: none;
}

.search-component:focus {
  border: 1px solid var(--color-primary);
}

.search-results {
  z-index: 10;
  position: absolute;
  bottom: 1;
  transform: translateY(50px);
  border: solid 1px #ffffff44;
  border-radius: 10px;
  background-color: var(--color-background);
  width: 100%;
  padding: .5rem;
}

.filter-options {
  display: flex;
  flex-direction: column;
  z-index: 10;
  position: absolute;
  bottom: 1;
  transform: translateY(50px);
  border: solid 1px #ffffff44;
  border-radius: 10px;
  background-color: var(--color-background);
  width: 100%;
  padding: .5rem;
  gap: .5rem;
}

.search-filter-component {
  display: flex;
  position: relative;
}

.filter-component {
  position: absolute;
  right: 0%;
  top: 50%;
  transform: translate(-0%, -50%);
  border-radius: 100px;
  height: 80%;
  margin-right: 4px;
  aspect-ratio: 1/1;
  cursor: pointer;
  transition: .2s ease-in-out all;
}

.filter-component:hover {
  background-color: #ffffff44;
}

.filter-icon {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
  color: #fff;
}
</style>
