<template>
  <div>
    <div>
      <h1 class="m-blog-title">{{ blog.title }}</h1>
    </div>
    <div class="m-title-info-date">{{ blog.created }}</div>
    <div class="m-title-info-box">
      <div class="m-title-info-avatar">
        <el-avatar :size="45" :src="blog.avatar"></el-avatar>
      </div>
      <div class="m-title-info-word-box">
        <div class="m-title-info-top-box">
          <div class="m-title-info-username"><router-link :to="'/user/'+blog.username">{{ blog.username }}</router-link></div>
          <div class="m-title-show-more">
          <el-dropdown trigger="click" v-show="currentUser">
            <span class="el-dropdown-link m-title-show-button"> ··· </span>
            <el-dropdown-menu>
              <div class="m-title-more-button-item" @click.stop="toEditBlog">编辑</div>
              <div class="m-title-more-button-item" @click.stop="confirmDeletion">删除</div>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        </div>
        <div class="m-title-info-amount">
          <div class="m-title-info-item">{{ blog.praises }}点赞</div>
          <div class="m-title-info-item">{{ blog.views }}浏览</div>
          <div class="m-title-info-item">{{ blog.comments }}评论</div>
        </div>
      </div>
    </div>
    <div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      blog: {},
      currentUser: false,
    };
  },
  methods: {
    confirmDeletion() {
      const _this = this;
      this.$confirm("你确认删除博客吗, 删除后不可恢复?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //将用户信息和博客信息加入data中
          var formData = new FormData();
          //转为json格式传输
          formData.append("user", JSON.stringify(this.$store.getters.getUser));
          formData.append(
            "blog",
            JSON.stringify({
              id: _this.blog.id,
              userId: _this.blog.userId,
              title: _this.blog.title,
              content: " ",
            })
          );
          //console.log(formData);
          this.$axios({
            url: "/blog/delete",
            method: "DELETE",
            data: formData,
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          }).then((res) => {
            //如果删除成功弹窗提醒，失败会被拦截器拦截
            const result = res.data;
            if (result.code === 200) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              _this.$router.push("/blogs");
            } else {
            }
          });
        })
        .catch(() => {});
    },
    toEditBlog() {
        this.$router.push('/blog/edit/' + this.blog.id);
    },  
    loadTitle(blog) {
      this.blog = blog;
      // 判断是否是自己的文章，能否编辑
      //用户信息为空：未登录
      if (this.$store.getters.getUser) {
        this.currentUser = this.blog.userId === this.$store.getters.getUser.id;
      } else {
        this.currentUser = false;
      }
    },
    updatePraiseInfo(praiseStatus) {
      if (praiseStatus === true) {
        this.blog.praises = this.blog.praises + 1;
      } else {
        this.blog.praises = this.blog.praises - 1;
      }
    }
  },
};
</script>

<style>
.m-blog-title {
  text-align: left;
}
.m-delete-btn {
  color: #e84b39;
  cursor: pointer;
}

.m-title-info-box {
  display: flex;
  flex-direction: row;
}
.m-title-info-date {
  text-align: left;
  font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica,
    Arial, PingFang SC, Hiragino Sans GB, Microsoft YaHei, sans-serif;
  color: #9499a0;
  font-size: 13px;
  margin-bottom: 20px;
}
.m-title-info-top-box {
    display: flex;  
    flex-direction: row;
    justify-content: space-between;
}
.m-title-info-word-box {
  display: flex;
  flex-direction: column;
  margin-left: 20px;
    flex-grow: 1;
}
.m-title-info-amount {
  margin-top: 5px;
  display: flex;
  font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica,
    Arial, PingFang SC, Hiragino Sans GB, Microsoft YaHei, sans-serif;
  color: #9499a0;
  font-size: 13px;
}
.m-title-info-username {
  text-align: left;
  margin-left:0px;
  margin-top: 5px;
  font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica,
    Arial, PingFang SC, Hiragino Sans GB, Microsoft YaHei, sans-serif;
  color: #409eff;
  font-size: 14px;
}
.m-title-info-username:hover {
    cursor: pointer;
}
.m-title-info-item {
  margin-right: 5px;
}
.m-title-show-more {
    margin-right: 20px;
}
.m-title-show-button {
    font-size: 25px;
    color: #9499a0;
}
.m-title-more-button-item {
  width: 40px;
  text-align: center;
  font-size: 13px;
  font-weight: 500;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  margin: 5px 0px;
}
.m-title-more-button-item:hover {
  color: #409eff;
  cursor: pointer;
}
</style>