import request from '@/utils/request';
// 获取居民列表
export function getPeopleListAPI(data) {
    return request.post("/login-user/getUserList",data)
}
// 重置密码
export function resetPasswordAPI(loginId) {
    return request.post("/login-user/resetPassword",{loginId})
}
// 获取居民详细信息
export function getOnePeopleAPI(userId) {
    return request.post("/user-information/getUserInformationManager",{userId})
}
// 修改用户类型（认证）
export function updatePeopleTypeAPI(data) {
    return request.post("/login-user/changeUserType",data)
}