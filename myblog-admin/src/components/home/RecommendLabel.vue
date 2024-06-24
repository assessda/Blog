<template>
  <div>
    <div class="m-recommend-box">
      <div class="m-recommend-title">推荐文章</div>
      <el-divider class="m-recommend-title-divider"></el-divider>
      <div class="m-recommend-label" v-for="(blog, i) in blogs">
      <div class="m-recommend-label-word-box">
        <router-link :to="{ name: 'BlogDetail', params: { blogId: blog.id } }" class="m-recommend-label-word">{{blog.title}}</router-link>
        </div><el-divider class="m-recommend-label-divider" v-if="blogs.length - 1 !== i"></el-divider>
      </div>
      
    </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            blogs: [],
        }
    },
    mounted() {
        let _this = this;
        this.$axios({
            url: '/blog/recommend',
            method: 'get',
        }).then(res=> {
            _this.blogs = res.data.data;
        })
    }
};
</script>

<style scoped>
.m-recommend-box {
  width: 300px;
  background-color: white;
  margin-left: 30px;
  margin-top: 400px;
  border-radius: 2px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  padding: 10px 5px;
  /*设置固定*/
  position: fixed;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  min-height: 100px;
}
.m-recommend-title {
  font-size: 20px;
  font-weight: bold;
  height: 30px;
  line-height: 20px;
  text-align: left;
  margin-left: 30px;
}
.m-recommend-title-divider {
  width: 280px;
  margin: 5px 10px;
}
.m-recommend-label {
width: 300px;
  height: 45px;
  line-height: 30px;
  color: #148558;
  
}
.m-recommend-label-word-box {
    width: 300px;
    height: 30px;
    overflow: hidden;
}
.m-recommend-label-word {
    
  color: #148558;
}
.m-recommend-label-word:hover {
  color: #89edc5;
  cursor: pointer;
  text-decoration: none;
}
.m-recommend-label-divider {
    width: 280px;
    margin: 10px 10px;
}
</style>