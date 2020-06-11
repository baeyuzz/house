<head>
<script>
	import Constant from '../Constant';
	    export default {
	      data() {
	    return {
	      title: "",
	    };
	  },
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
       searchNotice() {
      http
        .post("/notice/search", {
            title: this.title
        })
        .then(response => {
          this.noticeList = response.data;
        })
        .catch(error => {
          alert("Error: ", error);
        });
    },
  },
};
</script>
</head>