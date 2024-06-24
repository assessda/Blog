<template>
  <div>
    <Header></Header>
    <div class="m-update-password-box">
      <div class="m-update-password-title">密码修改</div>
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="m-update-password-ruleForm"
      >
        <el-form-item label="旧密码" prop="oldPass">
          <el-input
            type="password"
            v-model="ruleForm.oldPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >提交</el-button
          >
          <el-button @click="cancalBack()">返回</el-button>
        </el-form-item>
      </el-form>
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
    var oldValidatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入旧密码"));
      } else {
        if (this.ruleForm.oldPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入新密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      user: {},
      ruleForm: {
        oldPass: "",
        pass: "",
        checkPass: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        oldPass: [{ validator: oldValidatePass, trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios({
            url: "/user/modify/password",
            method: "put",
            data: {
              oldPassword: this.ruleForm.oldPass,
              newPassword: this.ruleForm.pass,
              userId: this.user.id,
            },
            headers: {
              Authorization: localStorage.getItem("token"),
            },
          }).then((res) => {
            if (res.data.code === 200) {
              this.$message({
                message: "修改成功",
                type: "success",
                duration: 2 * 1000, //停留两秒
                showClose: true,
              });
              //跳转
              this.$router.push('/blogs');
            } else if (res.data.code === 400) {
              this.$message({
                message: "密码错误",
                type: "error",
                duration: 2 * 1000, //停留两秒
                showClose: true,
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    cancalBack() {
      this.$router.push("/user/" + this.user.username);
    },
  },
  mounted() {
    this.user = this.$store.getters.getUser;
  },
};
</script>

<style>
.m-update-password-box {
  background-color: white;
  width: 500px;
  min-height: 400px;
  margin: 80px auto;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  box-shadow: 0 0 6px rgb(0 0 0 / 20%);
  padding: 10px;
}
.m-update-password-title {
  font-size: 20px;
  font-weight: bold;
  margin: 10px;
}
.m-update-password-ruleForm {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 85%;
}
</style>