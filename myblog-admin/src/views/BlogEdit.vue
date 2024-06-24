<template>
  <div>
    <Header></Header>
    <div class="m-edit-box">
      <div class="m-edit-content">
        <el-form
          :model="editForm"
          status-icon
          :rules="rules"
          ref="editForm"
          label-width="100px"
          class="demo-editForm"
          :hide-required-asterisk="true"
        >
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="editForm.title"
              autocomplete="off"
              placeholder="请输入标题"
              class="m-edit-description-title"
            ></el-input>
          </el-form-item>
          <input type="text" hidden />
          <el-form-item label="摘要" prop="description">
            <el-input
              class="m-edit-description-input"
              type="textarea"
              :rows="3"
              v-model="editForm.description"
              placeholder="请输入摘要"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <!-- 标签 -->
          <label-input ref="labelInput"></label-input>
          <el-form-item label="内容" prop="content">
            <mavon-editor
              v-model="editForm.content"
              ref="md"
              @imgAdd="$imgAdd"
              @imgDel="$imgDel"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('editForm')"
              >提交</el-button
            >
            <el-button @click="resetForm('editForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import LabelInput from "../components/edit/LabelAdd.vue";
export default {
  components: { Header, LabelInput },
  data() {   return {
      editForm: {
        id: "",
        userId: "",
        titel: "",
        description: "",
        content: "",
        labels: ""
      },
      rules: {
        title: [
          { required: true, message: "请输入标题", trigger: "blur" },
          {
            min: 3,
            max: 50,
            message: "长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
        description: [
          { required: true, message: "请输入摘要", trigger: "blur" },
        ],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      //将标签信息加入到表单
      this.editForm.labels = this.getLabelInfo();
      const _this = this;
      //校验标签
      if (this.editForm.labels.length === 0) {
         this.$message({
                message: "标签不能为空",
                type: "error",
                showClose: true,
              });
        return;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post("/blog/edit", _this.editForm, {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              _this.$message({
                showClose: true,
                message: "操作成功！",
                type: "success",
              });
              _this.$router.push("/blogs");
            });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //markdown编辑器将图片上传到服务器
    $imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      var formdata = new FormData();
      formdata.append("image", $file);
      const _this = this;
      //console.log(formdata.get("image"));
      this.$axios({
        url: "/blog/imgUpload",
        method: "POST",
        data: formdata,
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: localStorage.getItem("token"),
        },
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        /**
         * $vm 指为mavonEditor实例，可以通过如下两种方式获取
         * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
         * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
         */
        let result = res.data;
        let imgUrl = "";
        //判断上传是否成功
        if (result.code === 200) {
          //成功
          imgUrl = result.data;
        } else {
          //失败
          imgUrl = "";
        }
        _this.$refs.md.$img2Url(pos, imgUrl);
      });
    },
    //取消上传
    $imgDel(pos) {
    },
    //获取标签信息
    getLabelInfo() {
      return this.$refs.labelInput.getLabelInfo();
    },
    loadLabelInfo(labels) {
      this.$refs.labelInput.loadLabelInfo(labels);
    }
  },
  created() {
    const blogId = this.$route.params.blogId;
    const _this = this;
    if (blogId) {
      this.$axios.get("/blog/detail/" + blogId).then((res) => {
        const blog = res.data.data;
        _this.editForm = blog;
        //将标签信息传给组件
        _this.loadLabelInfo(_this.editForm.labels);
      });
    }
  },
};
</script>

<style scoped>
.demo-editForm {
  max-width: 900px;
  margin: 20px auto;
}
.m-edit-box {
  background-color: white;
  width: 1000px;
  margin: 80px auto;
  display: flex;
  border-radius: 8px;
  box-shadow: 0 0 6px rgb(0 0 0 / 20%);
}
.m-edit-content {
  padding-top: 30px;
  padding-left: 15px;
  width: 900px;
  padding-bottom: 30px;
}
.m-edit-description-title {
  font-size: 15px;
}
.m-edit-description-input {
  font-size: 15px;
}
</style>