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
<title>Happy House - 회원가입</title>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style>
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
#join{
    background-color: #8fc242; /*초록색*/

}
#reset{
    background-color: #ffc266; /*노란색*/

}

</style>

</head>
<body>
	<div class="container" align="center">

		<h3>회원가입</h3>
		<div class="col-lg-3"></div>

		<div class="col-lg-6" align="center">

			<form method="post" action="${pageContext.request.contextPath}/user/joinCheck.go">
				<!-- ID -->
				<div class="form-group" align="left">
					<label for="">아이디</label> <input type="text" class="form-control"
						id="userid" name="userid" value="${param.userid}">
				</div>
				<!-- PWD -->
				<div class="form-group" align="left">
					<label for="">비밀번호</label> <input type="password"
						class="form-control" id="userpwd" name="userpwd"
						value="${param.userpwd}">
				</div>
				<!-- name -->
				<div class="form-group" align="left">
					<label for="username">이름</label> <input type="text"
						class="form-control" id="username" name="username" value="${param.username}">
				</div>
				<!-- 번호 -->
				<div class="form-group" align="left">
					<label for="email">이메일</label> <input type="text"
						class="form-control" id="email" name="email"
						value="${param.email}">
				</div>
				<!-- 주소 -->
				<div class="form-group" align="left">
					<label for="address">주소</label> <input type="text"
						class="form-control" id="address" name="address"
						value="${param.address}">
				</div>
				<!-- 버튼 -->
				<div class="form-group" align="center">
					<button type="submit" class="btn button pl-2 pr-2" id = "join">회원가입</button>
					<button type="reset" class="btn button pl-2 pr-2" id = "reset">초기화</button>
				</div>
			</form>
		</div>
		<div class="col-lg-3"></div>
	</div>
</body>
</html>
