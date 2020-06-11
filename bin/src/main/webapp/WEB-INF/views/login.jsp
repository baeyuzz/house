f<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Happy House - 로그인</title>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style>
#btns{
	text-align: center;
}
.button {
    border: none;
    color:#fff;
    padding: 15px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    cursor: pointer;
}

#gohome{
    background-color: #99adc2; /*약간 회색*/
}

#findpwd{
    background-color: #ffc266; /*노란색*/
}
#login{
    background-color: #8fc242; /*초록색*/

}
h4{
text-align: center;
}
</style>
</head>
<body>
	<jsp:include page="top_menu.jsp" />
	<div class="container mt-3">
		<h4>로그인</h4>
		${errorMessage}
			<form class="form-horizontal" method="post" action="${root}/user/login.do">
				<!-- ID -->
				<div class="form-group form-inline">
					<label class="col-sm-2 offset-4 control-label">아이디</label>
					<div class="mr-2">
						<input type="text" class="form-control" id="userid" name="userid" value="${savedId}">
					</div>
					<input type="checkbox" id="idsave" name="idsave" value="saveok" ${isCheck}>아이디 저장<br>
				</div>
			
				<!-- 비밀번호 -->
				<div class="form-group form-inline">
					<label class="col-sm-2 offset-4 control-label">비밀번호</label>
					<div class="mr-2">
						<input type="password" class="form-control" id="userpwd" name="userpwd">
					</div>
					<button type="submit" class="button btn" id = "login">로그인</button>
				</div>
			
				<div id="btns">
					<br>
			 		<a href="${root}/user/findpw.go"><input class="button btn pl-2 pr-2" id ="findpwd" type="button" value="비밀번호 찾기"></a>
			 		<a href="${root}/"><input class="button btn pl-2 pr-2" type="button" id = "gohome" value="돌아가기"></a>
			 	</div>
		 	</form>
		</div>
</body>
</html>

