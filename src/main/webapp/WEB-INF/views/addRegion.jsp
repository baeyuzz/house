<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Happy House - 관심지역 추가</title>

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

#add{
    background-color: #8fc242; /*초록색*/
	text-align: center;

}
#adddiv{
	text-align: center;
}

h3{
	text-align: center;
}

</style>
</head>
<body>
	<jsp:include page="top_menu.jsp" />

	<div class="container mt-3">
		<h3>관심지역 추가</h3>
		
		<form action="${root}/interest/add.do">
		
			<!-- 도/광역시 -->
			<div class="form-group form-inline">
				<label for="province" class="col-sm-3 offset-3 control-label">도/광역시</label>
				<div class="">
					<input type="text" class="form-control"	name="province">
				</div>
			</div>
		
			<!-- 시/군/구 -->
			<div class="form-group form-inline">
				<label for="city" class="col-sm-3 offset-3 control-label">시/군/구</label>
				<div class="">
					<input type="text" class="form-control"	name="city">
				</div>
			</div>
		
			<!-- 도/광역시 -->
			<div class="form-group form-inline">
				<label for="dong" class="col-sm-3 offset-3 control-label">읍/면/동</label>
				<div class="">
					<input type="text" class="form-control"	name="dong">
				</div>
			</div>
			
			<div class="form-group" id = "adddiv">
					<button type="submit" class="btn button pr-2 pl-2" id = "add">추가</button>
			</div>
	 	</form>
	</div>
</body>
</html>