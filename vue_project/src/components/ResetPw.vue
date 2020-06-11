<template>
<div class="container">

	<h3>비밀번호 재설정</h3>
	<div class="center-block" align="center">
		<form method="post" action="#" class="form-horizontal">

			<!-- pwd1 -->
			<div class="form-group form-inline d-flex justify-content-center">
				<label for="pw1">새로운 비밀번호</label> <input type="password"
					class="form-control ml-2" name="pw1" v-model="pw1">
			</div>

			<!-- pwd2 -->
			<div class="form-group form-inline d-flex justify-content-center">
				<label for="pw2">비밀번호 확인</label> <input type="password"
					class="form-control ml-2" name="pw2" v-model="pw2">
			</div>

			<input class="button btn" id="changepwd" role="button"
				@click.prevent="resetpw" value="비밀번호 설정">
		</form>
	</div>
</div>
</template>

<script>
import http from "@/http-common.js";

export default {
    data() {
        return {
			pw1 : '',
			pw2 : '',
        }
    },
    methods: {
        resetpw () {
            http.post('/user/resetpw/'+this.$route.params.userid, {
                pw1: this.pw1
                , pw2: this.pw2
            })
            .then((response) => {
                if(response.data.state) {
                    alert("비밀번호 변경 성공! 로그인 해주세요")
                    this.$router.push('/');
                } else {
                alert("Error ! 동일한 비밀번호를 입력해주세요");
                }
            })
            .catch((error) => {
                alert("Error ! ",error);
            });
        }
    },
};

</script>

<style scoped>
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

#changepwd {
	background-color: #8fc242; /*초록색*/
}

h3, h4 {
	text-align: center;
}

#gohome {
	background-color: #99adc2; /*약간 회색*/
}

div div {
	text-align: center;
}
</style>