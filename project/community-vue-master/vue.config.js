const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/community": {
        target: "http://192.168.43.181:8084/",
        changeOrigin: true,
        ws: true,
        secure: false,
      },
    },
  },
})
