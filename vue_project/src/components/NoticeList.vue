<template>
  <div>
    <section class="content-box">
      <br>
      <h2 style="text-align : center">공지사항</h2>
      <br>      
      <form action="#" method="post">
        <div class="form-inline sms-form-center">
          <label class="mr-sm-2" for="title">제목 검색</label>
          <input
            v-model.trim="title"
            class="form-control mb-2 mr-sm-2"
            type="text"
            name="title"
            placeholder="검색할 단어를 입력하시오"
          />
          <button
            @click.prevent="searchNotice"
            class="btn btn-success mb-2 mr-sm-2"
            type="submit"
          >검색</button>
        </div>
      </form>
      <div class="table-box">
        <table class="table table-striped table-hover table-bordered">
          <thead class="bg-secondary">
            <tr>
              <th>번호</th>
              <th colspan="4">제목</th>
              <th>조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="notice in noticeList"
              v-bind:key="notice.no"
              @click="noticeDetail(notice.no)"
            >
              <td :title="notice.no">{{notice.no}}</td>
              <td colspan="4" :title="notice.title">{{notice.title}}</td>
              <td>{{notice.hitCount}}</td>
            </tr>
          </tbody>
        </table>

        <div v-if="admin" class="d-flex justify-content-end">
          <router-link class="btn btn-info mb-2 mr-sm-2" to="/notice/write/">
            <i class="fas fa-pencil-alt" title="글쓰기"></i>
          </router-link>
        </div>
      </div>
      <!-- 시간 될 때 여기에 Paging 추가 -->
      <template v-if="paging.length > 0">
            <nav id="notice-page" v-html="paging"></nav>
      </template>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";
import $ from "jquery";


export default {
  data() {
    return {
      title: "",
      noticeList: [],
      paging : '',

    };
  },
  computed: {
    admin() {
      return this.$store.state.admin;
    }
  },
  created() {
    http
      .get("/rest/notice/list")
      .then(response => {
        this.noticeList = response.data.list;
        if(response.data.hasPaging){
          this.paging = response.data.paging;
        }
      })
      .catch(error => {
        alert("Error: ", error);
      });
  },
   updated() {
    let vue = this;
    $("#notice-page a").on("click", function(e) {
      let n = $(this).attr("id");
      e.preventDefault();

      http
        .get("rest/notice/pagenav/" + n )
        .then(response => {
          vue.noticeList = response.data.list;
          vue.paging = response.data.paging;
          if (response.data.hasPaging) {
            vue.paging = response.data.paging;
          }
        })
        .catch(error => {
          alert("Error: " + error);
        });
    });
  },
  methods: {
    searchNotice() {
      http
        .post("/rest/notice/search", {
            title: this.title
        })
        .then(response => {
          this.noticeList = response.data.list;
          this.paging = response.data.paging
        })
        .catch(error => {
          alert("Error: ", error);
        });
    },
    noticeDetail(no) {
      console.log(no);
      this.$router.push('/notice/detail/' + no);
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