<template>
  <div>
    <Header> </Header>
    <loading ref="loading"></loading>
    <div class="m-classic-border">
      <div class="m-classic-title">分类</div>
      <div class="m-classic-box">
        <div
          v-for="label in labels"
          class="m-classic-label-box"
          @click="toLabelBlog(label.name)"
        >
          <div>{{ label.name }}</div>
          <div>({{ label.records }})</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Header from "../components/Header.vue";
import Loading from "../components/loading/Loading.vue";
export default {
  components: {
    Header,
    Loading,
  },
  data() {
    return {
      labels: [],
    };
  },
  methods: {
    toLabelBlog(labelName) {
      this.$router.push("/classic/" + labelName);
    },
    startLoading() {
      this.$refs.loading.showLoading();
    },
    hiddenLoading() {
      this.$refs.loading.hiddenLoading();
    },
  },
  mounted() {
    //开始加载
    this.startLoading();
    let _this = this;
    this.$axios({
      method: "get",
      url: "/classic/labelsAndRecords",
    }).then((res) => {
      _this.labels = res.data.data;
      //结束加载
      this.hiddenLoading();
    });
  },
};
</script>
<style lang="css" scoped>
.m-classic-border {
  margin: 0 auto;
  width: 1000px;
  background-color: white;
  margin-top: 80px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  display: flex;
  flex-direction: column;
  min-height: 168px;
}
.m-classic-title {
  width: 110px;
  margin: 20px 0px 0px 10px;
  font-size: 20px;
  font-weight: bold;
}
.m-classic-box {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.m-classic-label-box {
  width: 100px;
  height: 80px;
  background-color: rgb(233, 245, 245);
  border: rgb(216, 231, 231) 1px solid;
  margin: 30px;
  border-radius: 4px;
  line-height: 40px;
  color: #3d3c3c;
  font-weight: bold;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  transition: all 0.2s ease 0s;
  -o-transition: all 0.2s ease 0s;
  -moz-transition: all 0.2s ease 0s;
  -webkit-transition: all 0.2s ease 0s;
}
.m-classic-label-box:hover {
  /**background-color: rgb(131, 143, 143);**/
  background-color: white;
  border: rgb(131, 218, 135) 1px solid;
}
</style>