<template>
  <div class="m-comment-write-box">
    <h4 class="m-comment-write-title">发表评论</h4>
    <el-input
      type="textarea"
      placeholder="请输入内容"
      v-model="commentDto.content"
      :autosize="{ minRows: 6, maxRows: 6 }"
      maxlength="1500"
      show-word-limit
      class="m-comment-write-input"
    >
    </el-input>
    <div class="m-comment-write-button-box">
      <el-button
        type="primary"
        plain
        class="m-comment-write-button"
        @click="submitComment"
        >评论</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      commentDto: {
        userId: "",
        blogId: "",
        username: "",
        avatar: "",
        content: "",
      },
    };
  },
  methods: {
    updateShowPage() {
      this.$parent.updateShowPage();
    },
    submitComment() {
      let user = this.$store.getters.getUser;
      if (user !== null) {
        this.commentDto.userId = user.id;
        this.commentDto.username = user.username;
        this.commentDto.avatar = user.avatar;
        this.commentDto.blogId = this.$route.params.blogId;
        console.log(this.commentDto);
        const _this = this;
        //发送清求
        this.$axios({
          url: "/comment/add",
          data: this.commentDto,
          method: "post",
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        }).then((res) => {
          //刷新展示页面
          _this.updateShowPage();
          //清除书写的评论
          _this.commentDto.content = "";
        });
      } else {
        //如果用户未登录
        this.$message({
          message: "请先登录",
          type: "warning",
          duration: 2 * 1000, //停留两秒
          showClose: true,
        });
      }
    },
  },
};
</script>

<style>
.m-comment-write-box {
  margin-left: 15px;
  margin-right: 15px;
}
.m-comment-write-title {
  float: left;
  margin: 10px 4px 0px;
}

.m-comment-write-input {
  padding-top: 10px;
}
.m-comment-write-button-box {
  display: flex;
  justify-content: right;
}
.m-comment-write-button {
  margin: 15px 10px 10px 15px !important;
  display: block !important;
}
</style>