<template>
   <div>
    <section class="content-box">
      <br>
      <h2 style="text-align : center">Q & A</h2>
      <br>
      <div class="article-box">
        <table class="table table-borderless shadow-lg p-3 rounded">
          <tr>
            <td class="border border-dark">{{qna.qna_no}}</td>
            <td class="border border-dark">제목 : {{qna.qna_title}}</td>
            <td class="border border-dark">작성자 : {{qna.qna_userid}}</td>
            <td class="border border-dark">작성일 : {{qna.qna_datetime}}</td>
          </tr>
          <tr class="border border-bottom-0 border-dark bg-white">
            <td colspan="3"></td>
            <td class="text-muted" style="text-align : right">조회수: {{qna.hitCount}}</td>
          </tr>
          <tr class="bg-white">
            <td id="content-cell" class="border border-top-0 border-dark text-left" colspan="4">
              {{qna.qna_content}}
            </td>
          </tr>
        </table>
        <br>
         <div style="text-align : left">
          <button
            data-toggle="collapse"
            href="#news"
            aria-expanded="false"
            aria-controls="news"
            class="btn btn-primary"
          >답변 보기</button>
        </div>
        <div class="collapse" id="news">
          <div class="container" v-if="name.length > 0">
            <hr>
              <div class="input-group">
              <label for="content"> comment </label>
               <input v-model = "comment" type="text" class="form-control" placeholder="내용을 입력하세요.">
               <span>
                    <button @click.prevent="regComment" class="btn-warning btn">등록</button>
               </span>
              </div>
            <br>
          </div>
          <table class="table text-left" style="font-size : 14px">
            <tr v-for="reply in replies" v-bind:key = "reply.no">
              <td colspan="2">
              {{reply.comment}}
              </td>
              <td style="text-align : right">
               {{reply.writer}}  /  {{reply.reg_date}}
                    <span v-if="nowid == reply.writer">
                      <button
                        class="btn btn-danger m-2"
                        @click="deleteReply(reply.no)"
                        title="삭제하기">
                        <i class="fas fa-trash-alt"></i>
                      </button>
                    </span>
              </td>
            </tr>
          </table> 
        </div>

        <div class="d-flex justify-content-end">
          <router-link class="btn btn-primary m-2" to="/qna" title="목록으로">
            <i class="fas fa-clipboard-list"></i>
          </router-link>
          <template v-if="qna.qna_userid == id">
            <button
              class="btn btn-warning m-2"
             @click="update(qna.qna_no)"
              title="수정하기">
              <i class="fas fa-tools"></i>
            </button>
            <button
              class="btn btn-danger m-2"
              @click="deleteQnA(qna.qna_no)"
              title="삭제하기">
              <i class="fas fa-trash-alt"></i>
            </button>
          </template>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Constant from "../Constant";
import http from '@/http-common.js'

export default {
  data: function() {
    return {
      replies : [],
      writer : '',
      comment : '',
      qno : '',
      name : this.$store.state.name,
      nowid : this.$store.state.id,
    };
  },
  created() {
    let no = this.$route.params.no;
    this.qno = no;
    this.$store.dispatch(Constant.GET_QNA, { no });
    this.getReplies(no);
  },
  computed: {
    qna() {
      return this.$store.state.qna; 
    }
    , id() {
      return this.$store.state.id;
    }
  },
  methods: {
    update(no) {
      this.$router.push('/update/'+no); 
    }
    , deleteQnA(no) {
      http.delete('/rest/qna/' + no)
      .then(() => {
        this.$router.push('/qna');
      })
      .catch((error) => {
        alert('Error: ' + error);
      });
    },
    getReplies(no){
      http.get('rest/qna/reply/'+no)
      .then((response)=>{
        this.replies = response.data;
      })
      .catch((err) =>{
        alert('Reply Error ! '+ err)
      })
    },
    regComment(){
      http.post('rest/qna/reply',{
        qno : this.qno,
        writer : this.$store.state.id,
        comment : this.comment,
      })
      .then(()=>{
        this.getReplies(this.qno);
        this.comment = '';

      })
      .catch(()=>{
        console.log(this.qno);
        console.log(this.$store.state.id);
        console.log(this.comment);
      })
    },
    deleteReply(no){
      http.get('rest/qna/reply/del/'+no)
      .then(()=>{
        this.getReplies(this.qno);
      })
      .catch((err)=>{
        alert("댓글 지우기 실패 " + err)
      })
    }
  }
}
</script>

<style scoped>

.modifyBtn, .addBtn {
  color: white;
  vertical-align: middle;
}
.content-box {
  text-align: center;
}
.article-box {
  width: 700px;
  margin: auto;
}
#content-cell {
  max-height: 700px;
  overflow: scroll;
}
</style>