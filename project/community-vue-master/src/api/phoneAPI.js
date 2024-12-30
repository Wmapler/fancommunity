import request from '@/utils/request';
// 获取电话
export function getPhoneAPI(data) {
    return request.post("/telephone/showTel",data)
}
// 新增电话
export function addPhoneAPI(data) {
    return request.post("/telephone/addTel",data)
}
// 修改电话
export function updatePhoneAPI(data) {
    return request.post("/telephone/modifyTel",data)
}
// 删除电话
export function deletePhoneAPI(data){
    return request.post("/telephone/deleteTel",data)
}