export default {
  setUser(state, data) {
    state.ucId = data.ucId;
    state.name = data.name;
    state.phone = data.phone;
    state.signCount = data.signCount;
    state.signTime = data.addTime;
    state.myImg = data.headImg;
  },
  setMessageNum(state, data){
    state.messageNum = data
  }
};
