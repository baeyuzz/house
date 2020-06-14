<template>
  <div>
    <section class="content-box">
      <h1>공지사항 수정</h1>
      <form method="post" action="#">
        <table class="table table-borderless shadow-lg p-3 rounded">
          <tr>
            <td class="border border-dark">{{notice.no}}</td>
            <td class="border border-dark bg-white">
              <input v-model.trim="notice.title" ref="title" type="text" name="title"/>
            </td>
            <td class="border border-dark">{{notice.userid}}</td>
          </tr>
          <tr class="border border-bottom-0 border-dark bg-white">
            <td colspan="2"></td>
            <td class="text-muted">조회수: {{notice.hitCount}}</td>
          </tr>
          <tr class="bg-white">
            <td id="content-cell" class="border border-top-0 border-dark text-left" colspan="3">
              <textarea v-model.trim="notice.content" ref="content" name="content"></textarea>
            </td>
          </tr>
        </table>
        <button @click.prevent="modify" class="btn btn-primary">수정</button>
      </form>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";

export default {
  data() {
    return {
        notice: {}
    };
  },
  computed: {
    admin() {
      return this.$store.state.admin;
    }
  },
  created() {
    http
      .get("/rest/notice/detail/" + this.$route.params.no + "/" + this.admin)
      .then(response => {
        this.notice = response.data;
      })
      .catch(error => {
        alert("Error: ", error);
        this.$router.push('/notice/list')
      });
  },
  methods: {
      modify() {
          if(this.notice.title.length == 0) {
              alert('제목을 입력해 주세요');
              this.$refs.title.focus();
              return;
          }
          if(this.notice.content.length == 0) {
              alert('내용을 입력해 주세요');
              this.$refs.content.focus();
              return;
          }
          http
            .put('/rest/notice/modify', {
                no: this.notice.no
                , title: this.notice.title
                , content: this.notice.content
                , userid: this.notice.userid
            })
            .then(response => {
                if(response.data) {
                    alert('공지사항이 수정되었습니다');
                } else {
                    alert('공지사항 수정 실패');
                }
                this.$router.push('/notice/detail/' + this.notice.no);
            })
            .catch(error => {
                alert('Error: ' + error);
                this.$router.push('/notice/detail/' + this.notice.no);
            });
      }
  }
};
</script>

<style scoped>
.content-box {
  text-align: center;
  width: 700px;
  margin: auto;
}
input,
textarea {
  border: 0;
  width: 100%;
  height: 100%;
}
input,
textarea {
  outline: none;
  border: 0;
}
textarea {
  resize: none;
}
#content-cell {
  height: 500px;
}
</style>