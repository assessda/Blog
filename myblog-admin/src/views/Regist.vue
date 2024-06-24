<template>
  <div>
    <div class="m-regist-box">
      <div class="m-regist-font-box">
        <h2 class="m-regist-font">注册</h2>
      </div>

      <el-main class="m-regist-input-box">
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
          hide-required-asterisk
        >
          <el-form-item label="头像" prop="avatar">
            <div class="m-avatar-button">
              <el-upload
                class="upload-demo"
                :action="`${$axios.defaults.baseURL}/create/uploadAvatar`"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList"
                :on-success="onSuccess"
                list-type="picture"
                name="avatar"
              >
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </div>
          </el-form-item>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="ruleForm.email" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="pass">
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
              >注册</el-button
            >
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form></el-main
      >
      <div class="m-regist-login">
        <router-link to="Login">已有账号,去登陆</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    var checkUserName = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("用户名不能为空"));
      } else {
        const _this = this;
        this.$axios.get("/create/checkUserName/" + value).then((res) => {
          const result = res.data;
          if (result.code === 200) {
            return callback();
          } else {
            return callback(new Error("用户名已存在"));
          }
        });
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
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
    var validateAvatar = (rule, value, callback) => {
      if (this.ruleForm.avatar.length === 0) {
        this.$message({
          showClose: true,
          message: "请上传头像",
          type: "warning",
        });
        callback(new Error("请上传头像"));
      } else {
       callback();
      }
    };
    return {
      ruleForm: {
        username: "",
        pass: "",
        checkPass: "",
        email: "",
        avatar: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        username: [
          { validator: checkUserName, trigger: "blur" },
          { min: 3, max: 15, message: "用户名大小在3到15个字符内" },
        ],

        avatar: [{ validator: validateAvatar, trigger: "blur" }],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
      },
      fileList: [],
      avatarImg: {},
      avatarParamBody: {},
      headers: {},
      avatarUploadUrl: "",
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        console.log(111);
        if (valid) {

          console.log(222);
          //提交注册信息
          const _this = this;
          var formData = new FormData();
          formData.append(
            "user",
            JSON.stringify({
              username: this.ruleForm.username,
              password: this.ruleForm.pass,
              avatar: this.ruleForm.avatar,
              email: this.ruleForm.email,
            })
          );
          this.$axios.post("/create/registration", formData).then((res) => {
            const result = res.data;
            if (result.code === 200) {
              _this.$message({
                showClose: true,
                message: "操作成功！",
                type: "success",
              });
              _this.$router.push("/login");
            } else {
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
      console.log("=====")
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
      //获取文件名称，然后发起删除清求
    },
    handlePreview(file) {
      console.log(file + "--------------");
    },
    handleExceed(files, fileList) {
      this.$message.warning(`最多选择一张图片`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    onSuccess(response, file, fileList) {
      this.ruleForm.avatar = response.data;
      console.log(this.ruleForm.avatar);
    },
  },
  mounted() {
    /* //初始化上传头像表单的地址
    this.avatarUploadUrl = this.
    /create/uploadAvatar */
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
  max-width: 500px;
  margin: 0 auto;
}

.m-regist-box {
  width: 600px;
  margin: 10% auto 0 auto;
  background: white;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
}
.m-regist-font-box {
  margin-top: 15px;
  height: 40px;
  text-align: center;
}
.m-regist-font {
  margin: 0px auto;
}

.m-avatar-content {
  align-content: center;
  display: inline;
  margin: 10px auto 20px auto;
  width: 500px;
}
.m-avatar-word {
  display: inline;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  font-size: 15px;
  text-align: center;
  width: 88px;
  display: inline-block;
}
.m-avatar-button {
  display: inline-block;
  width: 200px;
  padding-right: 210px;

}
.m-regist-input-box {
  margin-right: 30px;
  padding-bottom: 0px !important;
}
.m-regist-login {
  width: 500px;
  text-align: left;
  margin-left: 60px;
  margin-bottom: 10px;
}
</style>