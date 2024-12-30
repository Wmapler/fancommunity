import axios from "axios";
import { Message } from 'element-ui'
const request = axios.create({
  // 请求根路径
  baseURL: "/community",
  timeout: 5000,
});
request.interceptors.request.use(
  config => {
    //设置请求头信息
    let token = localStorage.getItem('token') || '';
    const headersOption = {
      token,
    };
    config.headers = Object.assign(config.headers, headersOption);
    return config;
  },
  err => {
    console.log(err);
  },
);

//响应拦截处理
request.interceptors.response.use(res => {
  return res
}, err => {
  Message({
    message: err.message,
    type: 'error',
    duration: 5 * 1000
  })
  return Promise.reject(err)
})

export default request;
