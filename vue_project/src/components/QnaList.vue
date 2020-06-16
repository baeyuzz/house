<template>
  <div>
    <section>
      <br>
      <h2 style="text-align : center">Q & A</h2>
      <br>
      <form action="#" method="post">
        <div class="form-inline sms-form-center">
          <label class="mr-sm-2" for="title">Q&A 검색</label>
          <input
            v-model.trim="title"
            class="form-control mb-2 mr-sm-2"
            type="text"
            name="title"
            placeholder="검색할 단어를 입력하시오"
          />
          <button @click.prevent="searchQna(title)" class="btn btn-success mb-2 mr-sm-2" type="submit">검색</button>
        </div>
      </form>

      <div class="table-box">
        <table class="table table-striped table-hover table-bordered">
          <thead class="bg-secondary">
            <tr>
              <th>번호</th>
              <th colspan="4">제목</th>
              <th>작성자</th>
              <th>조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="qna in qnas" v-bind:key="qna.qna_no" @click="getQna(qna.qna_no)">              <td>{{qna.qna_no}}</td>
              <td colspan="4">{{qna.qna_title}} 
                <span v-if="qna.replyCnt>0"> [{{qna.replyCnt}}] </span>
              </td>
              <td>{{qna.qna_userid}}</td>
              <td>{{qna.hitCount}}</td>
            </tr>
          </tbody>
        </table>
        <template v-if="qnaPaging.length > 0">
            <nav id="qna-page" v-html="qnaPaging"></nav>
          </template>
        <div v-if="name.length > 0" class="d-flex justify-content-end">
          <router-link class="btn btn-info mb-2 mr-sm-2" to="/addQna">글쓰기</router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";
import Constant from "@/Constant.js";
import $ from "jquery";

export default {
  data() {
    return {
      title: "",
      qnas : [],
      qnaPaging:'',
    };
  },
  computed: {
    name() {
      return this.$store.state.name;
    },
  },
  created() {
    this.$store.dispatch(Constant.GET_QNALIST);
    this.getQnaList();
  },
  updated() {
    let vue = this;
    $("#qna-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .get("rest/qna/pagenav/" + n )
        .then(response => {
          vue.qnas = response.data.list;
          vue.qnaPaging = response.data.paging;
          if (response.data.hasPaging) {
            vue.qnaPaging = response.data.paging;
          }
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });
  },
  methods: {
    getQna(no) {
      console.log("getQna -> " + no);
      this.$router.push("/qna/" + no);
    }
    ,getQnaList(){
      http
      .get("rest/qna")
      .then(response => {
        this.qnas = response.data.list;
        if(response.data.hasPaging){
          this.qnaPaging = response.data.paging;
        }
      })
      .catch(error=>{
        alert("Error ! " + error)
      });
    },
    searchQna(t) {
      console.log("search() called!!");
      http
        .post("rest/qna/search", {
          title : t
        })
        .then(response => {
          this.qnaPaging = response.data.paging;
          this.qnas = response.data.list;
        })
        .catch(error => {
          alert("Error: " + error);
        });
    }
  }
};
</script>

<style scoped>
.content-box {
  text-align: center;
}

.table-box {
  width: 700px;
  margin: auto;
}

.sms-form-center {
  justify-content: center;
}

.table {
  margin-top: 20px;
  table-layout: fixed;
}

td {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>

