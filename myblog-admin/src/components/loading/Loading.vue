<template>
  <div class="m-loading-box" v-show="isShow">
    <div class="m-spin-dot">
      <span class="u-dot-item m-loading-span-1"></span>
      <span class="u-dot-item m-loading-span-2"></span>
      <span class="u-dot-item m-loading-span-3"></span>
      <span class="u-dot-item m-loading-span-4"></span>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isShow: true,
    };
  },
  methods: {
    showLoading() {
      this.isShow = true;
    },
    hiddenLoading() {
      this.isShow = false;
    },
  },
};
</script>
<style lang="css">
.m-loading-box {
  position: fixed;
  width: 100%;
  height: 100%;
  background: #fafafa;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 100;
  pointer-events: auto; /**屏蔽鼠标事件**/
  opacity: 1;
}
.m-spin-dot {
  /** 水平垂直居中 **/
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: 20% auto;
  width: 40px;
  height: 40px;
  animation: rotate 1s linear infinite;
  -webkit-animation: rotate 1s linear infinite;
  z-index: 1000;
}
@keyframes rotate {
  to {
    transform: rotate(450deg);
  }
}
.u-dot-item {
  /**  单个圆点样式 **/
  position: absolute;
  width: 12px;
  height: 12px;
  background: #31c3e6;
  border-radius: 50%;
  opacity: 0.3;
  animation: spinMove 1s linear infinite alternate;
  -webkit-animation: spinMove 1s linear infinite alternate;
  opacity: 1;
}
.u-dot-item:first-child {
  top: 0;
  left: 0;
}
.u-dot-item:nth-child(2) {
  top: 0;
  right: 0;
  animation-delay: 0.4s;
  -webkit-animation-delay: 0.4s;
}
.u-dot-item:nth-child(3) {
  bottom: 0;
  right: 0;
  animation-delay: 0.8s;
  -webkit-animation-delay: 0.8s;
}
.u-dot-item:last-child {
  bottom: 0;
  left: 0;
  animation-delay: 1.2s;
  -webkit-animation-delay: 1.2s;
}
.m-loading-span-1 {
  background: #3aa1ff;
}
.m-loading-span-2 {
  background: #5db1ff;
}
.m-loading-span-3 {
  background: #a5d4ff;
}
.m-loading-span-4 {
  background: #84c4ff;
}
</style> 