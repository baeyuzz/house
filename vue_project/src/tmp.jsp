<!-- vue에 쓸 코드 정렬할 용도로 만듦 -->
<template>
<div class="container">

	<h3>비밀번호 재설정</h3>
	<div class="center-block" align="center">
		<!-- <div v-if="different">
				<h4>동일한 비밀번호를 입력해주세요</h4>
		</div> -->
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
        findpw () {
            http.post('/user/resetpw', {
                pw1: this.pw1
                , pw2: this.pw2
            })
            .then((response) => {
                if(response.data.state) {
                    alert("비밀번호 변경 성공! 로그인 해주세요")
                    this.$router.push('/');
                } else {
                    alert("일치하는 정보 없음")
                }
            })
            .catch((error) => {
                alert("Error ! 동일한 비밀번호를 입력해주세요\n",error);
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