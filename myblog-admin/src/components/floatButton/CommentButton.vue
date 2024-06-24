<template>
  <div>
    <el-tooltip class="item" effect="light" content="去评论" placement="bottom">
      <div class="m-div-fixed-commet m-comment-position" ref="commetButton">
        <button
          class="m-button-fixed-comment m-button-comment-img"
          v-on:mouseover="commentBtnActive()"
          v-on:mouseout="commentBtnRelease()"
          v-on:click="commentBtnClick()"
          v-bind:style="commentBtnColorAttr"
        >
          <span
            ><svg
              t="1650867417689"
              class="m-button-comment"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="3336"
              width="28"
              height="28"
            >
              <path
                d="M512 0C226.742857 0 0 197.485714 0 446.171429c0 138.971429 73.142857 270.628571 190.171429 351.085714L190.171429 1024l226.742857-138.971429c29.257143 7.314286 65.828571 7.314286 95.085714 7.314286 285.257143 0 512-197.485714 512-446.171429C1024 197.485714 797.257143 0 512 0zM256 512C219.428571 512 190.171429 482.742857 190.171429 446.171429S219.428571 380.342857 256 380.342857c36.571429 0 65.828571 29.257143 65.828571 65.828571S292.571429 512 256 512zM512 512C475.428571 512 446.171429 482.742857 446.171429 446.171429S475.428571 380.342857 512 380.342857c36.571429 0 65.828571 29.257143 65.828571 65.828571S548.571429 512 512 512zM768 512C731.428571 512 702.171429 482.742857 702.171429 446.171429s29.257143-65.828571 65.828571-65.828571c36.571429 0 65.828571 29.257143 65.828571 65.828571S804.571429 512 768 512z"
                p-id="3337"
                :fill="commentBtnColorAttr"
                class="m-button-comment-fill"
              ></path></svg
          ></span>
        </button></div
    ></el-tooltip>
  </div>
</template>

<script>
export default {
  data() {
    return {
      commentBtnColorAttr: "#8a8a8a",
      commentBtnStatus: true,
      blogId: "",
    };
  },
  methods: {
    commentBtnClick() {
      //跳转至评论区
      document
        .querySelector("#m-comment-write")
        .scrollIntoView({ behavior: "smooth" });
    },
    commentBtnActive() {
      this.commentBtnColorAttr = "#31c3e6";
    },
    commentBtnRelease() {
      this.commentBtnColorAttr = "#8a8a8a";
    },
    loadCommentBtn() {},
  },
  created() {
    this.loadCommentBtn();
  },
  mounted() {
    setTimeout(() => {
      this.$refs.commetButton.setAttribute('style', 'opacity: 1')
    }, 1500);
  }
};
</script>

<style>
.m-div-fixed-commet {
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
  opacity: 0;
}

.m-button-fixed-comment {
  border: 0;
  width: 40px;
  height: 40px;
  background-size: 100%;
  margin-top: 2px;
  background-color: rgb(0 0 0 / 0%);
  /*动画*/
  transition: all 1s ease 0s;
  -o-transition: all 1s ease 0s;
  -moz-transition: all 1s ease 0s;
  -webkit-transition: all 1s ease 0s;
  cursor: pointer;
}
.m-div-fixed-commet:hover {
  background-color: #f2f6fc;
}
/*评论按钮*/
.m-comment-position {
  top: 57%;
}
.m-button-comment {
  overflow: hidden;
}
.m-button-comment-fill {
  transition: all 0.3s ease 0s;
  -o-transition: all 0.3s ease 0s;
  -moz-transition: all 0.3s ease 0s;
  -webkit-transition: all 0.3s ease 0s;
}
/*根据屏幕宽度来调整按钮位置*/
@media (max-width: 1800px) {
  .m-div-fixed-commet {
    right: 240px;
  }
}
@media (max-width: 1600px) {
  .m-div-fixed-commet {
    right: 140px;
  }
}
@media (max-width: 1400px) {
  .m-div-fixed-commet {
    right: 40px;
  }
}
</style>