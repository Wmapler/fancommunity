import request from '@/utils/request';
// 获取公告
export function getNoticeAPI(data){
    return request.post("/notice/showNotice",data)
}
// 添加公告
export function addNoticeAPI(data) {
    return request.post("/notice/addNotice",data)
}
// 修改公告
export function updateNoticeAPI(data) {
    return request.post("/notice/modifyNotice",data)
}
// 删除公告
export function deleteNoticeAPI(data) {
    return request.post("/notice/deleteNotice",data)
}