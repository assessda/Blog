<template>
  <div id="app">
    <!-- 回到顶部 -->
    <el-backtop :bottom="60" class="m-totop"></el-backtop>
    <star-background v-show="starBackground"></star-background>
    <background ref="background" v-show="!starBackground"></background>
    <div class="m-content-display">
      <div class="m-router-view">
        <transition name="page" class="page">
          <router-view class="m-router-view" :key="$route.fullPath"/>
        </transition>
      </div>
      <footer-div></footer-div>
    </div>
  </div>
</template>
<script>
import StarBackground from "./components/background/StarBackground.vue";
import Background from "./components/background/Background.vue";
import FooterDiv from "./components/footer/Footer.vue";
export default {
  components: {
    StarBackground,
    FooterDiv,
    Background,
  },
  data() {
    return {
      /* 加载 */
      loading: true,
      starBackground: false,
    };
  },
  methods: {
    enableStarBackground() {
      this.starBackground = true;
      console.log("1111");
    },
    enableImgBackground(index) {
      this.starBackground = false;
      //更改图片
      this.$refs.background.changeBackground(index);
    },
  },
};
</script>

<style>
/*滚动框的设置*/
::-webkit-scrollbar {
  width: 5px;
  height: 100%;
  background: rgb(200, 200, 209);
  opacity: 0;
}

::-webkit-scrollbar-thumb {
  background-color: #96888a;
  border-radius: 15px;
}
::-webkit-scrollbar-thumb:hover {
  background-color: rgb(100, 126, 255);
  border-radius: 15px;
}

body {
  overflow-y: scroll;
  margin: 0 auto;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
a {
  text-decoration: none;
  color: #33bbff;
}
.router-link-active {
  text-decoration: none;
  color: #33bbff;
}
a:hover {
  text-decoration: underline;
  color: #80d4ff;
}
*::selection {
  background-color: #ccffcc;
}
.m-content {
  margin: 0 auto;
  text-align: center;
  max-width: 1000px;
}
.m-totop {
  margin-right: 300px;
  transition: all 1s ease 0s;
  -o-transition: all 1s ease 0s;
  -moz-transition: all 1s ease 0s;
  -webkit-transition: all 1s ease 0s;
}
/*根据屏幕宽度来调整按钮位置*/
@media (max-width: 1800px) {
  .m-totop {
    margin-right: 200px;
  }
}
@media (max-width: 1600px) {
  .m-totop {
    margin-right: 100px;
  }
}
@media (max-width: 1400px) {
  .m-totop {
    margin-right: 0px;
  }
}

.m-content-display {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}
.m-router-view {
  flex: 1;
}
textarea {
  font-family: Arial, Helvetica, sans-serif;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

/*转场动画*/
.page {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right:0;
}

.page-enter-active {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right:0;
  animation: aniEnt 1.5s; 
}

@keyframes aniEnt {
  0% {
    transform: translateX(100%);
  }

  100% {
    transform: translateX(0%);
  }
}

.page-leave-active {
  position: fixed;
  animation: aniLeave 1s;
  transform-origin: center center;
}

@keyframes aniLeave {
  0% {
    
  }

  100% {
    transform: translateX(-100%) rotateZ(-180deg) translateY(0%);
  }
}
</style>
