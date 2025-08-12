import { createRouter, createWebHistory } from 'vue-router'
import ItemPage from './../components/ItemPage/ItemPage.vue';
import Main from './../components/Main.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', component: Main
    },
    {
      path: '/media/item/:title', component: ItemPage
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
  ],
})

export default router
