import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    token: localStorage.getItem("token"),
    userInfo: JSON.parse(localStorage.getItem("userInfo")),
    serverhost: "http://localhost:80",
    backgroundId: localStorage.getItem("backgroundId"),
  },
  mutations: {
    //set
    //设置token
    SET_TOKEN: (state, token) =>  {
      state.token = token;
      localStorage.setItem("token", token);
    },
     //设置用户信息
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo;
      localStorage.setItem("userInfo",JSON.stringify(userInfo));
    },
     //移除信息
    REMOVE_USERINFO: (state) => {
      state.userInfo = '';
      state.token = '';
      localStorage.removeItem("userInfo");
      localStorage.removeItem("token");
    },
    //设置token
    SET_BACKGROUND: (state, id) =>  {
      state.backgroundId = id;
      localStorage.setItem("backgroundId", id);
    },
  },
    //获取信息
  getters: {
    //get
    getUser: (state) => {
      return state.userInfo;
    },
    getBackgroundId: (state) => {
      return state.backgroundId;
    }
  },
  actions: {},
  modules: {}
})