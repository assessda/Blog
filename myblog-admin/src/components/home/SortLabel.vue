<template>
  <div>
    <div class="m-sortlabel-box">
        <div class="m-sortlabel-header">排序方式</div>
      <div
        :class="{'m-sortlabel-label': !createdDescStatu, 'm-sortlabel-label-active': createdDescStatu, 'm-sortlabel-label-box': true}"
        @click="sortByLabel('createdDesc')"
        ref="createdDesc"
      >
        时间逆序
      </div>
      <el-divider
        direction="vertical"
        class="m-sortlabel-label-divider"
      ></el-divider>
      <div
        :class="{'m-sortlabel-label': !createdAscStatu, 'm-sortlabel-label-active': createdAscStatu, 'm-sortlabel-label-box': true}"
        @click="sortByLabel('createdAsc')"
        ref="createdAsc"
      >
        时间顺序
      </div>
      <el-divider
        direction="vertical"
        class="m-sortlabel-label-divider"
      ></el-divider>
      <div
        :class="{'m-sortlabel-label': !praiseDescStatu, 'm-sortlabel-label-active': praiseDescStatu, 'm-sortlabel-label-box': true}"
        @click="sortByLabel('praiseDesc')"
        ref="praiseDesc"
      >
        点赞数量
      </div>
      <el-divider
        direction="vertical"
        class="m-sortlabel-label-divider"
      ></el-divider>
      <div
        :class="{'m-sortlabel-label': !viewsDescStatu, 'm-sortlabel-label-active': viewsDescStatu, 'm-sortlabel-label-box': true}"
        @click="sortByLabel('viewsDesc')"
        ref="viewsDesc"
      >
        阅读数量
      </div>
    </div>
    <el-divider class="m-sortlabel-divider"></el-divider>
  </div>
</template>
<script>
export default {
  data() {
    return {
      labelStyle: "",
      createdDescStatu: false,
      createdAscStatu: false,
      praiseDescStatu: false,
      viewsDescStatu: false,

    };
  },
  methods: {
    sortByLabel(sortName) {
      this.createdDescStatu = false;
      this.createdAscStatu = false;
      this.praiseDescStatu = false;
      this.viewsDescStatu = false;
      if (sortName === "createdDesc") {
        this.createdDescStatu = true;
      } else if (sortName === "createdAsc") {
        this.createdAscStatu = true;
      } else if (sortName === "praiseDesc") {
        this.praiseDescStatu = true;
      } else if (sortName === "viewsDesc") {
        this.viewsDescStatu = true;
      }
      //加载数据
      this.$parent.sortBySortName(sortName);
    },
  },
  mounted() {
    this.createdDescStatu = true;
  },
};
</script>
<style lang="css">
.m-sortlabel-box {
  display: flex;
}
.m-sortlabel-header {
height: 25px;
line-height: 25px;
font-family: "Helvetica Neue", "Hiragino Sans GB", "WenQuanYi Micro Hei",
    "Microsoft Yahei", sans-serif;
    font-size: 15px;
    margin-right: 15px;
    font-weight: 600;
}
.m-sortlabel-label-box {
  height: 25px;
  width: 70px;
  cursor: pointer;
  border-radius: 5px;
  font-family: "Helvetica Neue", "Hiragino Sans GB", "WenQuanYi Micro Hei",
    "Microsoft Yahei", sans-serif;
  font-size: 14px;
  text-align: center;
  line-height: 25px;
}
.m-sortlabel-label {
  color: #909090;
  
}
.m-sortlabel-label-active {
  background: #dff6fd;
  color: #45c1ff;
}
.m-sortlabel-divider {
  margin: 10px 0px !important;
}
.m-sortlabel-label-divider {
  margin-top: 5px !important;
}
</style>