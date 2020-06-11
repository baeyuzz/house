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

<style>

h4{
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
    margin: 4px;
    cursor: pointer;
}
#findpw{
    background-color: #8fc242; /*초록색*/

}


</style>
</head>
<body>
	<div class="container">
	
		<h4>비밀번호 찾기</h4>

		<div class="center-block" align="center">
			<form method="post" action="${root}/user/findpw.do">
				<!-- 이름 -->
				<div class="form-group">
					<label class="col-sm-4 control-label" >이름</label> 
					<div class="col-sm-4">
						<input type="text" class="form-control" name="username">
					</div>
				</div>
				
				<!-- 아이디 -->
				<div class="form-group">
					<label  class="col-sm-4 control-label">아이디</label> 
					<div class="col-sm-4">
						<input type="text" class="form-control" name="userid">
					</div>
				</div>
				
				<!-- 전화번호 -->
				<div class="form-group">
					<label  class="col-sm-4 control-label">이메일</label> 
					<div class="col-sm-4">
						<input type="text" class="form-control" name="email">
					</div>
				</div>
			
				<div class="form-group">
					<input class="btn button pr-2 pl-2" id = "findpw" role="button" type="submit" value="비밀번호 찾기">
				</div>
			
			</form>
		</div>
		
	</div>
</body>
</html>