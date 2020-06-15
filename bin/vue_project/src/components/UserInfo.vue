<template>
	<div class="container">
		<div class="col-md-4"></div>

		<div id="mypage" class="col-md-4 center-block mt-3">
			<h3 id="title">회원 정보 확인</h3>
			<div id="info">
				<table class="table">
       
					<tr>
						<th>아이디</th>
						<td>{{userid}}</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>{{userpwd}}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>{{username}}</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>{{email}}</td>
					</tr>
					<tr>
						<th>주소</th>
						<td>{{address}}</td>
					</tr>
				</table>
			</div>
			<div id="infobtn">
				<a @click.prevent="goMain" class="btn button pl-2 pr-2" id="ok" role="button">확인</a>
				<a @click.prevent="update" class="btn button pl-2 pr-2" id="edit" role="button">수정</a>
				<a @click.prevent="close" class="btn button pl-2 pr-2" id="delId" role="button">회원 탈퇴</a>
			</div>
		</div>
		<div class="col-md-4"></div>
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
     
          this.$router.push("/user/update");
        
	},
	goMain(){
		this.$router.push("/");
	},
	close(){
		this.$router.push("/user/closeAccount")
		
	}
  }
};
</script>
<style scoped>
#mypage {
	text-align: center;
}

#info>p {
	text-align: left;
	font-size: 18px;
}

h3 {
	text-align: center;
}

th {
	width : 40%;
}
td{
	width : 60%;
	text-align: left;
}
table tr th, table tr td {
	font-size: 18px;
}

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
#ok{
    background-color: #8fc242; /*초록색*/

}
#edit{
    background-color: #ffc266; /*노란색*/

}
#delId{
    background-color: #ff2626; /*빨간색*/
}

.center-block {
	margin: auto;
}
</style>