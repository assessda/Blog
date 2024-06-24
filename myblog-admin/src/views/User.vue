<template>
  <div>
    <Header></Header>
    <loading ref="loading"></loading>
    <div class="m-user-content-box">
      <!-- 用户信息展示 -->
      <user-info ref="userInfo"></user-info>
      <!-- 用户博客信息 -->
      <div class="m-user-list-box">
        <user-content-list ref="userContentList"></user-content-list>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import UserInfo from "../components/user/UserInfo.vue";
import UserContentList from "../components/user/UserContentList.vue";
import Loading from "../components/loading/Loading.vue";
export default {
  components: {
    Header,
    UserInfo,
    UserContentList,
    Loading,
  },
  data() {
    return {
      user: "",
    };
  },
  methods: {
    async loadComponents() {
      return new Promise((resolve, reject) => {
        //加载userContentList
        var promise1 = this.$refs.userContentList.loadListByUser(this.$route.params.username);
        //加载UserInfo
        var promise2 = this.$refs.userInfo.loadUserInfo();
        Promise.all([promise1, promise2]).then(res=> {
          resolve();
        })
      });
    },
    startLoading() {
      this.$refs.loading.showLoading();
    },
    hiddenLoading() {
      this.$refs.loading.hiddenLoading();
    },
    async init() {
      //开始加载
      this.startLoading();
      let _this = this;
      //加载组件
        this.loadComponents().then((res) => {
        //关闭加载
        _this.hiddenLoading();
      });
    },
  },
  mounted() {
    //初始化
    this.init();
  },
};
</script>

<style lang="css" scoped>
.m-user-content-box {
  display: flex;
  margin: 0 auto;
  margin-top: 80px;
  width: 800px;
  height: 620px;
}
.m-user-list-box {
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  margin-left: 10px;
  width: 540px;
}
</style>