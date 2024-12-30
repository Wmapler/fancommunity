<template>
  <div class="home">
    <el-col :span="3" class="menu">
      <el-menu :default-active="activeIndex" class="el-menu-vertical-demo left_menu" @select="handleSelect">
        <el-menu-item index="/home/people">
          <i class="el-icon-user"></i>
          <span slot="title">居民管理</span>
        </el-menu-item>
        <el-menu-item index="/home/village">
          <i class="el-icon-office-building"></i>
          <span slot="title">小区管理</span>
        </el-menu-item>
        <el-menu-item index="/home/notice">
          <i style="margin-left:5px" class="iconfont icon-gonggao"></i>
          <span slot="title">&ensp; 公告管理</span>
        </el-menu-item>
        <el-menu-item index="/home/activity">
          <i style="font-size:22px;margin-left:1px" class="iconfont icon-activity-line"></i>
          <span slot="title">&ensp;活动管理</span>
        </el-menu-item>
        <el-menu-item index="/home/repair">
          <i class="el-icon-setting"></i>
          <span slot="title">报修处理</span>
        </el-menu-item>
        <el-menu-item index="/home/feedback">
          <i class="el-icon-document-checked"></i>
          <span slot="title">意见反馈</span>
        </el-menu-item>
        <el-menu-item index="/home/phone">
          <i class="el-icon-phone-outline"></i>
          <span slot="title">电话管理</span>
        </el-menu-item>
      </el-menu>
      <div id="user-info">
        <img src="" alt="" />
        <div id="name">{{ userName }}</div>
        <div id="logout"><a href="#" @click="logout">退 出</a></div>
      </div>
    </el-col>
    <el-col :span="21" style="background: #f7f7f7">
      <router-view></router-view>
    </el-col>
  </div>
</template>

<script>
export default {
  name: "HomeView",
  data() {
    return {
      activeIndex: "/home",
      userName: "",
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      this.$router.push(key);
      this.activeIndex = key;
    },
    // 退出登录
    logout() {
      this.$store.commit("setUser", { userName: null, phone: null });
      this.$router.push("/login");
    },
  },
  mounted() {
    this.userName = this.$store.getters.getUserName;
    if (this.$route.path === "/home") {
      this.activeIndex = "/home";
    } else {
      this.activeIndex = "/home";
      this.$router.push("/home");
    }
  },
  destroyed() {
    this.$store.commit("setUser", "");
  },
};
</script>

<style lang="less" scoped>
.home {
  .left_menu {
    // background: url("@/assets/menu_background.jpg");
    background-size: 100% 100vh;
    height: 100vh;
  }

  .menu {
    position: relative;

    #user-info {
      position: absolute;
      bottom: 50px;
      left: 50%;
      transform: translateX(-50%);
    }

    #logout {
      text-align: center;
    }
  }
}
</style>
<style lang="less">
.el-menu {
  background: rgba(255, 255, 255, 0.3);
}

.el-submenu .el-menu-item {
  padding-left: 60px !important;
  min-width: 180px;
}
</style>
