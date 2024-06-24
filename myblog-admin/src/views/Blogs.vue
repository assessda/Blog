<template>
  <div>
    <Header></Header>
    <div class="m-blog-list-box">
      <div class="m-blog-content">
        <content-list ref="contentList"></content-list>
      </div>
      <div class="m-label-box">
        <classic-label></classic-label>
        <recommend-label></recommend-label>
        <div class="m-blog-fillup"></div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import ContentList from "../components/home/ContentList.vue";
import ClassicLabel from "../components/home/ClassicLabel.vue";
import RecommendLabel from "../components/home/RecommendLabel.vue";
export default {
  components: { Header, ContentList, ClassicLabel, RecommendLabel },
  mounted() {
    this.$refs.contentList.sortBySortName();
  },
};
</script>

<style lang="css" scoped>
.m-blog-list-box {
  display: flex;
  text-align: center;
  margin-top: 80px;
  justify-content: center;
}
.m-blog-content {
  max-width: 800px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 2px;
  background-color: white;
  display: flex;
  justify-content: left;
}
.m-blog-fillup {
  width: 300px;
  height: 1px;
  margin-left: 30px;
}
/*根据屏幕宽度来调整*/

@media (max-width: 1150px) {
  .m-label-box {
    display: none;
  }
}
</style>