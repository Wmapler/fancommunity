import request from '@/utils/request';
// 登录接口
export function getActivityListAPI(data) {
    return request.post("/activity/showActivity", data)
}
// 修改活动
export function updateActivityAPI(data) {
    return request.post("/activity/modifyActivity", data)
}
// 添加活动
export function addActivityAPI(data) {
    return request.post("/activity/addActivity", data)
}