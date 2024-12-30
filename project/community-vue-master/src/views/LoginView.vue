<template>
  <div class="login-container">
    <div class="box">
      <h1>今天&nbsp;你又拥有</h1>
      <h1 class="h1_bottom">哪些事情呢？</h1>
      <h4>便民服务社区管理系统</h4>
      <div class="form-box">
        <el-form :model="ruleForm" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="账 号" prop="account">
            <el-input type="text" v-model="ruleForm.account" autocomplete="off" placeholder="请输入工号或手机号" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item label="密 码" prop="password">
            <el-input type="password" v-model="ruleForm.password" autocomplete="off" placeholder="请输入密码" size="small" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitLogin()" size="small">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import { loginAPI } from '@/api/loginAPI.js';

export default {
  name: 'LoginView',
  data() {
    return {
      ruleForm: {
        account: '',
        password: '',
      },
    };
  },
  methods: {
    async submitLogin() {
        const {data:res} = await loginAPI(this.ruleForm.account,this.ruleForm.password)
        if(res.msg === "success"){
            //保存数据
            window.localStorage.setItem("token",res.data.token);
            this.$router.push('/home')
        } else {
            this.$message.error('账号或密码错误！');
        }
    },
  },
};
</script>

<style lang="less" scoped>
.login-container {
  position: relative;
  height: 100vh;
  background: url('@/assets/background.jpg') no-repeat right center;
  background-size: 130% 100%;
  .box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 800px;
    height: 500px;
    background-color: rgba(255, 255, 255, 0.4);
    transform: translate(-50%, -50%);
    border-radius: 16px;
    box-shadow: 6px 6px 5px rgba(0, 0, 0, 0.4);
    .form-box {
      position: absolute;
      height: 100%;
      width: 340px;
      left: 0;
      background-color: #fff;
      border-radius: 16px 0 0 16px;
    }
    .demo-ruleForm {
      position: absolute;
      top: 48%;
      left: -4%;
      transform: translate(0, -50%);
      .el-button {
        margin-left: -30px;
      }
    }
    h1,
    h4 {
      color: #333;
    }
    h1 {
      position: absolute;
      top: 26px;
      right: 102px;
      font-size: 38px;
      letter-spacing: 6px;
    }
    h4 {
      position: absolute;
      top: 130px;
      right: 202px;
      letter-spacing: 2px;
    }
    .h1_bottom {
      top: 76px;
      right: 120px;
    }
  }
}
</style>
