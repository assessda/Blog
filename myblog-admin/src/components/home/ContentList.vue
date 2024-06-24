<template>
  <div>
    <loading ref="loading"></loading>
    <div class="m-contentlist-box">
      <!-- 排序标签 -->
      <sort-label></sort-label>
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
            <div class="m-contentlist-avatar">
              <el-avatar
                :size="30"
                :src="blog.avatar"
                @click="toUserDetailed(blog.username)"
              ></el-avatar>
            </div>
            <div class="m-contentlist-username">
              <router-link :to="'/user/' + blog.username">{{
                blog.username
              }}</router-link>
            </div>
            <div class="m-contentlist-date">{{ blog.created }}</div>
          </div>
          <div class="m-contentlist-infolist">
            <div class="m-info">
              <el-tooltip
                class="item"
                effect="light"
                content="点赞数"
                placement="bottom"
              >
                <svg
                  t="1650799490253"
                  class="m-home-icon"
                  viewBox="0 0 1024 1024"
                  version="1.1"
                  xmlns="http://www.w3.org/2000/svg"
                  p-id="2059"
                >
                  <path
                    d="M890.908 429.534c-20.66-26.05-51.585-40.99-84.84-40.99l-189.34 0c10.34-28.83 17.18-60.165 19.615-90.48 3.785-47.147-3.465-89.02-20.955-121.095-21.53-39.46-58.18-64.072-105.997-71.177-21.662-3.22-40.292 0.945-55.377 12.372-30.495 23.102-36.092 67.065-42.575 117.97-5.182 40.692-11.055 86.817-30.79 119.877-13.172 22.065-38.807 51.397-109.092 51.397l-73.345 0c-48.842 0-88.58 39.722-88.58 88.545l0 334.754c0 48.82 39.737 88.545 88.58 88.545l534.336 0c24.33 0 48.22-8.325 67.275-23.43 19.06-15.11 32.605-36.475 38.145-60.155l73.52-314.264C919.058 489.054 911.553 455.569 890.908 429.534zM168.672 830.708 168.672 495.956c0-16.27 13.252-29.505 29.537-29.505l62.232 0 0 393.762-62.232 0C181.925 860.213 168.672 846.978 168.672 830.708zM853.998 507.956l-73.52 314.272c-5.23 22.36-24.94 37.985-47.93 37.985L319.484 860.213 319.484 462.169c49.877-9.52 87.4-34.92 111.86-75.895 26.05-43.64 32.75-96.257 38.662-142.68 2.67-20.977 5.193-40.79 9.2-55.99 4.297-16.302 8.745-21.08 10.457-22.377 1.96-1.485 5.677-1.835 11.05-1.035 29.615 4.4 50.175 17.83 62.84 41.052 25.99 47.642 14.505 128.052-10.265 182.86-5.93 13.127-4.83 28.167 2.96 40.232 7.78 12.052 21.01 19.247 35.395 19.247l214.424 0c15.125 0 29.185 6.792 38.58 18.637C854.028 478.049 857.438 493.261 853.998 507.956z"
                    p-id="2060"
                    fill="#8a8a8a"
                  ></path>
                </svg>
              </el-tooltip>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">{{ blog.praises }}</div>
              </div>
              <el-tooltip
                class="item"
                effect="light"
                content="浏览量"
                placement="bottom"
                ><svg
                  t="1650800027342"
                  class="m-home-icon"
                  viewBox="0 0 1024 1024"
                  version="1.1"
                  p-id="3437"
                  width="22"
                  height="22"
                >
                  <path
                    d="M515.2 224c-307.2 0-492.8 313.6-492.8 313.6s214.4 304 492.8 304 492.8-304 492.8-304S822.4 224 515.2 224zM832 652.8c-102.4 86.4-211.2 140.8-320 140.8s-217.6-51.2-320-140.8c-35.2-32-70.4-64-99.2-99.2-6.4-6.4-9.6-12.8-16-19.2 3.2-6.4 9.6-12.8 12.8-19.2 25.6-35.2 57.6-70.4 92.8-102.4 99.2-89.6 208-144 329.6-144s230.4 54.4 329.6 144c35.2 32 64 67.2 92.8 102.4 3.2 6.4 9.6 12.8 12.8 19.2-3.2 6.4-9.6 12.8-16 19.2C902.4 585.6 870.4 620.8 832 652.8z"
                    p-id="3438"
                    fill="#8a8a8a"
                  ></path>
                  <path
                    d="M512 345.6c-96 0-169.6 76.8-169.6 169.6 0 96 76.8 169.6 169.6 169.6 96 0 169.6-76.8 169.6-169.6C681.6 422.4 604.8 345.6 512 345.6zM512 640c-67.2 0-121.6-54.4-121.6-121.6 0-67.2 54.4-121.6 121.6-121.6 67.2 0 121.6 54.4 121.6 121.6C633.6 582.4 579.2 640 512 640z"
                    p-id="3439"
                    fill="#8a8a8a"
                  ></path>
                </svg>
              </el-tooltip>
              <div class="m-home-icon-amount">
                <div class="m-home-icon-word">{{ blog.views }}</div>
              </div>
              <el-tooltip
                class="item"
                effect="light"
                content="评论数"
                placement="bottom"
              >
                <svg
                  t="1650800244593"
                  class="m-home-icon"
                  viewBox="0 0 1024 1024"
                  version="1.1"
                  xmlns="http://www.w3.org/2000/svg"
                  p-id="2360"
                  width="22"
                  height="22"
                >
                  <path
                    d="M511.999488 847.882863c-28.734592 0-56.729303-2.604314-83.969807-7.099698L231.232673 960.185602 231.232673 761.40735C128.618486 689.355337 62.772174 578.889433 62.772174 454.825836c0-217.07906 201.129864-393.058051 449.228338-393.058051 248.084146 0 449.228338 175.980014 449.228338 393.058051C961.227826 671.917176 760.083635 847.882863 511.999488 847.882863zM511.999488 117.91762c-217.086932 0-393.074156 150.851707-393.074156 336.907193 0 114.166179 66.421434 214.898395 167.761552 275.820929l-1.768346 130.234133 132.171551-79.455633c30.4487 6.497994 62.117231 10.308787 94.910422 10.308787 217.101258 0 393.073132-150.825101 393.073132-336.907193C905.073644 268.769326 729.10177 117.91762 511.999488 117.91762zM736.614169 510.976694c-31.011542 0-56.154182-25.128307-56.154182-56.150858 0-31.010271 25.14264-56.151881 56.154182-56.151881s56.154182 25.14161 56.154182 56.151881C792.768351 485.848387 767.624687 510.976694 736.614169 510.976694zM511.999488 510.976694c-31.010518 0-56.153158-25.128307-56.153158-56.150858 0-31.010271 25.14264-56.151881 56.153158-56.151881 31.011542 0 56.154182 25.14161 56.154182 56.151881C568.15367 485.848387 543.01103 510.976694 511.999488 510.976694zM287.385831 510.976694c-31.010518 0-56.153158-25.128307-56.153158-56.150858 0-31.010271 25.14264-56.151881 56.153158-56.151881s56.153158 25.14161 56.153158 56.151881C343.53899 485.848387 318.39635 510.976694 287.385831 510.976694z"
                    p-id="2361"
                    fill="#8a8a8a"
                  ></path>
                </svg>
              </el-tooltip>
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
      @current-change="page"
      v-show="pages > 1"
    >
    </el-pagination>
  </div>
</template>

<script>
import Loading from "../../components/loading/Loading.vue";
import SortLabel from "../../components/home/SortLabel.vue";
export default {
  components: {
    Loading,
    SortLabel,
  },
  data() {
    return {
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
      pageSize: 10,
      pages: 1,
      sortName: "createdDesc",
      queryLabel: "",
      searchContent: "",
    };
  },
  methods: {
    page(cur) {
      this.startLoading();
      var _this = this;
      console.log(this.queryLabel);
      this.$axios({
        method: "get",
        url: "/blog/page/" + cur,
        params: {
          sortName: this.sortName,
          pageSize: this.pageSize,
          label: this.queryLabel,
        },
      }).then((res) => {
        let datas = res.data.data;
        _this.blogs = datas.records;
        _this.currentPage = datas.current;
        _this.total = datas.total;
        _this.pages = datas.pages;
        _this.pageSize = datas.size;
        //loading close
        _this.hiddenLoading();
      });
    },
    pageOfSearch(cur) {
      this.startLoading();
      var _this = this;
      console.log(this.searchContent);
      this.$axios({
        method: "get",
        url: "/blog/search/" + this.searchContent + "/" + cur,
        params: {
          sortName: this.sortName,
          pageSize: this.pageSize,
        },
      }).then((res) => {
        let datas = res.data.data;
        console.log(datas)
        _this.blogs = datas.records;
        _this.currentPage = datas.current;
        _this.total = datas.total;
        _this.pages = datas.pages;
        _this.pageSize = datas.size;
        //loading close
        _this.hiddenLoading();
      });
    },
    startLoading() {
      this.$refs.loading.showLoading();
    },
    hiddenLoading() {
      this.$refs.loading.hiddenLoading();
    },
    sortBySortName(sortName) {
      if (arguments.length !== 0) {
        this.sortName = sortName;
      } else {
        this.sortName = "createdDesc";
      }
      //判断是那个路由
      if (this.$route.name === "BlogSearch") {
        this.pageOfSearch(1);
      } else {
        this.page(1);
      }
    },
    setSearchQuery(searchContent) {
      this.searchContent = searchContent;
      this.pageOfSearch(1);
    },
    setLabelQuery(label) {
      this.queryLabel = label;
      this.page(1);
    },
    setpageSize(pageSize) {
      this.pageSize = pageSize;
    },
    /* 跳转至分类标签页面 */
    toLabelBlog(label) {
      this.$router.push("/classic/" + label);
    },
    //跳转至用户主页
    toUserDetailed(username) {
      this.$router.push("/user/" + username);
    },
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
  height: 70px;
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
.m-contentlist-username {
  margin: 0px 10px;
  line-height: 30px;
  font-size: 13px;
}
.m-contentlist-date {
  margin: 0px 10px;
  line-height: 30px;
  color: #666666;
  font-weight: 500;
  font-size: 13px;
}
.m-contentlist-infolist {
  line-height: 30px;
  float: right;
}
.m-home-icon {
  width: 22px;
  height: 22px;
  margin-top: 3px;
}
.m-home-icon-amount {
  line-height: 30px;
  margin: 0px 10px 0px 6px;
  font-size: 13px;
  color: #999999;
}
.m-contentlist-null {
  height: 150px;
  line-height: 150px;
  text-align: center;
  color: #b3b3b3;
  font-size: 22px;
  font-style: oblique;
}
</style>