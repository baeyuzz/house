<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Happy House - 회원탈퇴</title>
<link rel='stylesheet' href='${root}/css/top_menu.css'>

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
#cancel{
    background-color: #8fc242; /*초록색*/

}
#delId{
    background-color: #ff2626; /*빨간색*/
}

</style>
</head>
<body>
	<jsp:include page="top_menu.jsp" />
	<div class="container mt-3" align="center">
		<h4>탈퇴 하시겠습니까?</h4>
		<div id = "answer">
			<a href="${pageContext.request.contextPath}/user/remove.do" class="btn button pr-2 pl-2" id = "delId" role="button">탈퇴</a>
			<a href="${pageContext.request.contextPath}/user/viewinfo.do" class="btn button pr-2 pl-2" id = "cancel" role="button">취소</a>
		</div>
	</div>
</body>
</html>