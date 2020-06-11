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
          <tr class="bg-white">
            <td id="content-cell" class="border border-top-0 border-dark text-left" colspan="4">
              <textarea v-model="qna.qna_content" /></td>
          </tr>
        </table>
        <div class="d-flex justify-content-end">
          <router-link class="btn btn-primary m-2" to="/qna" title="목록으로">
            <i class="fas fa-clipboard-list"></i>
          </router-link>
            <button
              class="btn btn-warning m-2" @click="modifyqna" title="수정하기">
              <i class="fas fa-tools"></i>
            </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Constant from "../Constant";
export default {
  created() {
    let no = this.$route.params.no;
    this.$store.dispatch(Constant.GET_QNA, { no });
    console.log(no);
  },
  computed: {
    qna: {
      get() {
        return this.$store.state.qna;
      },
      set(newqna) {
        console.log("qnaInput computed set...");
        // this.qna.qna_no = newqna.qna_no;
        this.qna.qna_title = newqna.qna_title;
        this.qna.qna_content = newqna.qna_content;
        // this.qna.qna_datetime = newqna.qna_datetime;
      }
    }
  },
  methods: {
  
    modifyqna() {
      if (this.qna.qna_content.trim() != "") {
        console.log("할일 수정:: ");
        this.$store.dispatch(Constant.MODIFY_QNA, { qna: this.qna });
        this.$router.push("/qna");
      } else {
        alert.log("내용을 입력해주세요");
      }
      this.clear();
    },
    clear() {
      this.qna = {}; 
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