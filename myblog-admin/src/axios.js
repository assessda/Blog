import axios from 'axios'
import ElementUI from 'element-ui'
import store from './store'
import router from './router'

axios.defaults.baseURL="http://120.79.149.62:3333";


//前置拦截
axios.interceptors.request.use(config => {
    
    return config;
}, error=> {
    
})

axios.interceptors.response.use(response => {
    const res = response.data;
    //如果状态码正常 code=200
    if (response.data.code === 200 || response.data.code === 201) {
        return response;
    } else {
        ElementUI.Message({
            message: response.data.msg,
            type: 'error',
            duration: 2 * 1000, //停留两秒
            showClose: true
        });
        return Promise.reject(response.data.msg);
    }
},
error => {
    console.log('err' + error);
    if (error.response.data) {
        error.message = error.response.data.msg;
    }
    //根据状态确认是否重新登录
    if (error.response.status === 401) {
        store.commit('REMOVE_USERINFO');
        router.push({
            path: '/login'
        });
        error.message = "登录失效，请重新登陆！";
    }
    if (error.response.status === 403) {
        error.message = "权限不足！"
    }
    ElementUI.Message({
        message: error.message,
        type: 'error',
        duration: 2 * 1000,
        showClose: true
    });
    return Promise.reject(error);
}
)