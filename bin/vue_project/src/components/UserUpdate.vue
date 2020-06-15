<template>
	<div class="container">
		<div id="mypage" class="mt-3 mb-3">
			<h3 id="title">회원 정보 수정</h3>

			<form method="post" action="#" >

				<!-- id -->
				<div class="form-group form-inline">
					<label for="userid" class="col-sm-2 offset-4 control-label">아이디</label>
					<div class="" id="iddiv">
						<span id="userid">{{this.userid}}</span>
					</div>
				</div>
				<!-- pwd -->
				<div class="form-group form-inline">
					<label for="userpwd" class="col-sm-2 offset-4 control-label">비밀번호</label>
					<div class="">
						<input type="password" class="form-control" name="userpwd" v-model="userpwd">
					</div>
				</div>
				<!-- name -->
				<div class="form-group form-inline">
					<label for="username" class="col-sm-2 offset-4 control-label">이름</label>
					<div class="">
						<input type="text" class="form-control"
							name="username" v-model="username">
					</div>
				</div>
				<!-- tel -->
				<div class="form-group form-inline">
					<label for="email" class="col-sm-2 offset-4 control-label">이메일</label>
					<div class="">
						<input type="text" class="form-control"
							name="email" v-model="email">
					</div>
				</div>
				<!-- address -->
				<div class="form-group form-inline">
					<label for="address" class="col-sm-2 offset-4 control-label">주소</label>
					<div class="">
						<input type="text" class="form-control"
							name="address" v-model="address">
					</div>
				</div>

				<div class="form-group">
						<button @click.prevent="update" class="btn button pl-2 pr-2" id="edit">수정하기</button>
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
		userid : this.$store.state.id,
		userpwd: '',
		username: '',
		email : '',
		address : '',
    };
  },
  created() {
    http
      .get("/rest/user/mypage/" + this.userid )
      .then(response => {
        this.username = response.data.username;
        this.userpwd = response.data.userpwd;
        this.email = response.data.email;
        this.address = response.data.address;
      })
      .catch(error => {
        alert("Error: ", error);
      });
  },
  methods: {
    update() {
     http.post('/rest/user/update', {
				userid : this.userid,
				username : this.username,
				userpwd : this.userpwd,
				email : this.email,
				address : this.address
            })
            .then((response) => {
                if(response.data.state) {
                    this.$router.push('/user/mypage/'+this.userid);
                } else {
                alert("Error ?! ");
                }
            })
            .catch((error) => {
                alert("Error ! ",error);
            });
    },
  }
};
</script>
<style scoped>
#mypage {
	text-align: center;
}

.form-group div input, .form-group label {
	font-size: 18px;
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

#edit {
	background-color: #ffc266; /*노란색*/
}

#userid {
	font-size: 18px;
}

#iddiv {
	text-align: left;
}
</style>