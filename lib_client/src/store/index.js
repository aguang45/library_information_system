import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    flash: false,
    user: {},
    token: ''
  },
  getters: {
  },
  mutations: {
    setFlash(state, flash) {
      state.flash = flash
    },
    setUser(state, user) {
      state.user = user
    },
    setToken(state, token) {
      state.token = token
    }
  },
  actions: {
  },
  modules: {
  }
})
