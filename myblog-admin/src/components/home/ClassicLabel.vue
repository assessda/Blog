<template>
  <div>
    <div class="m-classic-content-box">
      <div class="m-classic-title">标签</div>
      <el-divider class="m-classic-divider"></el-divider>
      <div class="m-classic-label-box">
        <div class="m-classic-label-divider-box" v-for="(label, i) in labels">
          <div class="m-classic-label">
            <router-link
              :to="{ name: 'LabelBlog', params: { labelName: label.name } }"
              class="m-classic-label-word"
            >
              {{ label.name }}
            </router-link>
          </div>
          <el-divider
            class="m-classic-label-divider"
            v-if="i !== labels.length - 1 && i !== labels.length - 2"
          ></el-divider>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      labels: [],
    };
  },
  methods: {},
  mounted() {
    let _this = this;
    this.$axios({
      method: "get",
      url: "/classic/labels",
    }).then((res) => {
      _this.labels = res.data.data;
    });
  },
};
</script>

<style scoped>
.m-classic-content-box {
  width: 300px;
  background-color: white;
  margin-left: 30px;
  border-radius: 2px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  padding: 10px 5px;
  /*设置固定*/
  position: fixed;
}
.m-classic-label-box {
  display: flex;
  flex-wrap: wrap;
}
.m-classic-title {
  font-size: 20px;
  font-weight: bold;
  height: 30px;
  line-height: 20px;
  text-align: left;
  margin-left: 30px;
}
.m-classic-label {
  width: 150px;
  height: 30px;
  line-height: 30px;
  color: #148558;
}
.m-classic-label-word {
  color: #148558;
}
.m-classic-label-word:hover {
  color: #89edc5;
  cursor: pointer;
  text-decoration: none;
}
.m-classic-label-divider {
  width: 130px;
  margin: 5px 10px;
}
.m-classic-divider {
  width: 280px;
  margin: 5px 10px;
}
</style>