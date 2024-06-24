import Vue from 'vue'
import VueRouter from 'vue-router'
import Blogs from '../views/Blogs.vue'
import BlogDetail from '../views/BlogDetail.vue'
import BlogEdit from '../views/BlogEdit.vue'
import Regist from '../views/Regist.vue'
import Classic from '../views/Classic.vue'
import LabelBlog from '../views/LabelBlog.vue'
import User from '../views/User.vue'
Vue.use(VueRouter)

const routes = [ 
  {
    path: '/login',
    name: 'Login',
    component: () => import ('../views/Login.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: Regist
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  }, 
  {
    path: '/',
    name: 'Blogs',
    component: Blogs
  }, 
  {
    path: '/blog/edit/:blogId',
    name: 'BlogEdit',
    meta: {
      requireAuth: true
    },
    component: BlogEdit
  },
  {
    path: '/blog/add',
    name: 'BlogEdit',
    component: BlogEdit,
    meta: {
      requireAuth: true
    },
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/regist',
    name: 'Regist',
    component: Regist
  },
  {
    path: '/classic',
    name: 'Classic',
    component: Classic
  },
  {
    path: '/classic/:labelName',
    name: 'LabelBlog',
    component: LabelBlog
  },
  {
    path: '/user/:username',
    name: 'User',
    component: User
  },
  {
    path: '/update/user',
    name: 'UpdateUserInfo',
    component: () => import ('../views/UpdateUserInfo.vue')
  },
  {
    path: '/update/password',
    name: 'UpdatePassword',
    component: () => import ('../views/UpdatePassword.vue')
  },
  {
    path: '/search/:contentSearch',
    name: 'BlogSearch',
    component: () => import ('../views/BlogSearch.vue')
  },
  {
    path: '*',
    name: 'NotFound',
    component: () => import ('../views/NotFound.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
