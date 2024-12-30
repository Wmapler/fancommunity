import Vue from "vue";
import Vuex from "vuex";
import state from "@/store/state.js";
import getters from "@/store/getters.js";
import mutations from "@/store/mutations.js";
import actions from "@/store/actions.js";
import VueXAlong from "vuex-along";

Vue.use(Vuex);

const store = new Vuex.Store({
  state,
  getters,
  mutations,
  actions,
  plugins: [
    VueXAlong({
      name: "vuexAlong", // 存放在localStroage或者sessionStroage 中的名字
      local: false,
    }),
  ],
});

export default store;
