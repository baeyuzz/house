<template>
	<div>
		<section>
			<h1 style="text-align : center">Q & A</h1>
			<form action="#" method="post">
				<div class="form-inline sms-form-center">
					<label class="mr-sm-2" for="title">Q&A 검색</label>
          <input v-model.trim="title" class="form-control mb-2 mr-sm-2" type="text"
						name="title" placeholder="검색할 단어를 입력하시오" />
					<button @click.prevent="searchQna"
						class="btn btn-success mb-2 mr-sm-2" type="submit">검색</button>
				</div>
			</form>

			<div class="table-box">
				<table class="table table-striped table-hover table-bordered">
					<thead class="bg-secondary">
						<tr>
							<th>번호</th>
							<th colspan="4">제목</th>
							<th>작성자</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="qna in qnalist" v-bind:key="qna.qna_no" @click="getQna(qna.qna_no)">
							<td>{{qna.qna_no}}</td>
							<td colspan="4">{{qna.qna_title}}</td>
							<td>{{qna.qna_userid}}</td>
						</tr>
					</tbody>
				</table>

				<div v-if="name.length > 0" class="d-flex justify-content-end">
					<router-link class="btn btn-info mb-2 mr-sm-2" to="/addQna">
					글쓰기</router-link>
				</div>
			</div>
		</section>
	</div>
	<!-- searchQna 백엔드에 만들어야함
      hit count도 만들어야함 -->
</template>

	<script>
import http from "@/http-common.js";

    export default {
      data(){
        return {
          title : "",
          searchlist: this.$store.state.qnaItems,
        }
      },
      computed : {
        qnalist(){
          return this.searchlist;
          },
        name() {
          return this.$store.state.name;
          }
        },
      created(){
		http.get("/qna")
		.then(response => {this.searchlist = response.data;})
		.catch(error => {alert("Error: ", error);});
		},
      methods: {
        searchQna() {
          console.log('click search');
          http.post("/qna/search/",{
            title : this.title
          })
          .then(response => {
          this.searchlist = response.data;
          })
          .catch(err => {
            alert("Error : ", err);
          });
        },
 
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

