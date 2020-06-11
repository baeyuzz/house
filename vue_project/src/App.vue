<template>
  <div id="app">
    <nav-header></nav-header>
    <router-view></router-view>
  </div>
</template>

<script>

import Vue from 'vue';
import VueRouter from 'vue-router';

import NavHeader from '@/components/NavHeader.vue';
import MainPage from './components/MainPage.vue';
import HouseDetail from '@/components/HouseDetail.vue';
import NoticeList from '@/components/NoticeList.vue';
import NoticeDetail from '@/components/NoticeDetail.vue';
import Login from '@/components/Login.vue';
import Join from '@/components/Join.vue';
import FindPw from '@/components/FindPw.vue';
import ResetPw from './components/ResetPw.vue';

import QnaList from './components/QnaList.vue';
import QnaDetail from './components/QnaDetail.vue';
import QnaAdd from './components/QnaAdd.vue';
import QnaModify from './components/QnaModify.vue'

Vue.use(VueRouter);

const router = new VueRouter({
  mode : 'history',
  routes : [
    {path: '/', component: MainPage}
    , {path: '/house/detail/:no', component: HouseDetail}
    , {path: '/notice/list', component: NoticeList}
    , {path: '/notice/detail/:no', component: NoticeDetail}
    , {path: '/user/login', component: Login}
    , {path: '/user/join', component: Join}
    , {path: '/user/findpw', component: FindPw}
    , {path: '/user/resetpw/:userid', component: ResetPw}
    
    , {path:'/qna',component:QnaList}
    , {path:'/qna/:no',component:QnaDetail}
    , {path:'/addQna',component:QnaAdd}
    , {path:'/update/:no',component:QnaModify}
  ]
});

export default {
  name : 'app',
  components: {
    NavHeader
  },
  router

  , data() {
    return {
      qnaItems: [],
      qna : {}
    }
  }
};

</script>

<style>


</style>
