<template>
  <div class="m-header">
    <div class="m-header-box">
      <div class="m-header-left">
        <!--添加Logo  -->
        <div class="m-header-logo-box">
          <img
            class="m-header-logo"
            src="https://6666-1300801489.cos.ap-nanjing.myqcloud.com/2022-05-23/favicon.ico"
          />
        </div>
        <!-- 文字按钮 -->
        <div class="m-header-option-word">
          <router-link to="/`blogs`">首页</router-link>
        </div>
        <div class="m-header-option-word">
          <router-link to="/blog/add">写博客</router-link>
        </div>
        <div class="m-header-option-word">
          <router-link to="/classic">分类</router-link>
        </div>
      </div>
      <div class="m-header-right">
        <!-- 搜索框 -->
        <!-- 搜索框最外边框，动画变化的边框，从圆形变成长条形圆角 -->
        <div class="m-search-box" @mouseover="seachboxHover()" ref="searchBox">
          <!-- 左侧搜索框 -->
          <div class="m-search-box-key">
            <input
              type="text"
              placeholder="搜索关键词..."
              v-model="searchContent"
              @keyup.enter="toSearch"
            />
          </div>
          <!-- 右侧搜索按钮 -->
          <div class="m-search-box-bt" ref="searchBtn">
            <router-link to="" @click.native="toSearch()">
              <i class="search-iconfont search-icon-sousuo"></i>
            </router-link>
          </div>
        </div>
        <!-- 登录按钮 -->
        <div v-show="!isLogin">
          <el-button size="mini" @click="toLogin" plain>登录</el-button>
        </div>
        <div v-show="isLogin" class="m-header-option-avatar">
          <el-avatar
            :size="50"
            :src="user.avatar"
            class="m-header-avatar"
          ></el-avatar>
        </div>
        <!-- 用户名 -->
        <div v-show="isLogin" class="m-header-option-username">
          <router-link :to="'/user/' + user.username">{{
            user.username
          }}</router-link>
        </div>
        <!-- 退出登录按钮 -->
        <div v-show="isLogin">
          <el-button size="mini" @click="logout" plain>退出登录</el-button>
        </div>
        <!-- 切换背景 -->
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <svg
              t="1653380670990"
              class="m-header-more"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="3991"
              width="25"
              height="25"
              fill="#515151"
            >
              <path
                d="M93.866667 213.333333m-42.666667 0a42.666667 42.666667 0 1 0 85.333333 0 42.666667 42.666667 0 1 0-85.333333 0Z"
                p-id="3992"
              ></path>
              <path
                d="M940.8 245.333333H294.4c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h646.4c17.066667 0 32 14.933333 32 32s-14.933333 32-32 32z"
                p-id="3993"
              ></path>
              <path
                d="M93.866667 512m-42.666667 0a42.666667 42.666667 0 1 0 85.333333 0 42.666667 42.666667 0 1 0-85.333333 0Z"
                p-id="3994"
              ></path>
              <path
                d="M940.8 544H294.4c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h646.4c17.066667 0 32 14.933333 32 32s-14.933333 32-32 32z"
                p-id="3995"
              ></path>
              <path
                d="M93.866667 810.666667m-42.666667 0a42.666667 42.666667 0 1 0 85.333333 0 42.666667 42.666667 0 1 0-85.333333 0Z"
                p-id="3996"
              ></path>
              <path
                d="M940.8 842.666667H294.4c-17.066667 0-32-14.933333-32-32s14.933333-32 32-32h646.4c17.066667 0 32 14.933333 32 32 0 19.2-14.933333 32-32 32z"
                p-id="3997"
              ></path>
            </svg>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="changeBackground(1)"
              >背景一</el-dropdown-item
            >
            <el-dropdown-item @click.native="changeBackground(2)"
              >背景二</el-dropdown-item
            >
             <el-dropdown-item @click.native="changeBackground(3)"
              >背景三</el-dropdown-item
            >
            <el-dropdown-item @click.native="changeBackground(4)"
              >背景四</el-dropdown-item
            >
            <el-dropdown-item @click.native="changeBackground(5)"
              >背景五</el-dropdown-item
            >
            <el-dropdown-item @click.native="changeBackground(6)"
              >背景六</el-dropdown-item
            >
             <el-dropdown-item @click.native="changeBackground(0)"
              >背景七</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLogin: false,
      user: {
        username: "",
        avatar: "",
      },
      searchContent: "",
    };
  },
  methods: {
    toLogin() {
      this.$router.push("/login");
    },
    //退出登录
    logout() {
      const _this = this;
      this.$axios
        .get("/logout", {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((res) => {});
      _this.$store.commit("REMOVE_USERINFO");
      _this.$router.push("/login");
    },
    seachboxHover() {
      this.$refs.searchBox.classList.add("active");
    },
    seachboxOut() {
      this.$refs.searchBox.classList.remove("active");
    },
    //跳转搜索页面
    toSearch() {
      if (this.searchContent !== null && this.searchContent.length !== 0) {
        this.$router.push("/search/" + this.searchContent);
      }
    },
    //切换背景图片
    changeBackground(index) {
       //存储序号
       this.$store.commit("SET_BACKGROUND", index);
      //如果是零就切换css背景，其他就换成图片
      if (index == 0) {
        this.$parent.$parent.enableStarBackground();
      } else {
        this.$parent.$parent.enableImgBackground(index);
      }
    },
  },
  created() {
    let user = localStorage.getItem("userInfo");
    if (this.$store.getters.getUser.username) {
      this.user.username = this.$store.getters.getUser.username;
      this.user.avatar = this.$store.getters.getUser.avatar;
      this.isLogin = true;
    } else {
      this.isLogin = false;
    }
  },
  mounted() {
    //加载背景图
    console.log(this.$store.getters.getBackgroundId)
    this.changeBackground(this.$store.getters.getBackgroundId);
  }
};
</script>

<style scoped>
.m-header {
  width: auto;
  border-width: 0px 0px 1px 0px;
  border-style: solid;
  border-color: #f0f0f0;
  background: #ffffff;
  position: fixed;
  top: 0px;
  margin: 0;
  right: 0;
  left: 0;
  z-index: 1000;
}
.m-header-box {
  margin: 0 auto;
  width: 800px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.m-header-left {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.m-header-logo {
  height: 60px;
  width: auto;
}
.m-header-logo-box {
  width: 60px;
  height: 60px;
  margin: 0px 30px;
}
.m-header-right {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.m-header-option-word {
  width: 80px;
  height: 40px;
  line-height: 40px;
}
.m-header-option-word a {
  color: black;
  font-size: 15px;
  font-weight: bold;
}
.m-header-option-word a:hover {
  color: #80d4ff;
}
.m-header-option-avatar {
  margin-top: 10px;
}
.m-header-option-username {
  margin: 0 10px;
  height: 40px;
}
.m-header-option-username a {
  color: black;
  font-size: 15px;
  font-weight: bold;
  line-height: 40px;
}
.m-header-option-username a:hover {
  color: #80d4ff;
}

:root {
  --background-color: #2c3e50;
  --border-color: #34495e;
  --text-color: #7591ad;
  --color1: #ec3e27;
  --color2: #fd79a8;
  --color3: #277dfd;
  --color4: #00b894;
  --color5: #fdcb6e;
  --color6: #e056fd;
  --color7: #f97f51;
  --color8: #bdc581;
  --color9: #ffffff;
}

/* 原教程中使用的是第三方库 */

/* 这里使用iconfont.cn的图标导出 */

@font-face {
  font-family: "iconfont";
  src: url("data:application/x-font-woff2;charset=utf-8;base64,d09GMgABAAAAAAKsAAsAAAAABnAAAAJeAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHEIGVgCCcAp8gRgBNgIkAwgLBgAEIAWEbQcwG74FEdWTB2Q/DuyY8FcY0Z8Dxdjg34Pn37W3+5LMb0qoAFARg6qrQ14wdv8cv+DJEUhAt3rM2ozizqgIRZOMB/9VUzbQw+RyXwLAiMNp87wLZP55l8sYg8aaNKmL4jiQAhxrUWRFEnnD2AUu4TgECCKRfKSppWsIi8EaJ4Bsra/OY3NeDIYlLIKvZqlBbnGw6ky9hJvw+/K7VCwoHI1V0rfSvETdt0I7Re96u0z5BHwgoNUOoIF8wIAM1UZ70Aija4KihlTYV6HAt8zzfAuyVyPC/jorPwSAeCi6J7EruVVuBYCB7pAK1I66DXRQSMjOw92YmAfncSPv7XfvRt+8GX79eujt25FIkBVAPryltjj3cfLjfy+Snz971t7+ol0duO6BOlR+VwU013/AJ6kbh9r99cv15Bq2aDf9HeoA8HYNehcBBCrCyh7Nhlf9t8EGgE8hozuU9LOD3b9N4D+JezYYXWqZlVHFtUlFKvr8ZUEEAR7YGcXXsbrYm0oKlthbsweQkEJjSScaaj4cgimHD0stgsijdXUw0WyjERMI5HIEIETghyKMZ9BE8IpoqF/gEMdf+IgQjSBGJHrLYDJFW1yjojOM1P9pGeRkx2ouKrxhCnunWWnIeEH9EqAyK4qJFid0jhn+M1VmTKxyUANOw74LXSorBstms6vOc657UzbIEbmoUdEZRur/tAxyst/eXOnzN0xh77Slrsb7gvqldyqzogexVZ296u7lFf+ZKjMmVjmoAYOw70JX/aAVg2XziPBV53Yz7qvK1tccX7cDCIosvYrtfmnk8xJ8kCgCAAAA")
    format("woff2");
}

.search-iconfont {
  font-family: "iconfont" !important;
  font-size: 16px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.search-icon-sousuo:before {
  content: "\e62e";
}

.m-search-box {
  width: 50px;
  height: 50px;
  margin: auto 20px;
  border-radius: 60px;
  /* 垂直居中，左右居左 */
  display: flex;
  justify-content: flex-end;
  align-items: center;
  transition: 0.5s;
  background: #277dfd;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.m-search-box-key {
  /* flex布局中本区域的缩放比例为1份 */
  flex-shrink: 1;
  flex-grow: 1;
  /* background-color: violet; */
}

.m-search-box-key input {
  width: calc(100% - 40px);
  height: 40px;
  padding: 0 20px;
  font-size: 14px;
  color: #999;
  /* 边框背景为空 */
  background: none;
  border: none;
  outline: none;
}

.m-search-box-key input:focus {
  border: none;
  outline: none;
}

.m-search-box-key input::-webkit-input-placeholder {
  /* 提示文字的样式 */
  color: #999;
}

.m-search-box-bt {
  /* flex布局中本区域的缩放比例为0份，不缩放，保持原有大小 */
  flex-shrink: 0;
  flex-grow: 0;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  /* 搜索图标居中 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.m-search-box-bt a {
  padding: 10px;
  border-radius: 100%;
  text-decoration: none;
  color: #fff;
  /* background-color: violet; */
  transition: 0.5s;
}

.m-search-box.active {
  width: 200px;
  background-color: #ffffff;
}

.m-search-box.active .m-search-box-bt a {
  color: #fff;
  background-color: #277dfd;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
  margin-left: 10px;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.m-header-more:hover {
  fill: #31c3e6;
}
</style>