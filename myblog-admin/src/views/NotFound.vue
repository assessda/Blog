<template>
  <div>
    <Header></Header>
    <div class="m-view ">
      <div class="m-number">404</div>
        <div class="m-text"><span>Ooops...</span><br>抱歉，访问的页面不存在</div>
      </div>
    </div>
</template>

<script>
import Header from "../components/Header.vue";
export default {
  components: {
    Header,
  },
  methods: {
    toIndex() {
      this.$router.push("/blogs");
    },
  },
};
</script>

<style>
.m-view {
    margin-left: 40px;
    margin-top: 200px;
}
.m-number {
  border-radius: 10px;
  overflow: hidden;
  width: 600px;
  margin:100px auto 0 auto;
  background: #fff;
  position: relative;
  font: 900 30vmin 'Consolas';
  letter-spacing: 5vmin;
  text-shadow: 2px -1px 0 #000, 4px -2px 0 #0a0a0a, 6px -3px 0 #0f0f0f, 8px -4px 0 #141414, 10px -5px 0 #1a1a1a, 12px -6px 0 #1f1f1f, 14px -7px 0 #242424, 16px -8px 0 #292929;
}
.m-number::before {
  background-color: #673ab7;
  background-image: radial-gradient(closest-side at 50% 50%, #ffc107 100%, rgba(0, 0, 0, 0)), radial-gradient(closest-side at 50% 50%, #e91e63 100%, rgba(0, 0, 0, 0));
  background-repeat: repeat-x;
  background-size: 40vmin 40vmin;
  background-position: -100vmin 20vmin, 100vmin -25vmin;
  width: 100%;
  height: 100%;
  mix-blend-mode: screen;
  -webkit-animation: m-moving 10s linear infinite both;
          animation: m-moving 10s linear infinite both;
  display: block;
  position: absolute;
  content: "";
}
@-webkit-keyframes m-moving {
  to {
    background-position: 100vmin 20vmin, -100vmin -25vmin;
  }
}
@keyframes m-moving {
  to {
    background-position: 100vmin 20vmin, -100vmin -25vmin;
  }
}
.m-text {
  font: 400 5vmin "Courgette";
}
.m-text span {
  font-size: 10vmin;
}
</style>