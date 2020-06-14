<template>
   <div>
    <section class="content-box">
     <h2>Q&A</h2>
      <div class="article-box">
        <table class="table table-borderless shadow-lg p-3 rounded">
          <tr>
            <td class="border border-dark">{{qna.qna_no}}</td>
            <td class="border border-dark">제목 : {{qna.qna_title}}</td>
            <td class="border border-dark">작성자 : {{qna.qna_userid}}</td>
            <td class="border border-dark">작성일 : {{qna.qna_datetime}}</td>
          </tr>
          <!-- <tr class="border border-bottom-0 border-dark bg-white">
            <td colspan="2"></td>
            <td class="text-muted">조회수: {{notice.hitCount}}</td>
          </tr> -->
          <tr class="bg-white">
            <td id="content-cell" class="border border-top-0 border-dark text-left" colspan="4">
              {{qna.qna_content}}
            </td>
          </tr>
        </table>
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
      item: {},
    };
  },
  created() {
    let no = this.$route.params.no;
    this.$store.dispatch(Constant.GET_QNA, { no });

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