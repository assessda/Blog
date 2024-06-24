<template>
  <div class="m-content">
    <Header></Header>
    <div class="m-blog-border">
      <blog-title ref="blogTitle"></blog-title>
      <el-divider></el-divider>
      <div class="m-blogContent">
        <div class="markdown-body" v-html="content"></div>
      </div>
    </div>
    <!-- 点赞按钮 -->
    <praise-button ref="praiseButton"></praise-button>
    <!-- 评论按钮 -->
    <comment-button ref="commentButton"></comment-button>
    <!-- 评论 -->
    <comment ref="comment"></comment>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import Comment from "../components/comment/comment.vue";
import PraiseButton from "../components/floatButton/PraiseButton.vue";
import CommentButton from "../components/floatButton/CommentButton.vue"
import BlogTitle from "../components/title/BlogTitle.vue"

import "github-markdown-css/github-markdown.css"; // 然后添加样式markdown-body
import "highlight.js/styles/atom-one-light.css";
export default {
  components: {
    Header,
    Comment,
    PraiseButton,
    CommentButton,
    BlogTitle
  },
  data() {
    return {
      id: "",
      userId: "",
      title: "",
      content: "",
      created: "",
      currentUser: false,
      praises: 0,
      comments: 0,
      views: 0,
      username: '',
      avatar: ''
    };
  },
  methods: {
    loadComment() {
      //将当前分页信息传递给评论组件
      this.$refs.comment.start(this.id, this.userId);
    },
    //加载点赞按钮
    loadPraiseButton(blogId) {
      this.$refs.praiseButton.loadButton(blogId);
    },
    loadTitle() {
      this.$refs.blogTitle.loadTitle({
        id: this.id,
        userId: this.userId,
        title: this.title,
        praises: this.praises,
        comments: this.comments,
        views: this.views,
        username: this.username,
        avatar: this.avatar,
        created: this.created,
        })
    },
    updatePraiseInfo(praiseStatus) {
      this.$refs.blogTitle.updatePraiseInfo(praiseStatus);
    },
  },
  created() {
    const blogId = this.$route.params.blogId;
    const _this = this;
    if (blogId) {
      this.$axios.get("/blog/detail/" + blogId).then((res) => {
        //获取博客数据
        const blog = res.data.data;
        console.log(blog)
        _this.title = blog.title;
        _this.content = blog.content;
        _this.id = blog.id;
        _this.userId = blog.userId;
        _this.praises = blog.praises;
        _this.views = blog.views;
        _this.avatar = blog.avatar;
        _this.username = blog.username;
        _this.created = blog.created;
        
        //转成MarkDown格式
        var MarkDownIt = require("markdown-it");
        var hljs = require("highlight.js");
        var md = new MarkDownIt({
          html: true,
          linkify: true,
          typographer: true,
          highlight: function (str, lang) {
            // 得到经过highlight.js之后的html代码
            var code = "";
            //如果有支持的语言就使用该语言的语法，没有就用js的
            if (hljs.getLanguage(lang)) {
              code = hljs.highlight(lang, str, true).value;
            } else {
              code = hljs.highlight("js", str, true).value;
            }
            return code;
          },
         
        });
        var mdTxt = md.render(_this.content);
        _this.content = mdTxt;
        /*加载标题头部 */
        this.loadTitle();
        /*加载评论区*/
        this.loadComment();
        /* 按钮在文章加载完成后再加载 */
         /*加载按钮 */
        this.loadPraiseButton(_this.id);
      });
    }
  },
};
</script>

<style>
.m-blog-border {
  margin: 20px auto;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  padding: 1px 30px 30px 30px;
  margin-top: 80px;
}
.m-blog-border {
  text-align: left;
  background-color: white;
  border-radius: 8px;
}

</style>