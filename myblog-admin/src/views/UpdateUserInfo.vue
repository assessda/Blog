<template>
  <div>
    <Header></Header>
    <loading ref="loading"></loading>
    <div class="m-update-user-box">
      <div class="m-update-user-title">信息修改</div>
      <el-form
        :model="userInfoForm"
        ref="userInfoForm"
        label-width="100px"
        class="m-demo-userForm"
        :rules="rules"
      >
        <el-form-item label="头像" prop="avatar">
          <!-- 头像显示 -->
          <el-avatar :size="50" :src="userInfoForm.avatar"></el-avatar>
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
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input
            type="username"
            v-model="userInfoForm.username"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="profile">
          <el-input
            type="textarea"
            v-model="userInfoForm.profile"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <!-- 标签 -->
        <label-input ref="labelInput"></label-input>
        <el-form-item>
          <el-button type="primary" @click="submitForm('userInfoForm')"
            >提交</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import LabelInput from "../components/edit/LabelAdd.vue";
import Loading from "../components/loading/Loading.vue";
export default {
  components: {
    Header,
    LabelInput,
    Loading,
  },
  data() {
    var validateAvatar = (rule, value, callback) => {
      let _this = this;
      if (this.userInfoForm.avatar.length === 0) {
        _this.$message({
          showClose: true,
          message: "请上传头像",
          type: "warning",
        });
        return callback(new Error("请上传头像"));
      } else {
        return callback();
      }
    };
    var validateUsername = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("用户名不能为空"));
      } else if (this.$store.getters.getUser.username !== value) {
        const _this = this;
        this.$axios.get("/create/checkUserName/" + value).then((res) => {
          const result = res.data;
          if (result.code === 200) {
            return callback();
          } else {
            return callback(new Error("用户名已存在"));
          }
        });
      } else {
        return callback();
      }
    };
    return {
      userInfoForm: {
        username: "",
        profile: "",
        skills: [],
        avatar: "",
      },
      fileList: [],
      avatarImg: {},
      avatarParamBody: {},
      headers: {},
      avatarUploadUrl: "",
      rules: {
        avatar: [{ validator: validateAvatar, trigger: "blur" }],
        username: [
          { validator: validateUsername, trigger: "blur" },
          { min: 3, max: 15, message: "用户名大小在3到15个字符内" },
        ],
        profile: [{ min: 0, max: 100, message: "简介不能超过100个字符" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      //将标签信息加入到表单
      this.userInfoForm.skills = this.getLabelInfo();
      const _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //发起请求
          _this
            .$axios({
              url: "/user/modify",
              method: "put",
              data: this.userInfoForm,
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              //更新本地用户信息
              _this.$store.commit("SET_USERINFO", _this.userInfoForm);
              //跳转页面
            _this.$router.push("/user/" + _this.userInfoForm.username);
            });
            this.startLoading();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //获取标签信息
    getLabelInfo() {
      return this.$refs.labelInput.getLabelInfo();
    },
    loadLabelInfo(labels) {
      this.$refs.labelInput.loadLabelInfo(labels);
    },
    setLabelsMaxSize(number) {
      this.$refs.labelInput.setLabelsMaxSize(number);
    },
    handleRemove(file, fileList) {
      //如果没有response
      this.userInfoForm.avatar = "";
      console.log(file, fileList);
      //获取文件名称，然后发起删除清求
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`最多选择一张图片`);
    },
    beforeRemove(file, fileList) {
      console.log(fileList);
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    onSuccess(response, file, fileList) {
      console.log(response.data);
      this.userInfoForm.avatar = response.data;
    },
    startLoading() {
      this.$refs.loading.showLoading();
    },
    hiddenLoading() {
      this.$refs.loading.hiddenLoading();
    },
    loadUserInfo() {
      //设置标签个数限制
      this.setLabelsMaxSize(10);
      //开始加载
      this.startLoading();
      var _this = this;
      this.$axios({
        url: "/user/userDetailed/" + this.$store.getters.getUser.username,
        method: "get",
      }).then((res) => {
        _this.userInfoForm = res.data.data;
        console.log(_this.userInfoForm);
        //文件列表添加初始头像
        _this.fileList.push({
          url: _this.userInfoForm.avatar,
          name: "avatar.png",
        });
        //将数据传输到标签组件
        _this.loadLabelInfo(_this.userInfoForm.skills);
        //关闭加载
        _this.hiddenLoading();
      });
    },
  },
  mounted() {
    //加载
    this.loadUserInfo();
  },
};
</script>

<style scoped>
.m-update-user-box {
  background-color: white;
  width: 500px;
  min-height: 400px;
  margin: 80px auto;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  box-shadow: 0 0 6px rgb(0 0 0 / 20%);
}
.m-demo-userForm {
  width: 85%;
  margin: 20px 70px 20px 0px;
  display: flex;
  flex-direction: column;
}
.m-update-user-title {
  font-size: 20px;
  font-weight: bold;
  margin: 10px;
}
</style>