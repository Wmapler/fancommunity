import request from '@/utils/request';
// 获取小区
export function getVillageAPI(data){
    return request.post("/village/showVillage",data)
}
// 修改小区
export function updateVillageAPI(data) {
    return request.post("/village/modifyVillage",data)
}