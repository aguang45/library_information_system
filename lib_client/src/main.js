import Vue from 'vue'
import App from './App.vue'
// import App from './views/Home.vue'
// import App from './views/UserManage.vue'
// import App from './views/BookManage.vue'
// import App from './views/BorrowManage.vue'
// import App from './views/MyBorrow.vue'
// import App from './views/MyInformation.vue'
import router from './router'
import store from './store'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import axios from 'axios'


Vue.config.productionTip = false

Vue.use(ElementUI)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

// axios.defaults.baseURL = 'http://localhost:8080/api'

// 添加拦截器
axios.interceptors.request.use(
  config => {
    // 从本地存储中获取Token
    const token = localStorage.getItem('token')
    // 将Token添加到请求头中
    if (token) {
      config.headers.Authorization = 'Bearer ' + token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response.status === 401) {
      // 清除本地存储中的Token
      localStorage.removeItem('token')
      // 清除 Vuex 中的 Token
      store.commit('setToken', null)
      // 跳转到登录页面
      router.push('/404')
    }
    return Promise.reject(error)
  }
)

