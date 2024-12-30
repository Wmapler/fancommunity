import request from '@/utils/request';
// 获取帖子
export function getMultipleAPI(data){
    return request.post("/multiple/showMultipleManager",data)
}
// 添加公告
export function addNoticeAPI(data) {
    return request.post("/notice/addNotice",data)
}
// 修改帖子
export function updateMultipleAPI(data) {
    return request.post("/multiple/modifyMultiple",data)
}
// 删除公告
export function deleteNoticeAPI(data) {
    return request.post("/notice/deleteNotice",data)
}