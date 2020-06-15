<template>
  <div>
    <section class="content-box">
     <h2>Q&A</h2>
      <div class="article-box">
        <table class="table table-borderless shadow-lg p-3 rounded">
          <tr>
            <td class="border border-dark">제목</td>
            <td class="border border-dark"> 
              <input type="text" v-model="qna.qna_title"/>
            </td>
          </tr>
          <tr class="bg-white">
            <td id="content-cell" class="border border-top-0 border-dark text-left" colspan="2">
              <textarea v-model="qna.qna_content" />
            </td>
          </tr>
        </table>
        <div class="d-flex justify-content-end">
          <router-link class="btn btn-primary m-2" to="/qna" title="목록으로">
            <i class="fas fa-clipboard-list"></i>
          </router-link>
          <span class="btn btn-primary m-2" @click="addQna">등록</span>

        </div>
      </div>
    </section>
  </div>

</template>

<script>
import Constant from '../Constant';
export default {
  created() {
    console.log('created');
  },
    data() {
        return {
            qna : {
                qna_content: '',
                qna_title: ''
            }
        }
    },
    computed: {
      id() {
        return this.$store.state.id;
      }
    },
    methods: {
        addQna(){
            if(this.qna.qna_content.trim() != ''){
                console.log(this.qna.qna_userid);
                this.qna.qna_userid = this.id;
                this.$store.dispatch(Constant.ADD_QNA, {qna : this.qna});
                this.$router.push('/qna');
            }else{
                console.log('공백입력.');
            }    
            this.clear();
        },
        clear(){
            this.qna = {
            };  
        }
    }
}     
</script>




<style scoped>

.content-box {
  text-align: center;
}
.article-box {
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
  max-height: 700px;
  overflow: scroll;
}
</style>