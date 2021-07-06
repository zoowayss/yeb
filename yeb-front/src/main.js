import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {getRequest} from "@/utils/api";
import {postRequest} from "@/utils/api";
import {putRequest} from "@/utils/api";
import {deleteRequest} from "@/utils/api";
import 'font-awesome/css/font-awesome.css'
import {downloadRequest} from "@/utils/download";
Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest=postRequest
Vue.prototype.putRequest=putRequest
Vue.prototype.deleteRequest=deleteRequest
Vue.prototype.downloadRequest = downloadRequest;
Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

// router.beforeEach((to,from,next)=>{
//   if (window.sessionStorage.getItem('tokenStr')) {
//     // 已经登录
//     initMenu(router,store);
//     next();
//
//   }else {
//     console.log(123);
//     //未登录  这里是 logout 之后 未登录，去 / 要放行
//     if (to.path == '/') {
//       next();
//     }else {
//       next('/?redirect='+to.path);
//     }
//
//   }
// })




