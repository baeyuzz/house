<template>
  <div>
    <section class="content-box">
      <h1>공지 사항 보기</h1>
      <div class="article-box">
        <table class="table table-borderless shadow-lg p-3 rounded">
          <tr>
            <td class="border border-dark">{{notice.no}}</td>
            <td class="border border-dark">{{notice.title}}</td>
            <td class="border border-dark">{{notice.userid}}</td>
          </tr>
          <tr class="border border-bottom-0 border-dark bg-white">
            <td colspan="2"></td>
            <td class="text-muted">조회수: {{notice.hitCount}}</td>
          </tr>
          <tr class="bg-white">
            <td
              id="content-cell"
              class="border border-top-0 border-dark text-left"
              colspan="3"
            >{{notice.content}}</td>
          </tr>
        </table>
        <div class="d-flex justify-content-end">
          <router-link class="btn btn-primary m-2" to="/notice/list" title="목록으로">
            <i class="fas fa-clipboard-list"></i>
          </router-link>
          <template v-if="admin">
            <button
              class="btn btn-warning m-2"
              @click="goModifyPage"
              title="수정하기"
            >
              <i class="fas fa-tools"></i>
            </button>
            <button
              class="btn btn-danger m-2"
              @click="deleteNotice"
              title="삭제하기"
            >
              <i class="fas fa-trash-alt"></i>
            </button>
          </template>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";

export default {
  data() {
    return {
      no: 0,
      notice: {}
    };
  },
  computed: {
    admin() {
      return this.$store.state.admin;
    }
  },
  created() {
    console.log('Notice Detail created() called!');
    this.no = this.$route.params.no;
    http
      .get("/notice/detail/" + this.no + "/" + this.admin)
      .then(response => {
        this.notice = response.data;
      })
      .catch(error => {
        alert("Error: ", error);
      });
  },
  methods: {
      goModifyPage() {
        this.$router.push('/notice/modify/' + this.no);
      }
      , deleteNotice() {
        http
          .delete('/notice/delete/' + this.no)
          .then(response => {
            if(response.data) {
              alert('공지사항이 삭제되었습니다!');
            } else {
              alert('공지사항 삭제에 실패했습니다!');
            }
            this.$router.push('/notice/list');
          })
          .catch(error => {
            alert('Error: ' + error);
            this.$router.push('/notice/list');
          });
      }
  },
};
</script>

<style scoped>
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