<template>
  <div class="search-filter-component">
    <div class="search-input-component">
      <input ref="inputRef" class="search-component" v-model="inputText" placeholder="Search" type="text"
        @input="onSearchInput" @focus="onInputFocus" @blur="status = 0" />
      <div class="search-results" v-if="status === 1 && data && data.length > 0" @mousedown.prevent>
        <MediaSearchResultItem v-for="(item, index) in data" :key="index" :title="item?.title" :id="item?.id"
          @onChildPressedEvent="unfocusInput" />
      </div>
      <div class="filter-options" v-if="status === 2">
        <MediaFilterComponent v-for="(item, index) in filterOptions" :key="index" :data="item" :numeric="item?.numeric"
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
import { ref, onMounted, defineEmits } from 'vue';
import { useToast } from 'vue-toast-notification'


const toast = useToast();
const emits = defineEmits(['filterChangedEmit']);

const status = ref(0);
const debounceTimeout = ref(null);
const debounceFilterTimeout = ref(null);

const inputText = ref('');
const inputRef = ref(null);


const filterOptions = ref([{
  componentId: 0,
  type: 'tag',
  onlyOne: false,
  title: 'Genres',
  filter: [
  ]
},
{
  componentId: 1,
  type: 'input',
  numeric: true,
  title: 'Min rating',
  filter: null
},
{
  componentId: 2,
  type: 'input',
  numeric: false,
  title: 'Max price',
  filter: null
}
]);


const data = ref(null);
const genreData = ref(null);


onMounted(() => {
  getGenres();
});


const getGenres = () => {
  fetch(`${apiUrl}/genre/all`)
    .then(request => {
      if (!request.ok) {
        return request.json()
          .then(response => {
            const errorMessage = response?.error;
            throw new Error(errorMessage);
          })
      }
      return request.json();
    })
    .then(response => {
      genreData.value = response.map(item => ({
        id: item?.genreId,
        title: item?.genreName,
        status: false
      }));
      filterOptions.value = filterOptions.value.map(item => item.componentId === 0 ? {
        ...item, filter: genreData.value
      } : item)
    })
};


// wait 2 seconds till fetching backend for movie search input data

const onSearchInput = () => {

  clearTimeout(debounceTimeout.value);

  debounceTimeout.value = setTimeout(() => {
    getMediaQueryData();
  }, 500);
};


// when focused update list

const onInputFocus = () => {
  status.value = 1;
}


// get media filter data from backend

const getMediaFilterData = () => {

  const currentFilterData = {
    genre: filterOptions.value[0].filter.filter(item => item.status).map(item => item.title),
    rating: filterOptions.value[1].filter ? filterOptions.value[1].filter : 0,
    price: filterOptions.value[2].filter ? filterOptions.value[2].filter : 100000
  };

  emits('filterChangedEmit', currentFilterData);

  console.log(currentFilterData);

};


// get media query data from backend

const getMediaQueryData = () => {
  fetch(`${apiUrl}/media/search?query=${inputText.value}`)
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
    .then(response => data.value = response)
    .catch(error => {
      toast.error('Connection error');
      console.error('Error while searching media');
    })
};


// update filter when tag is pressed

const updateFilterOptions = (parameters) => {

  clearTimeout(debounceFilterTimeout.value);

  debounceFilterTimeout.value = setTimeout(() => {
    getMediaFilterData();
  }, 500);


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

  clearTimeout(debounceFilterTimeout.value);

  debounceFilterTimeout.value = setTimeout(() => {
    getMediaFilterData();
  }, 500);


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
