<template>
	<div class="container mt-3" align="center">
		<h4>탈퇴 하시겠습니까?</h4>
		<div id="answer">
			<a href="#" @click.prevent="close"
				class="btn button pr-2 pl-2" id="delId" role="button">탈퇴</a>
				<a href="#" @click.prevent="cancel"
				class="btn button pr-2 pl-2" id="cancel" role="button">취소</a>
		</div>
	</div>
</template>
<script>
	import http from "@/http-common.js";
export default {

	methods: {
		cancel(){
			this.$router.push("/user/mypage/"+this.$store.state.id);
		},
		close(){
			http
			.get("/user/closeAccount/"+this.$store.state.id)
			.then(() => {
			this.$store.commit("resetname");
			this.$router.push("/");
			})
			.catch(error => {
			alert("Error: ", error);
			});
		}
	}
	
}
</script>
<style type="text/css">
.button {
    border: none;
    color:#fff;
    padding: 15px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px;
    cursor: pointer;
}
#cancel{
    background-color: #8fc242; /*초록색*/

}
#delId{
    background-color: #ff2626; /*빨간색*/
}

</style>