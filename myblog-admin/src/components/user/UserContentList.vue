<template>
  <div>
    <div class="m-contentlist-box">
      <div v-for="(blog, i) in blogs">
        <div class="m-contentlist-title">
          <router-link
            :to="{ name: 'BlogDetail', params: { blogId: blog.id } }"
            >{{ blog.title }}</router-link
          >
        </div>
        <div class="m-contentlist-description">
          {{ blog.description }}
        </div>
        <div class="m-contentlist-label-box">
          <div
            class="m-contentlist-label"
            v-for="label in blog.labels"
            @click="toLabelBlog(label)"
          >
            {{ label }}
          </div>
        </div>
        <div class="m-contentlist-footer">
          <div class="m-contentlist-footer-left">
            <div class="m-contentlist-date">{{ blog.created }}</div>
          </div>
          <div class="m-contentlist-infolist">
            <div class="m-info">
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">点赞数</div>
              </div>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">{{ blog.praises }}</div>
              </div>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">浏览量</div>
              </div>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">{{ blog.views }}</div>
              </div>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">评论数</div>
              </div>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">{{ blog.comments }}</div>
              </div>
            </div>
          </div>
        </div>
        <!-- 分割线 -->
        <el-divider
          class="m-comment-divider"
          v-if="i !== blogs.length - 1"
        ></el-divider>
      </div>
      <!-- 如果没有内容 -->
      <div
        v-show="blogs === null || blogs.length === 0"
        class="m-contentlist-null"
      >
        暂无内容
      </div>
    </div>
    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :current-page="currentPage"
      :page-size="pageSize"
      @current-change="setPage"
      v-show="pages > 1"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      username: "admin",
      blogs: {},
      blog: {
        id: "",
        username: "",
        title: "",
        description: "",
        content: "",
        created: "",
        status: "",
        praises: 0,
        comments: 0,
        views: 0,
        labels: [],
      },
      currentPage: 1,
      total: 0,
      pageSize: 3,
      pages: 1,
      sortName: "createdDesc",
    };
  },
  methods: {
    page(cur) {
      return new Promise((resolve, reject) => {
        var _this = this;
        this.$axios({
          method: "get",
          url: "/blog/page/user/" + cur,
          params: {
            sortName: this.sortName,
            pageSize: this.pageSize,
            username: this.username,
          },
        }).then((res) => {
          let datas = res.data.data;
          _this.blogs = datas.records;
          _this.currentPage = datas.current;
          _this.total = datas.total;
          _this.pages = datas.pages;
          _this.pageSize = datas.size;
          //返回成功
          resolve();
        });
      });
    },
    setpageSize(pageSize) {
      this.pageSize = pageSize;
    },
    /* 跳转至分类标签页面 */
    toLabelBlog(label) {
      this.$router.push("/classic/" + label);
    },
    loadListByUser(username) {
      return new Promise((resolve, reject) => {
        this.username = username;
        this.page(1).then((res) => {
          resolve();
        });
      });
    },
    setPage(cur) {
      let _this = this;
      //执行加载
      this.$parent.startLoading();
      this.page(cur).then(res=>{
        //关闭加载
        _this.$parent.hiddenLoading();
      });
    }
  },
  mounted() {},
};
</script>

<style scoped>
.m-list {
  margin-top: 20px;
  text-align: left;
}
.router-link-active:hover {
  text-decoration: underline;
  color: #80d4ff;
}

.m-info {
  height: 30px;
  display: flex;
  float: right;
  margin-bottom: 10px;
}
.m-icon {
  border: 0;
  width: 22px;
  height: 22px;
  background-size: 100%;
  background-color: white;
}

.m-icon-amount {
  width: 24px;
  height: 22px;
  text-align: center;
}
.m-icon-word {
  margin-top: 5px;
  height: 22px;
  font-weight: bold;
  color: #8a8a8a;
}

.m-contentlist-box {
  display: flex;
  flex-direction: column;
  text-align: left;
  padding: 13px 35px;
  font-family: "Helvetica Neue", "Hiragino Sans GB", "WenQuanYi Micro Hei",
    "Microsoft Yahei", sans-serif;
}
.m-contentlist-title {
  padding-top: 8px;
  font-size: 18px;
  font-weight: bold;
  height: 30px;
}
.m-contentlist-title a {
  color: #404040;
}
.m-contentlist-title a:hover {
  color: #45c1ff;
}

.m-contentlist-description {
  font-size: 14px;
  color: #666666;
  height: 50px;
  overflow: hidden;
  text-overflow: ellipsis;
  /* 将对象作为弹性伸缩盒子模型显示 */
  display: -webkit-box;
  /* 限制在一个块元素显示的文本的行数 */
  /* -webkit-line-clamp 其实是一个不规范属性，使用了WebKit的CSS扩展属性，该方法适用于WebKit浏览器及移动端；*/
  -webkit-line-clamp: 3;
  /* 设置或检索伸缩盒对象的子元素的排列方式 */
  -webkit-box-orient: vertical;
  -webkit-font-smoothing: antialiased;
  line-height: 1.8;
}
.m-contentlist-label-box {
  margin: 10px 0px;
  display: flex;
}
.m-contentlist-label {
  font-size: 13px;
  font-weight: bold;
  color: #999999;
  height: 18px;
  padding: 4px;
  line-height: 18px;
  background: #ececec;
  border-radius: 2px;
  margin-right: 8px;
}
.m-contentlist-label:hover {
  color: #b3b3b3;
  cursor: pointer;
}

.m-contentlist-footer {
  display: flex;
  justify-content: space-between;
}
.m-contentlist-footer-left {
  display: flex;
  flex-direction: row;
}
.m-contentlist-date {
  margin-right: 10px;
  line-height: 30px;
  color: #666666;
  font-weight: 500;
  font-size: 13px;
}
.m-contentlist-infolist {
  line-height: 30px;
  float: right;
}
.m-home-icon-amount {
  line-height: 30px;
  margin: 0px 3px 0px 0px;
  font-size: 13px;
  color: #999999;
}
.m-contentlist-null {
  height: 540px;
  line-height: 540px;
  text-align: center;
  color: #b3b3b3;
  font-size: 22px;
  font-style: oblique;
}
.m-comment-divider {
  margin: 0 5px;
}
</style>