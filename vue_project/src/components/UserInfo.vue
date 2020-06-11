<template>
	<div class="container">
		<div class="col-md-4"></div>

		<div id="mypage" class="col-md-4 center-block mt-3">
			<h3 id="title">회원 정보 확인</h3>
			<div id="info">
				<table class="table">
					<tr>
						<th>아이디</th>
						<td>${user.userid}</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>${user.userpwd}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${user.username}</td>
					</tr>
					<tr>
						<th>주소</th>
						<td>${user.address}</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${user.email}</td>
					</tr>
				</table>
			</div>
			<div id="infobtn">
				<a href="${pageContext.request.contextPath}/"
					class="btn button pl-2 pr-2" id="ok" role="button">확인</a> <a
					href="${pageContext.request.contextPath}/user/useredit.go"
					class="btn button pl-2 pr-2" id="edit" role="button">수정</a> <a
					href="${pageContext.request.contextPath}/user/removeConfirm.go"
					class="btn button pl-2 pr-2" id="delId" role="button">회원 탈퇴</a>
			</div>
		</div>
		<div class="col-md-4"></div>
	</div>
</template>
<script>
export default {
    
}
</script>
<style>
#mypage {
	text-align: center;
}

#info>p {
	text-align: left;
	font-size: 18px;
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