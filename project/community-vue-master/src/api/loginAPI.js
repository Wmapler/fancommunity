import request from '@/utils/request';
// 登录接口
export function loginAPI(loginId,password) {
    return request.post("/login-user/adminLogin", { loginId,password })
}