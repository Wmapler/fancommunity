import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: '登录页',
    component: () => import('@/views/LoginView.vue'),
  },
  {
    path: '/home',
    name: '',
    component: () => import('@/views/HomeView.vue'),
    children:[
      {
        path: '',
        name: '欢迎',
        component: () => import('@/views/WelcomeView.vue'),
      },
      {
        path: 'people',
        name: '居民管理',
        component: () => import('@/views/PeopleView.vue'),
      },
      {
        path: 'village',
        name: '小区管理',
        component: () => import('@/views/VillageView.vue'),
      },
      {
        path: 'notice',
        name: '公告管理',
        component: () => import('@/views/NoticeView.vue'),
      },
      {
        path: 'activity',
        name: '活动管理',
        component: () => import('@/views/ActivityView.vue'),
      },
      {
        path: 'repair',
        name: '报修处理',
        component: () => import('@/views/RepairView.vue'),
      },
      {
        path: 'feedback',
        name: '意见反馈',
        component: () => import('@/views/FeedbackView.vue'),
      },
      {
        path: 'phone',
        name: '电话管理',
        component: () => import('@/views/PhoneView.vue'),
      },
      {
        path: 'questionnaire',
        name: '问卷管理',
        component: () => import('@/views/QuestionnaireView.vue'),
      },
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
