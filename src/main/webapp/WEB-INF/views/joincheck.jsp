<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join</title>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style>

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
	float : left;
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
#ok {
    background-color: #8fc242; /*초록색*/

}
#edit{
    background-color: #ffc266; /*노란색*/

}

</style>
</head>
<body>
	<jsp:include page="top_menu.jsp" />
	<div class="container center-block" align="center">
		<h3>회원 정보 확인</h3>
		<div class="col-lg-3"></div>

		<div class="col-lg-6" align="center">
			<div>
				<table class="table">
					<tr>
						<th>아이디</th>
						<td>${param.userid }</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>${param.userpwd }</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${param.username }</td>
					</tr>
					<tr>
						<th>주소</th>
						<td>${param.address }</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${param.email }</td>
					</tr>
				</table>
			</div>
			<div class = "form-group d-flex justify-content-center">
				<form class="form-group" method="get"
					action="${pageContext.request.contextPath}/user/join.do">
					<button type="submit" class="btn button pl-2 pr-2" id = "ok">확인</button>
				</form>
				<form class="form-group" method="get"
					action="${pageContext.request.contextPath}/user/useredit.go">
					<button type="submit" class="btn button pl-2 pr-2" id ="edit">수정</button>
				</form>
			</div>
		</div>

		<div class="col-lg-3"></div>
	</div>
</body>
</html>