<template>
  <!--添加Logo  -->
  <!-- <img class="mlogo" src="../../Resoures/img/logo.png"/> -->
  <div class="m-main-box">
    <div class="m-login-logo-box">
      <img class="m-login-logo" src="https://6666-1300801489.cos.ap-nanjing.myqcloud.com/2022-05-23/favicon.ico" />
    </div>
    <div class="m-login-font-box">
      <h2 class="m-login-font">登录</h2>
    </div>
    <el-main>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="username" class="m-login-input">
          <el-input v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password" class="m-login-input">
          <el-input
            type="password"
            v-model="ruleForm.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <div class="m-button-box">
          <el-button type="primary" @click="submitForm('ruleForm')" class="m-login-submit-button"
            >提交</el-button
          >
        </div>
      </el-form></el-main
    >
    <div class="m-login-regist">
      <router-link to="Regist">还没有账号？去注册</router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    //用户名不能为空
    var checkUserName = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("用户名不能为空"));
      } else {
        callback();
      }
    };
    //密码不能为空
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        password: [
          { validator: validatePass, trigger: "blur" },
          /*{ required: false, message:"密码不能为空",trigger: "blur" }*/
        ],
        username: [
          { validator: checkUserName, trigger: "blur" }, //自定义
          { min: 3, max: 15, message: "用户名大小在3到15个字符内" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this;
          //向后端发送请求,判断密码是否错误
          this.$axios.post("/login", this.ruleForm).then((res) => {
            const token = res.headers["authorization"];
            const userInfo = res.data.data;

            _this.$store.commit("SET_TOKEN", token);
            _this.$store.commit("SET_USERINFO", userInfo);
            console.log(_this.$store.getters.getUser);

            _this.$router.push("/blogs");
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  },
};
</script>
<style>
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}
.el-main {
  text-align: center;
  line-height: 160px;
}
body > .el-container {
  margin-bottom: 40px;
}
.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}
.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.demo-ruleForm {
  max-width: 550px;
  margin: 0 auto;
  background: white;
  border-radius: 5px;
}
.m-main-box {
  background: white;
  margin-top: 10% !important;
  display: flex;
  justify-content: center;
  margin: 0 auto;
  width: 600px;
  height: 355px;
  border-radius: 8px;
  flex-direction: column;
}
.m-login-logo-box {
  height: 80px;
}

.m-login-logo {
  margin: 5px 0px;
  height: 70px;
}
.m-login-font-box {
  height: 40px;
  text-align: center;
}
.m-login-font {
  margin: 0px auto;
}
.m-login-input {
  margin-right: 50px;
}
.m-button-box {
  display: flex;
  justify-content: center;
  align-items: center;
}
.m-login-submit-button {
width: 200px;
}
.m-login-regist {
  width: 500px;
  text-align: left;
  margin-left: 60px;
  margin-bottom: 10px;
}
</style>