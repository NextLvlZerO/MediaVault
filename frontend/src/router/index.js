import { createRouter, createWebHistory } from 'vue-router'
import ItemPage from '../views/itemPage/ItemPage.vue';
import LendPage from '../views/lendPage/LendPage.vue';
import Main from '../views/homePage/Main.vue';
import ThanksPage from '../views/lendPage/ThanksPage.vue';
import ProfilePage from '../views/profilePage/ProfilePage.vue';

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
      path: '/media/item/lend/:title', component: LendPage
    },
    {
      path: '/media/item/lend/purchase-confirmation', component: ThanksPage
    },
    {
      path: '/profile', component: ProfilePage
    }
  ],
})

export default router
