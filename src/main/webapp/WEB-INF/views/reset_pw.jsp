<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Happy House - 비밀번호 찾기</title>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style type="text/css">

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
#changepwd {
    background-color: #8fc242; /*초록색*/

}
h3, h4{
	text-align: center;
}


#gohome{
    background-color: #99adc2; /*약간 회색*/
}

div div{
text-align: center;
}

</style>

</head>
<body>
	<div class = "container">
		<c:choose>
			<c:when test="${infoUnmatch}">
				<h3>비밀번호 찾기 결과</h3>
				<h4>입력한 정보가 정확하지 않습니다</h4>
				<div>
			 		<a href="${root}/"><input type="button" value="처음으로" id = "gohome" class = "btn button"></a>
				</div>
			</c:when>	
			<c:when test="${pwUnmatch}">
				<h3>비밀번호 찾기 결과</h3>
				<h4>동일한 비밀번호를 입력해주세요</h4>
				<div>
					<input type="button" value="뒤로가기" onclick="history.back(-1);" id = "gohome" class = "btn button">
				</div>
			</c:when>	
			<c:when test="${noInfo}">
				<h3>비밀번호 찾기 결과</h3>
				<h4>일치하는 아이디를 찾을 수 없습니다</h4>
				<div>
				<a href="${root}/"><input type="button" value="처음으로" id = "gohome" class = "btn button"></a>
				</div>
			</c:when>			
			<c:otherwise>
				<h3>비밀번호 재설정</h3>
				<div class="center-block" align="center">
						<form method="post" action="${root}/user/resetpw.do" class="form-horizontal">
						
						<!-- pwd1 -->
						<div class="form-group form-inline d-flex justify-content-center">
							<label for="password1">새로운 비밀번호</label> 
							<input type="password" class="form-control ml-2" name="password1">
						</div>
						
						<!-- pwd2 -->
						<div class="form-group form-inline d-flex justify-content-center">
							<label for="password2" >비밀번호 확인</label> 
							<input type="password" class="form-control ml-2" name="password2">
						</div>
						
						<input type="hidden" name="userid" value="${userInfo.userid}">
						<input type="hidden" name="username" value="${userInfo.username}">
						<input type="hidden" name="address" value="${userInfo.address}">
						<input type="hidden" name="email" value="${userInfo.email}">
						
						<input class = "button btn" id = "changepwd" role ="button" type="submit" value="비밀번호 설정">
				 	</form>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>