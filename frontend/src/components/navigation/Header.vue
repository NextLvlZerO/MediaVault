<template>
  <div class="header-component">
    <div class="header-component-navbar">
      <div class="header-component-navbar-name">
        <div class="header-component-navbar-centered">
          <p class="header-component-navbar-name-media">
            Media
          </p>
          <p class="header-component-navbar-name-vault">
            Vault
          </p>
        </div>
      </div>
      <div class="header-component-navbar-links">
        <RouterLink to="/" class="navbar-text">Media</RouterLink>
        <RouterLink to="/" class="navbar-text">Movies</RouterLink>
        <RouterLink to="/" class="navbar-text">Series</RouterLink>
        <MediaSearchInput />
      </div>

      <div class="header-component-navbar-right">
        <div class="header-component-navbar-message-button">
        </div>
        <div class="dropdown">
          <div class="header-component-navbar-profile-button nav-button" id="dropdownMenuButton"
            data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-person-fill " style="font-size: 24px; color: var(--color-primary)"></i>
          </div>
          <ul class="dropdown-menu fade" aria-labelledby="dropdownMenuButton">
            <li style="cursor: pointer;"><a class="dropdown-item" :onclick="() =>
              Router.push('/user/profile')">Profile</a>
            </li>
            <li style="cursor: pointer;"><a class="dropdown-item" :onclick="() =>
              Router.push('/user/friends')">Friends</a></li>
            <li style="cursor: pointer;"><a class="dropdown-item">Settings</a></li>
            <li style="cursor: pointer;"><a class="dropdown-item" :onclick="() =>
              Router.push('/login')">Logout</a></li>
          </ul>
        </div>

      </div>
    </div>
  </div>

</template>



<script setup>

import { RouterLink, RouterView, useRouter } from 'vue-router';
import { motion } from 'motion-v';

const Router = useRouter();

</script>




<style>
.header-component {
  display: flex;
  width: 100vw;
  height: 65px;
  padding-top: 3rem;
  padding-bottom: 1.8rem;
  border-bottom: 0px solid #ffffff22;
}

.header-component-navbar {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-component-navbar-name {
  display: flex;
  align-items: center;
  color: #fff;
  font-family: var(--font-family-title);
  font-size: 1.5rem;
  margin-left: 1rem;
  opacity: 0;
}

.header-component-navbar-centered {
  display: flex;
  align-items: center;
  height: 50px;
}

.header-component-navbar-name-media {
  display: flex;
  font-weight: 600;
}

.header-component-navbar-name-vault {
  display: flex;
  font-weight: 400;
}

.header-component-navbar-profile-button {
  display: flex;
  align-items: center;
  justify-content: center;


  border-radius: 100px;
  width: 35px;
  height: 35px;
  margin-right: 1.2rem;
  margin-bottom: 0.1rem;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
}

.header-component-navbar-profile-button:hover {
  background-color: #ffffff55;
}

.header-component-navbar-right {
  display: flex;
  gap: 8px;
}

.header-component-navbar-links {
  display: flex;
  gap: 1.6rem;
  border-radius: 100px;
  padding: 0.1rem 0.8rem;
}

Router .header-component-navbar-links {
  color: #fff;
}

.router-link-active {
  color: #ffffffdd;
  font-size: 1.1rem;
  font-weight: 500;
  text-decoration: none;
  font-family: var(--font-family-main);
}

.router-link-active:hover {
  color: #aaaaaa;
  background-color: #00000000 !important;
}

.navbar-text {
  color: #ffffffdd;
  font-size: 1.1rem;
  font-weight: 500;
  text-decoration: none;
  font-family: var(--font-family-main);
}

.navbar-text:hover {
  color: #aaaaaa;
  background-color: #00000000 !important;
  cursor: pointer;
}

.dropdown-menu {
  background-color: var(--color-background);
  border: 1px solid #ffffff44;
}


.dropdown-item {
  color: #fff;
}

.dropdown-item:hover {
  background-color: var(--color-background);
  color: var(--color-primary);
}


.header-component-navbar-message-button {
  width: 100px;
}
</style>
