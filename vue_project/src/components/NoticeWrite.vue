<template>
  <div>
    <section class="content-box">
      <h1>공지사항 작성</h1>
      <form method="post" action="#">
        <table class="table table-borderless shadow-lg p-3 rounded">
          <tr>
            <td class="border border-dark">제목</td>
            <td class="border border-dark bg-white">
              <input v-model.trim="title" ref="title" type="text" name="title" placeholder="제목을 입력하세요" />
            </td>
            <td class="border border-dark">{{id}}</td>
          </tr>
          <tr class="border border-bottom-0 border-dark bg-white">
            <td colspan="3"></td>
          </tr>
          <tr class="bg-white">
            <td id="content-cell" class="border border-top-0 border-dark text-left" colspan="3">
              <textarea v-model.trim="content" ref="content" name="content"></textarea>
            </td>
          </tr>
        </table>
        <button @click.prevent="addNotice" class="btn btn-primary">등록</button>
      </form>
    </section>
  </div>
</template>

<script>
import http from "@/http-common.js";

export default {
    data() {
        return {
            title: ''
            , content: ''
        }
    },
    computed: {
        id() {
            return this.$store.state.id;
        }
    },
    methods: {
        addNotice() {
            if(this.title.length == 0) {
                alert('제목을 입력해 주세요!');
                this.$refs.title.focus();
                return;
            }
            if(this.content.length == 0) {
                alert('내용을 입력해 주세요!');
                this.$refs.content.focus();
                return;
            }
            
            http.post('/notice/write', {
                title: this.title
                , content: this.content
                , userid: this.id
            })
            .then((response) => {
                if(response.data) {
                    alert('공지사항이 등록되었습니다!');
                } else {
                    alert('공지사항 등록에 실패했습니다!');
                }
                this.$router.push('/notice/list');
            })
            .catch((error) => {
                alert('Error: ' + error);
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
input, textarea {
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