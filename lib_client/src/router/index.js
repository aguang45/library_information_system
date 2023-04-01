import Vue from 'vue'
import VueRouter from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import login from '../views/Login.vue'
// import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: login
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    redirect: '/home/book-manage',
    children: [
      {
        path: 'user-manage',
        name: 'UserManage',
        component: () => import('../views/UserManage.vue'),
        meta: {
            requiresAdmin: true
        }
      },
      {
        path: 'borrow-manage',
        name: 'BorrowManage',
        component: () => import('../views/BorrowManage.vue'),
        meta: {
            requiresAdmin: true
        }
      },
      {
        path: 'book-manage',
        name: 'BookManage',
        component: () => import('../views/BookManage.vue'),
        
      },
      {
        path: 'my-borrow',
        name: 'MyBorrow',
        component: () => import('../views/MyBorrow.vue'),
      },
      {
        path: 'my-information',
        name: 'MyInformation',
        component: () => import('../views/MyInformation.vue'),
      },
    ]
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/404',
    name: '404',
    component: ()=>import('../views/404.vue')
  },
  {
    path: '*',
    redirect: '/404'
  }
]

// function requireAdmin(to, from, next) {
//   const user = store.getters.getUser
//   if (user.isAdmin) {
//     next()
//   } else {
//     next('/404')
//   }
// }



// router.beforeEach((to, from, next) => {
//   if (to.matched.some(record => record.meta.requiresAdmin)) {
//     // 如果需要管理员权限，则调用 requireAdmin 守卫
//     requireAdmin(to, from, next)
//   } else {
//     // 否则继续路由
//     next()
//   }
// })

const router = new VueRouter({
  routes
})

export default router
