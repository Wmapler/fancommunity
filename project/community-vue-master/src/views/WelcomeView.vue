<template>
  <div class="welcome">
    <div class="box">
      <p>欢迎使用便民服务社区管理系统</p>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="less" scoped>
.welcome {
  width: 87.5vw;
  height: 100vh;
  background: url('@/assets/background.jpg') no-repeat;
  background-size: 100, 100vh;

  .box {
    position: relative;
    height: 100%;
    width: 100%;
    background: rgba(255, 255, 255, .6);

    p {
      position: absolute;
      left: 50%;
      top: 60%;
      font-size: 24px;
      transform: translate(-50%, -50%);
    }
  }
}</style>