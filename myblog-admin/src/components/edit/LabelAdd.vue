<template>
  <div>
    <el-form-item label="标签" class="m-form-item-label" prop="label-item">
      <div class="m-form-item-label-box">
        <el-input
          v-model="labelInputVal"
          placeholder="按回车添加标签"
          class="m-input-label"
          @keyup.enter.native="addLabel"
        ></el-input>
        <el-button
          type="primary"
          plain
          class="m-button-label-add"
          size="small"
          @click="addLabel"
          >添加</el-button
        >
        <!-- 标签 -->
        <div class="m-input-label-item-box" v-for="(label, i) in labels">
          <div class="m-input-label-item">
            <span class="m-input-label-item-word">{{label}}</span>
            <svg
              t="1650872517715"
              class="m-input-label-item-remove"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="2597"
              width="9"
              height="9"
              @click="removeLabel(i)"
            >
              <path
                d="M1007.603202 929.818395L592.684241 514.699473 1007.603202 99.780512c10.797891-10.797891 16.196837-24.795157 16.196837-38.992385 0-13.997266-5.398946-28.194493-16.196837-38.992384-21.595782-21.595782-56.388987-21.595782-77.984768 0L514.699473 436.914665 99.780512 21.795743C78.18473 0.199961 43.391525 0.199961 21.795743 21.795743s-21.595782 56.388987 0 77.984769l415.118922 414.918961L21.795743 929.818395c-21.595782 21.595782-21.595782 56.388987 0 77.984768s56.388987 21.595782 77.984769 0l414.918961-415.118922L929.818395 1007.603202c10.797891 10.797891 24.795157 16.196837 38.992384 16.196837 13.997266 0 28.194493-5.398946 38.992384-16.196837 21.395821-21.395821 21.395821-56.388987-0.199961-77.784807z"
                fill="#999999"
                p-id="2598"
              ></path>
            </svg>
          </div>
        </div>
      </div>
    </el-form-item>
  </div>
</template>

<script>
export default {
  data() {
    return {
      labels: [],
      labelInputVal: "",
      labelsSize: 5,
    };
  },
  methods: {
    //点击添加标签后添加标签
    addLabel() {
      //如果不为空
      if (this.labelInputVal.length !== 0) {
        //如果标签个数太多
        if (this.labels.length >= this.labelsSize) {
          let _this = this;
          //弹出警告框
          this.$message({
            message: "标签最多设置" + _this.labelsSize + "个",
            type: "warning",
            howClose: true,
          });
        } else {
          //判断当前是否重复
          let flag = true;
          this.labels.forEach((element) => {
            if (element === this.labelInputVal) {
              this.$message({
                message: "标签已重复",
                type: "warning",
                showClose: true,
              });
              flag = false;
              return;
            }
          });
          if (flag) {
            this.labels.push(this.labelInputVal);
            this.labelInputVal = "";
          }
        }
      }
    },
    removeLabel(index) {
        if (this.labels.length > 0) {
            this.labels.splice(index, 1);
        }
    },
    getLabelInfo() {
      return this.labels;
    },
    setLabelsMaxSize(number) {
      this.labelsSize = number;
    },
    loadLabelInfo(labels) {
      
      if (labels.length <= this.labelsSize) {
        this.labels = labels;
      }  
    }
  },
  created() {
    
  }
};
</script>

<style>
.m-input-label {
  width: 130px !important;
}
.m-input-label input {
  float: left;
  width: 130px;
}
.m-form-item-label-box {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.m-button-label-add {
  margin: 0 10px !important;
  height: 40px;
}
.m-input-label-item-box {
  margin: auto 0px;
  display: flex;
}
.m-input-label-item {
  font-size: 12px;
  font-weight: 600;
  color: #999999;
  height: 20px;
  padding: 4px;
  line-height: 20px;
  background: #f4f8fc;
  border-radius: 5px;
  border: 1px solid rgba(38, 125, 204, 0.2);
  margin-right: 8px;
  cursor: pointer;
  margin-top: 7px;
}
.m-input-label-item-remove {
  margin: 0 3px 0 5px;
}
.m-input-label-item-remove:hover > path {
  fill: #5297d6;
}
.m-input-label-item-remove:hover ::before {
  color: #5297d6;
  font-size: 20px;
}
</style>