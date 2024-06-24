<template>
  <div>
    <div class="m-user-info-box">
      <div class="m-user-avatar-box">
        <el-avatar :size="60" :src="user.avatar"></el-avatar>
      </div>
      <div class="m-user-username">
        <router-link :to="'/user/' + user.username">{{
          user.username
        }}</router-link>
      </div>
      <div class="m-user-amount">文章数 {{ user.blogCount }}</div>
      <div class="m-user-amount">访问量 {{ user.views }}</div>
      <div class="m-user-amount">获赞数 {{ user.praises }}</div>
      <div class="m-user-edit" v-show="currentUser">
        <el-dropdown>
          <span class="el-dropdown-link">
            编辑<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <router-link to="/update/user"><el-dropdown-item>修改信息</el-dropdown-item></router-link>
            <router-link to="/update/password"><el-dropdown-item>修改密码</el-dropdown-item></router-link>
          </el-dropdown-menu>
        </el-dropdown>
        
      </div>
      <div class="m-user-introduction">简介: {{ user.profile }}</div>

      <div class="m-user-skill">
        <div class="m-user-skill-title">技能标签</div>
        <div class="m-user-skill-label-box">
          <div
            class="m-user-skill-label"
            v-for="skill in user.skills"
            @click="toLabelBlog(skill)"
          >
            {{ skill }}
          </div>
        </div>
        <div
          class="m-user-skill-label-null"
          v-show="user.skills === null || user.skills.length === 0"
        >
          暂无
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        avatar: "",
        blogCount: "",
        email: "",
        id: "",
        praises: "",
        profile: "",
        skills: [],
        username: "",
        views: "",
      },
      currentUser: false,
    };
  },
  methods: {
    loadUserInfo() {
      return new Promise((resolve, reject) => {
        var _this = this;
        this.$axios({
          url: "/user/userDetailed/" + this.$route.params.username,
          method: "get",
        }).then((res) => {
          _this.user = res.data.data;
          console.log(_this.user);
          //判断简介是否为空
          if (_this.user.profile === null || _this.user.profile.length === 0) {
            _this.user.profile = "这个人很懒，什么也没说";
          }
          resolve();
        });
      });
    },
    /* 跳转至分类标签页面 */
    toLabelBlog(label) {
      this.$router.push("/classic/" + label);
    },
  },
  mounted() {
    //初始化当前用户信息
    this.currentUser =
      this.$store.getters.getUser.username == this.$route.params.username;
  },
};
</script>

<style lang="css" scoped>
.m-user-info-box {
  display: flex;
  flex-direction: column;
  background-color: white;
  justify-content: center;
  width: 250px;
  height: 620px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.m-user-username {
  margin-top: 10px;
  margin-bottom: 10px;
}
.m-user-username a {
  color: #21b4dd;
  font-size: 20px;
  font-weight: bold;
}
.m-user-username a:hover {
  color: #66d7f7;
  text-decoration: none;
}
.m-user-amount {
  margin-top: 10px;
  font-weight: bold;
}
.m-user-introduction {
  margin: 10px;
  margin-top: 30px;
  font-weight: 500;
}
.m-user-edit {
  margin: 20px;
}
.m-user-skill {
  margin: 20px;
  height: 30%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.m-user-skill-label-box {
  margin: 10px 0px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.m-user-skill-label {
  font-size: 13px;
  font-weight: bold;
  color: #999999;
  height: 18px;
  padding: 4px;
  line-height: 18px;
  background: #ececec;
  border-radius: 2px;
  margin: 5px;
}
.m-user-skill-label:hover {
  color: #b3b3b3;
  cursor: pointer;
}
.m-user-skill-title {
  font-size: 18px;
  font-weight: bold;
  margin: 5px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>