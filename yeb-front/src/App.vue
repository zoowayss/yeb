<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import router from './router'
import store from './store'
import {initMenu} from "@/utils/menus";
export default {
  name: "App",
  mounted() {
    if (window.sessionStorage.getItem('tokenStr')) {
      initMenu(router, store);
    }
  }
}
</script>

<style scoped>

</style>