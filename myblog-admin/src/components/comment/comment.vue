<template>
  <div class="m-comment-box">
    <!-- 写评论 -->
    <comment-write id="m-comment-write"></comment-write>
    <!-- 评论列表 -->
    <comment-show ref="commentShow"></comment-show>
  </div>
</template>

<script>
import CommentShow from "./comment-show.vue";
import CommentWrite from "./comment-write.vue";
export default {
  components: {
    CommentShow,
    CommentWrite
  },
  methods: {
    start(blogId, userId) {
      this.$refs.commentShow.init(blogId, userId);
    },
    updateShowPage() {
      this.$refs.commentShow.updatePage();
    },
  }
}
</script>

<style scoped>
.m-comment-box {
  box-shadow: 0 0 6px rgb(0 0 0 / 20%);
  border-radius: 8px;
  background-color: white;
  }
</style>