import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'markdown-it'
import 'highlight.js'
import 'markdown-it-highlightjs'

//自定义axios拦截
import axios from 'axios';
import './axios.js'
import './permission.js'
import './loadBar.js'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'


//引入element-ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);



Vue.prototype.$axios=axios;
Vue.config.productionTip = false
Vue.use(mavonEditor);
Vue.use(router);

NProgress.configure({     
  easing: 'ease',  // 动画方式    
  speed: 500,  // 递增进度条的速度    
  showSpinner: false, // 是否显示加载ico    
  trickleSpeed: 200, // 自动递增间隔    
  minimum: 0.3 // 初始化时的最小百分比
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
