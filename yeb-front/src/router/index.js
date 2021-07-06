import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login";
import store from "@/store";
import {initMenu} from "@/utils/menus";

Vue.use(VueRouter)


//解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
//push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
//replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
  return originalReplace.call(this, location).catch(err => err)
}




const routes = [
  {
    path: '/',
    name:Login,
    component:Login,
    hidden:true,
  }
]

const router = new VueRouter({
  routes,
})
router.beforeEach((to, from, next)=> {
      if (to.path == '/') {
        next();
        return;
      }
      var name = store.state.user.name;
      if (name == '未登录') {
        next({path: '/', query: {redirect: to.path}})
      } else {
        initMenu(router, store);
        next();
      }
    }
)

export default router
