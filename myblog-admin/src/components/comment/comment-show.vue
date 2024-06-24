<template>
  <div class="m-comment-list-box" v-if="updateShowBox">
    <div v-for="comment in allComments">
      <!-- 分割线 -->
      <el-divider class="m-comment-divider"></el-divider>
      <!-- 评论项 -->
      <div class="m-comment-show-box">
        <div class="m-comment-avatar-box">
          <el-avatar :size="39" :src="comment.avatar"></el-avatar>
        </div>
        <div class="m-comment-show-content-box">
          <div class="m-comment-show-box-info">
            <div class="m-comment-show-box-username-date">
              <div class="m-comment-show-username">{{ comment.username }}</div>
              <div class="m-comment-show-date">{{ comment.created }}</div>
            </div>
            <div class="m-comment-show-more-box">
              <el-dropdown trigger="click">
                <span class="el-dropdown-link m-button-more"> ··· </span>
                <el-dropdown-menu>
                  <div
                    class="m-comment-more-button"
                    @click.stop="deleteComment(comment.id)"
                    v-show="
                      comment.userId === userId || userId === blogAuthorId
                    "
                  >
                    删除
                  </div>
                  <div class="m-comment-more-button">更多</div>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
          <div class="m-comment-show-content">
            {{ comment.content }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId: "",
      blogAuthorId: "",
      comment: {
        id: "",
        userId: "",
        blogId: "",
        username: "",
        avatar: "",
        content: "",
        created: "",
      },
      allComments: [],
      updateShowBox: true,
    };
  },
  methods: {
    init(blogId, userId) {
      const _this = this;
      //将信息导入
      this.blogId = blogId;
      this.blogAuthorId = userId;
      //判断用户是否为空
      if (this.$store.getters.getUser !== null) {
        this.userId = this.$store.getters.getUser.id;
      }
      this.$axios({
        url: "/comment/get/" + blogId,
        method: 'get'
      }).then((res) => {
        _this.allComments = res.data.data;
      });
    },
    updatePage() {
      this.updateShowBox = false;
      this.$nextTick(() => {
        //在组件刷新后
        this.updateShowBox = true;
        this.init(this.blogId, this.blogAuthorId);
      });
    },
    deleteComment(id) {
      const _this = this;
      this.$axios({
        url: "/comment/delete/" + id,
        data: this.$store.getters.getUser,
        method: "delete",
        headers: {
          Authorization: localStorage.getItem("token"),
        },
      }).then((res) => {
        /* 刷新 */
        _this.updatePage();
      });
    },
  },
};
</script>

<style scoped>
.m-comment-list-box {
  margin-bottom: 20px;
  
}
.m-comment-show-box {
  display: flex;
  flex-direction: row;
  margin: 0px 12px;
}
.m-comment-avatar-box {
  width: 60px;
}
.m-comment-show-content-box {
  flex-grow: 1;
  margin-left: 10px;
  margin-bottom: 10px;
}
.m-comment-show-box-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  /*解决数字和中文对不齐*/
  height: 40px;
  line-height: 41px;
}
.m-comment-show-box-username-date {
  display: flex;
  flex-direction: row;
}
.m-comment-show-username {
  font-family: "lucida sans unicode", "lucida grande", sans-serif;
  color: #409eff;
  font-size: 15px;
}
.m-comment-show-date {
  margin-left: 10px;
  font-family: courier, "courier new", monospace;
  color: #9499a0;
  font-size: 15px;
}
.m-comment-show-content {
  text-align: left;
}
.m-comment-show-more-box {
  margin-right: 30px;
}
.m-comment-more-button {
  width: 40px;
  text-align: center;
  font-size: 13px;
  font-weight: 500;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  margin: 5px 0px;
}
.m-comment-more-button:hover {
  color: #409eff;
  cursor: pointer;
}
/*分割线*/
.m-comment-divider {
  width: 98% !important;
  margin: 10px 0px 10px 10px !important;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
/*更多下拉框样式*/
.m-button-more {
  font-size: 20px;
  font-weight: bolder;
  color: #9499a0;
}
.m-button-more:hover {
  color: #6bc7ff;
}
</style>