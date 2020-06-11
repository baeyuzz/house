<template>
  <div>
    <table>
      <tbody>
        <tr>
          <td>번호</td>
          <td>
            <span>{{qna.qna_no}}</span>
          </td>
        </tr>
        <tr>
          <td>제목</td>
          <td>
            <span>{{qna.qna_title}}</span>
          </td>
        </tr>
        <tr>
          <td>내용</td>
          <td>
            <span>{{qna.qna_content}}</span>
          </td>
        </tr>
        <tr>
          <td>등록일</td>
          <td>
            <span>{{qna.qna_datetime}}</span>
          </td>
        </tr>
        <tr>
          <td>id</td>
          <td>
            <span>{{qna.qna_userid}}</span>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <span class="addContainer" @click="movePage">
              목록
            </span>
            <template v-if="qna.qna_userid == id">
            <span class="addContainer" @click="update(qna.qna_no)">
              수정
            </span>
            <span class="addContainer" @click="deleteQnA(qna.qna_no)">
              삭제
            </span>
            </template>
          </td>
        </tr>
      </tbody>
    </table>
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
  
    movePage() {
      this.$router.push('/qna');
    }
    , update(no) {
      this.$router.push('/update/'+no); 
    }
    , deleteQnA(no) {
      http.delete('/qna/' + no)
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
.addContainer{
  background: linear-gradient(to right, #647811, #527810);
  display: inline-block;
  width: 3rem;
  border-radius: 5px 5px 5px 5px;
}
.modifyBtn, .addBtn {
  color: white;
  vertical-align: middle;
}

</style>