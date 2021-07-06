import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    routes:[],
    user: {
      name: window.sessionStorage.getItem('user' || '[]') == null ? '未登录' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).name,
      userFace: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).userFace
    }
  },
  mutations: {
    initRoutes(state,data){
      state.routes=data;
    },
    initUser(state, user) {
      window.sessionStorage.setItem('user', JSON.stringify(user));
      state.user = user;
    }
  },
  actions: {

  },
  modules: {
  }
})
