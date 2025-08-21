import { createRouter, createWebHistory } from 'vue-router'
import ItemPage from '../views/itemPage/ItemPage.vue';
import LendPage from '../views/checkoutPage/CheckoutPage.vue';
import Main from '../views/homePage/Main.vue';
import ThanksPage from '../views/checkoutPage/ThanksPage.vue';
import ProfilePage from '../views/profilePage/ProfilePage.vue';
import LoginPage from '../views/loginPage/LoginPage.vue';
import SubscriptionPage from '../views/profilePage/SubscriptionPage.vue';
import FriendPage from '../views/friendPage/FriendPage.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', component: Main
    },
    {
      path: '/media/item/:id', component: ItemPage
    },
    {
      path: '/checkout/:type/:item', component: LendPage
    },
    {
      path: '/media/item/lend/purchase-confirmation', component: ThanksPage
    },
    {
      path: '/user/profile', component: ProfilePage
    },
    {
      path: '/login', component: LoginPage
    },
    {
      path: '/user/subscription', component: SubscriptionPage,
    },
    {
      path: '/user/friends', component: FriendPage
    },
  ],
})

export default router
