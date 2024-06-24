<template>
  <div class="m-background-body" ref="backgroundBody"></div>
</template>

<script>
export default {
  data() {
    return {
      urls: [
        "https://6666-1300801489.cos.ap-nanjing.myqcloud.com/%E8%83%8C%E6%99%AF%E5%9B%BE%E7%89%87%2Fcool-background2.png",
        "https://6666-1300801489.cos.ap-nanjing.myqcloud.com/%E8%83%8C%E6%99%AF%E5%9B%BE%E7%89%87%2Fcool-background.png",
        "https://6666-1300801489.cos.ap-nanjing.myqcloud.com/%E8%83%8C%E6%99%AF%E5%9B%BE%E7%89%87%2Fcool-background1.png",
        "https://6666-1300801489.cos.ap-nanjing.myqcloud.com/%E8%83%8C%E6%99%AF%E5%9B%BE%E7%89%87%2Fcool-background3.png",
        "https://6666-1300801489.cos.ap-nanjing.myqcloud.com/%E8%83%8C%E6%99%AF%E5%9B%BE%E7%89%87%2Fcool-background4.png",
        "https://6666-1300801489.cos.ap-nanjing.myqcloud.com/%E8%83%8C%E6%99%AF%E5%9B%BE%E7%89%87%2Fcool-background5.png",
      ],
    };
  },
  methods: {
    changeBackground(index) {
      if (index > 0 && index <= this.urls.length) {
        this.$refs.backgroundBody.setAttribute(
          "style",
          "background-image: url(" + this.urls[index - 1] + ")"
        );
      }
    },
  },
  mounted() {
    //获取存储的背景id
    let backgroundId = this.$store.getters.getBackgroundId;
    if (backgroundId === null || backgroundId === '') {
      this.changeBackground(1);
    } else {
      this.changeBackground(backgroundId);
    }                  
  }
};
</script>

<style scoped>
.m-background-body {
  position: fixed;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  top: 0;
  z-index: -1;
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed; /*关键*/
}
</style>