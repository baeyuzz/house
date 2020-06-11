<template>
<div>
		<h4>비밀번호 찾기</h4>
		<div class="center-block" align="center">
			<form method="post" action="#">
				<div class="form-group">
					<label class="col-sm-4 control-label" >이름</label> 
					<div class="col-sm-4">
						<input type="text" class="form-control" name="username" v-model="username">
					</div>
				</div>
				
				<div class="form-group">
					<label  class="col-sm-4 control-label">아이디</label> 
					<div class="col-sm-4">
						<input type="text" class="form-control" name="userid" v-model="userid">
					</div>
				</div>
				
				<div class="form-group">
					<label  class="col-sm-4 control-label">이메일</label> 
					<div class="col-sm-4">
						<input type="text" class="form-control" name="email" v-model="email">
					</div>
				</div>
			
				<div class="form-group">
					<input @click.prevent="findpw" class="btn button pr-2 pl-2" id = "findpw" role="button" type="submit" value="비밀번호 찾기">
				</div>
			
			</form>
		</div>
</div>
</template>

<script>
	import http from "@/http-common.js";

export default {
    data() {
        return {
            errorMessage: ''
            , userid: ''
            , username : ''
            , email : ''
        }
    },
    methods: {
        findpw () {
            http.post('/user/findpw', {
                  userid: this.userid
                , username: this.username
                , email: this.email
            })
            .then((response) => {
                if(response.data.state) {
                  alert("회원 찾기 성공 ! 비밀번호 변경 페이지로 이동합니다");
                  this.$router.push('/user/resetpw/'+response.data.userid);
                } else {
                    alert("일치하는 정보 없음")
                }
            })
            .catch((error) => {
                alert("Error ! 일치하는 정보 없음\n",error);
            });
        }
    },
};

</script>


<style>
h4 {
	text-align: center;
}

.button {
	border: none;
	color: #fff;
	padding: 15px 0;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 15px;
	margin: 4px;
	cursor: pointer;
}

#findpw {
	background-color: #8fc242; /*초록색*/
}
</style>
