<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<script
	src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">


<meta charset="UTF-8">
<title>마이페이지</title>
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
</head>
<body>
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
					class="btn button pl-2 pr-2" id = "ok" role="button">확인</a>
				<a href="${pageContext.request.contextPath}/user/useredit.go"
					class="btn button pl-2 pr-2" id = "edit" role="button">수정</a> 
				<a href="${pageContext.request.contextPath}/user/removeConfirm.go"
					class="btn button pl-2 pr-2" id = "delId" role="button">회원 탈퇴</a>
			</div>
		</div>
		<div class="col-md-4"></div>
	</div>
</body>
</html>