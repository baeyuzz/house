import Vue from 'vue';
import Vuex from 'vuex';
import Constant from './Constant.js';
import http from './http-common.js';
Vue.use(Vuex);
const store = new Vuex.Store({
    state: {
        qnaItems: [],
        qna: {}

        , name: ''
        , id: ''
        , admin: false
    },
    actions: {
        [Constant.GET_QNALIST]: store => {
            http.get('/qna')
                .then(response => {store.commit(Constant.GET_QNALIST, { qnaItems: response.data });
                    console.log("qnalist")
            }
                )
                .catch(exp => alert('getQnAList처리에 실패하였습니다.' + exp));
        },
        [Constant.GET_QNA]: (store, payload) => {
            http.get('/qna/' + payload.no)
                .then(response => store.commit(Constant.GET_QNA, { qna: response.data }))
                .catch(exp => alert('getQnA처리에 실패하였습니다.' + exp));

        },
        [Constant.ADD_QNA]: (store, payload) => {
            http.post('/qna', {
                    qna_userid: payload.qna.qna_userid,
                    qna_title: payload.qna.qna_title,
                    qna_content: payload.qna.qna_content
                })
                .then(() => {
                    console.log('추가하였습니다.');
                    store.dispatch(Constant.GET_QNALIST);
                })
                .catch(exp => alert('추가 처리에 실패하였습니다.' + exp));
        },
        [Constant.MODIFY_QNA]: (store, payload) => {
            http.put('/qna/qna', {
                qna_no: payload.qna.qna_no,
                qna_title: payload.qna.qna_title,
                qna_content: payload.qna.qna_content   
             })
            .then(() => {
                console.log('수정하였습니다.');
                store.dispatch(Constant.GET_QNALIST);
            })
            .catch(exp => alert('수정 처리에 실패하였습니다.' + exp));
        },
    },
    mutations: { // 저장소에 데이터 실제 반영(commit시 호출됨)
        [Constant.GET_QNALIST]: (state, payload) => {
            state.qnaItems = payload.qnaItems;
        }
        , [Constant.GET_QNA]: (state, payload) => {
            state.qna = payload.qna;
        }

        , setname: (state, payload) => {
            state.id = payload.id;
            state.name = payload.name;
            if(payload.name == 'admin') {
                state.admin = true;
            }
        }
        , resetname: (state) => {
            state.name = '';
            state.admin = false;
        }
    }
});


export default store;