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
import {initMenu} from "@/utils/menus";
import 'font-awesome/css/font-awesome.css'
Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest=postRequest
Vue.prototype.putRequest=putRequest
Vue.prototype.deleteRequest=deleteRequest
Vue.config.productionTip = false
Vue.use(ElementUI);


// router.beforeEach((to, from, next) => {
//   if (to.path == '/') {
//     next();
//   } else {
//     if (!window.sessionStorage.getItem("")) {
//       console.log('123');
//       initMenu(router, store);
//       next();
//     } else {
//       next('/?redirect=' + to.path);
//     }
//   }
// })


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

router.beforeEach((to, from, next) => {
  if (window.sessionStorage.getItem('tokenStr')) {
    initMenu(router, store);
    //判断用户信息是否存在
    if (!window.sessionStorage.getItem('user')) {
      // console.log('123');
      return getRequest('/admin/info').then(resp=>{
        if (resp) {
          //存入用户信息
          window.sessionStorage.setItem('user', JSON.stringify(resp));
          next();
        }
      })
    }
    next();
  }else {
    if (to.path == '/') {
      next();
    }else {
      next('/?redirect=' + to.path);
    }
  }
});



