<template>
  <div>
    <Header></Header>
    <div class="m-labelblog-content">
        <div class="m-labelblog-title">标签: {{currentLabel}}</div>
        <content-list ref="contentList"></content-list>
    </div>
  </div>
</template>

<script>
import Header from '../components/Header.vue'
import ContentList from '../components/home/ContentList.vue'
export default {
    components: {
        Header,
        ContentList
    },
    data() {
      return {
        currentLabel: '',
      }
    },
    methods: {
      queryLabelBlog() {
        this.$refs.contentList.setLabelQuery(this.$route.params.labelName);
      }
    },
    mounted() {
      this.queryLabelBlog();
      this.currentLabel = this.$route.params.labelName;
    }
}
</script>

<style lang="css" scoped>
.m-labelblog-content {
  display: flex;
  flex-direction: column;
    margin: 0 auto;
    margin-top: 80px;
    max-width: 1000px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 2px;
  background-color: white;
}
.m-labelblog-title {
  font-size: 20px;
  text-align: left;
  margin: 10px 0px 0px 35px;
  font-weight: bold;
  font-style: oblique;
}
</style>>
