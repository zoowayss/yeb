import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";
import Home from "@/views/Home";
import Test1 from "@/views/Test1";
import Test2 from "@/views/Test2";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden:true
  },
  {
    path:'/home',
    name: 'Home',
    component: Home,
    children:[
      {
        path: '/Test1',
        name: 'Test1',
        component: Test1
      },
      {
        path: '/Test2',
        name: 'Test2',
        component: Test2
      }
    ]
  },


]

const router = new VueRouter({
  // mode: 'history',
  routes
})

// 解决报错
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
// push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
  return originalReplace.call(this, location).catch(err => err)
}




export default router
