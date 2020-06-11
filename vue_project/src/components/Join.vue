<template>
    
	<div class="container" align="center">

		<h3>회원가입</h3>
      {{errorMessage}}

		<div class="col-lg-6" align="center">
		<div class="col-lg-3"></div>


			<form method="post" action="#">
                <!-- ${pageContext.request.contextPath}/user/joinCheck.go" -->
				<!-- ID -->
				<div class="form-group" align="left">
					<label for="">아이디</label> 
                    <input type="text" class="form-control" ref="userid"
						id="userid" name="userid" v-model="userid">
				</div>
				<!-- PWD -->
				<div class="form-group" align="left">
					<label for="">비밀번호</label>
                    <input type="password" ref="userpwd"
						class="form-control" id="userpwd" name="userpwd"
						v-model="userpwd">
				</div>
				<!-- name -->
				<div class="form-group" align="left">
					<label for="username">이름</label>
                    <input type="text" class="form-control" id="username" 
                    ref="username" name="username" v-model="username">
				</div>
				<!-- 번호 -->
				<div class="form-group" align="left">
					<label for="email">이메일</label>
                    <input type="text" class="form-control" id="email" name="email"
						ref="email" v-model="email">
				</div>
				<!-- 주소 -->
				<div class="form-group" align="left">
					<label for="address">주소</label>
                    <input type="text" class="form-control" id="address" name="address"
						ref="address" v-model="address">
				</div>
				<!-- 버튼 -->
				<div class="form-group" align="center">
					<button @click.prevent="join" class="btn button pl-2 pr-2" id = "join">회원가입</button>
					<button type="reset" class="btn button pl-2 pr-2" id = "reset">초기화</button>
				</div>
			</form>
		</div>
		<div class="col-lg-3"></div>
	</div>
</template>
<script>
import http from "@/http-common.js";

export default {
    data() {
        return {
            errorMessage: ''
            , userid: ''
            , userpwd: ''
            , username : ''
            , address : ''
            , email : ''
        }
    },
    methods: {
        join() {
            if(this.userid.length == 0) {
                alert('아이디를 입력하세요');
                this.$refs.userid.focus();
                return;
            }
            if(this.userpwd.length == 0) {
                alert('비밀번호를 입력하세요');
                this.$refs.userpwd.focus();
                return;
            }
            if(this.username.length == 0) {
                alert('이름을 입력하세요');
                this.$refs.username.focus();
                return;
            }

            http.post('/user/join', {
                userid: this.userid
                , userpwd: this.userpwd
                , username: this.username
                , address: this.address
                , email: this.email
            })
            .then((response) => {
                if(response.data.state) {
                    alert("회원 가입 완료")
                    this.$router.push('/');
                } else {
                    alert("회원 가입 실패")
                }
            })
            .catch((error) => {
                alert("Error ! 중복된 ID\n",error);
            });
        }
    },
};
</script>
<style scoped>
.button {
    border : none;
    color:#fff;
    padding: 15px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px;
    cursor: pointer;
}
#join{
    background-color: #8fc242; /*초록색*/

}
#reset{
    background-color: #ffc266; /*노란색*/

}

</style>

