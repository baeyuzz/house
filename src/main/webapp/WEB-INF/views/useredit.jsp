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
	href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap"
	rel="stylesheet">

<meta charset="UTF-8">
<title>My Page</title>

<style>
#mypage {
	text-align: center;
}

.form-group div input, .form-group label{
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


#edit{
    background-color: #ffc266; /*노란색*/
}

#userid {
	font-size: 18px;
}
#iddiv{
	text-align: left;
}

</style>
</head>


<body>

	<div class="container">
		<div id="mypage" class="mt-3 mb-3">
			<h3 id="title">회원 정보 수정</h3>

			<form method="post" action="${root}/user/edit.do">

				<!-- id -->
				<div class="form-group form-inline">
					<label for="userid" class="col-sm-2 offset-4 control-label">아이디</label>
					<div class="" id = "iddiv">
						<span id = "userid">${user.userid}</span>
					</div>
				</div>
				<!-- pwd -->
				<div class="form-group form-inline">
					<label for="userpwd" class="col-sm-2 offset-4 control-label">비밀번호</label>
					<div class="">
						<input type="password" class="form-control" value="${user.userpwd}"
							name="userpwd">
					</div>
				</div>
				<!-- name -->
				<div class="form-group form-inline">
					<label for="username" class="col-sm-2 offset-4 control-label">이름</label>
					<div class="">
						<input type="text" class="form-control" value="${user.username}"
							name="username">
					</div>
				</div>
				<!-- address -->
				<div class="form-group form-inline">
					<label for="address" class="col-sm-2 offset-4 control-label">주소</label>
					<div class="">
						<input type="text" class="form-control" value="${user.address}"
							name="address">
					</div>
				</div>
				<!-- tel -->
				<div class="form-group form-inline">
					<label for="email" class="col-sm-2 offset-4 control-label">이메일</label>
					<div class="">
						<input type="text" class="form-control" value="${user.email}"
							name="email">
					</div>
				</div>
				
				<div class="form-group">
					<div class="">
						<button type="submit" class="btn button pl-2 pr-2" id = "edit">수정하기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
