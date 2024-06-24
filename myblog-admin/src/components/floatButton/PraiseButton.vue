<template>
  <!-- 点赞按钮 -->
  <div>
    <el-tooltip class="item" effect="light" content="点赞" placement="bottom">
      <div class="m-div-fixed m-praise-position" ref="praiseButton">
        <button
          class="m-button-fixed"
          v-on:click="clickPraise()"
          
        ><!-- v-bind:style="praiseColorAttr" -->
        <span>
          <svg
            t="1650866174774"
            class="m-button-priase"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="2314"
            width="30"
            height="30"
          >
            <path
              d="M884.875894 423.143253 646.970506 423.143253c92.185562-340.464205-63.516616-357.853247-63.516616-357.853247-65.993017 0-52.312436 52.182476-57.3031 60.881602 0 166.502152-176.849824 296.971645-176.849824 296.971645l0 472.171899c0 46.607504 63.516616 63.393819 88.433098 63.393819l357.452111 0c33.641191 0 61.036122-88.224344 61.036122-88.224344 88.434122-300.70569 88.434122-390.177444 88.434122-390.177444C944.657442 418.179195 884.875894 423.143253 884.875894 423.143253L884.875894 423.143253 884.875894 423.143253zM884.875894 423.143253M251.671415 423.299819 109.214912 423.299819c-29.420053 0-29.873378 28.89612-29.873378 28.89612l29.420053 476.202703c0 30.309306 30.361495 30.309306 30.361495 30.309306L262.420223 958.707948c25.686009 0 25.458835-20.049638 25.458835-20.049638L287.879058 459.411271C287.879058 422.837284 251.671415 423.299819 251.671415 423.299819L251.671415 423.299819 251.671415 423.299819zM251.671415 423.299819"
              p-id="2315"
              :fill="praiseColorAttr"
              class="m-button-praise-fill"
            ></path>
          </svg>
          </span>
        </button></div
    ></el-tooltip>
  </div>
</template>

<script>
export default {
  data() {
    return {
      praiseStatus: true,
      praiseColorAttr: "#8a8a8a",
      blogId: "",
    };
  },
  methods: {
    clickPraise() {
      const _this = this;
      //如果用户未登录
      if (this.$store.getters.getUser === null) {
        this.$message({
          message: "请先登录",
          type: "warning",
          duration: 2 * 1000, //停留两秒
          showClose: true,
        });
      } else {
        if (this.praiseStatus === false) {
          this.$axios
            .get("/praise/add/" + this.blogId, {
              params: {
                id: this.$store.getters.getUser.id,
              },
            })
            .then((res) => {
              _this.praiseStatus = true;
              _this.praiseColorAttr = "#31c3e6";
              //更新点赞信息
              this.updatePraiseInfo();
            });
        } else {
          this.$axios
            .get("/praise/cancel/" + this.blogId, {
              params: {
                id: this.$store.getters.getUser.id,
              },
            })
            .then((res) => {
              _this.praiseStatus = false;
              _this.praiseColorAttr = "#8a8a8a";
              //更新点赞信息
              this.updatePraiseInfo();
            });
        }
      }
    },
    loadButton(blogId) {
      const _this = this;
      this.blogId = blogId;
      //如果用户未登录为空
      if (this.$store.getters.getUser === null) {
        return;
      }
      //判断是否点过点赞
      this.$axios
        .get("/praise/getPraiseStatus", {
          params: {
            id: _this.$store.getters.getUser.id,
            blogId: this.blogId,
          },
        })
        .then((res) => {
          //改变按钮状态
          _this.praiseStatus = res.data.data.praiseStatus;
          //将点赞信息注入，并改变样式
          if (this.praiseStatus === false) {
            this.praiseColorAttr = "#8a8a8a";
          } else {
            this.praiseColorAttr = "#31c3e6";
          }
        });
    },
    updatePraiseInfo() {
      this.$parent.updatePraiseInfo(this.praiseStatus);
    },
  },
  created() {
    
  },
   mounted() {
    setTimeout(() => {
      this.$refs.praiseButton.setAttribute('style', 'opacity: 1')
    }, 1500);
  }
};
</script>

<style>
.m-div-fixed {
  width: 40px;
  height: 40px;
  background-color: white;
  border-radius: 50px;
  box-shadow: 0 0 6px rgb(0 0 0 / 12%);
  /*定位*/
  position: fixed;
  right: 340px;
  overflow: hidden;
  transition: all 1s ease 0s;
  -o-transition: all 1s ease 0s;
  -moz-transition: all 1s ease 0s;
  -webkit-transition: all 1s ease 0s;

  /*位于顶层*/
  z-index: 100;
  /*开始设置全透明*/
  opacity: 0;
}
.m-button-fixed {
  border: 0;
  width: 40px;
  height: 40px;
  background-size: 100%;
  background-color: rgb(0 0 0 / 0%);
  /*动画*/
  transition: all 0.5s ease 0s;
  -o-transition: all 0.5s ease 0s;
  -moz-transition: all 0.5s ease 0s;
  -webkit-transition: all 0.5s ease 0s;
  cursor: pointer;
}
.m-div-fixed:hover {
  background-color: #f2f6fc;
}
/*点赞按钮*/
.m-praise-position {
  top: 50%;
}
.m-button-praise {
  overflow: hidden;
}
.m-button-praise-fill {
transition: all 0.3s ease 0s;
  -o-transition: all 0.3s ease 0s;
  -moz-transition: all 0.3s ease 0s;
  -webkit-transition: all 0.3s ease 0s;
}
/*根据屏幕宽度来调整按钮位置*/
@media (max-width: 1800px) {
  .m-div-fixed {
    right: 240px;
  }
}
@media (max-width: 1600px) {
  .m-div-fixed {
    right: 140px;
  }
}
@media (max-width: 1400px) {
  .m-div-fixed {
    right: 40px;
  }
}
</style>