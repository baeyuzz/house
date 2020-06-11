<template>
  <div>
    <ul>
      <li v-for="(qna) in qnalist" v-bind:key="qna.qna_no" @click="getQna(qna.qna_no)">
        {{qna.qna_no}} -  {{qna.qna_userid}} - {{qna.qna_title}} - {{qna.qna_content}} - {{qna.qna_datetime}}
        <!-- {{qna.reply_content}} - {{qna.reply_datetime}} - {{qna.reply_userid}} -->
      </li>
    </ul>
    <router-link v-if="name.length > 0" to="/addQna">추가</router-link> &nbsp;

  </div>
</template>


<script>
import Constant from '../Constant';
// import { mapActions } from 'vuex'
    export default {
        computed : {
            qnalist(){
                return this.$store.state.qnaItems;
            }
            , name() {
              return this.$store.state.name;
            }
        },
        created(){
          console.log("created");
          this.$store.dispatch(Constant.GET_QNALIST);
        },
         methods: {
            getQna(no) {
            console.log('getQna -> '+ no);
            this.$router.push('/qna/'+no);
            },
        // ...mapActions([
        //     Constant.REMOVE_TODO,Constant.COMPLETE_TODO
        // ])
  },
};
</script>
<style scoped>

</style>