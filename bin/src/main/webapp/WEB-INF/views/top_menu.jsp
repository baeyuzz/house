<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<script
	src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>

<link
	href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
<style>
#main-title:link {
	color: #3385ff;
	text-decoration: none;
}

#main-title:visited {
	color: #3385ff;
	text-decoration: none;
}

#main-title:hover {
	color: #3385ff;
	text-decoration: none;
}

#title-box>h1 {
	font-family: "Do Hyeon";
	text-align: center;
}

#user-box {
	text-align: right;
}

#title-box {
	align: center;
	padding: 10px;
}

#top-menu {
	margin: 0px;
	background-image:
		url(
"${root}/include/headerimg.jpg"
);
	background-position: center top;
	background-size: cover;
}

#user-box {
	height: 150px;
	font-size: 15px;
}

.sheader{
	padding : 10px;
	margin : 10px;
}

.main-menu {
	padding: 0px;
	text-align: center;
	margin-bottom: 0px;
}

</style>
<div id="top-menu" class="page-header">
	<div id="user-box">
		<c:choose>
			<c:when test="${not empty name}">
			<div class="sheader">
				<span id="welcome" class="text-white mr-2">${name}님 안녕하세요 </span>
				<a class="mr-2" href="${root}/user/viewinfo.do">
					<i class="fas fa-user"></i>
					회원정보
				</a>
				<a class="mr-2" href="${root}/user/logout.do">
					<i class="fas fa-sign-out-alt"></i>
					로그아웃
				</a>
			</div>
			</c:when>
			<c:otherwise>
			<div class="sheader">
				<a href="${root}/user/login.go"> <span
					class="  glyphicon glyphicon-log-in" aria-hidden="true"></span>
					로그인
				</a>
				<a href="${root}/user/join.go"> <span
					class="   glyphicon glyphicon-pencil" aria-hidden="true"></span>
					가입 하기
				</a>
			</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div id="title-box">
		<h1><a id="main-title" href="${root}/">
				Happy House
				<i class="fas fa-home"></i>
			</a></h1>
	</div>
</div>
<section class="main-menu">
	<nav class="navbar navbar-expand-sm bg-secondary navbar-dark mb-0">
	
		<button type="button" class="navbar-toggler"  data-toggle="collapse" 
			data-target="#navbarMenu">
   			<span class="navbar-toggler-icon"></span>
		</button>
	
		<div class="collapse navbar-collapse" id="navbarMenu">
			<ul class="navbar-nav">
				<li class="nav-item">
					<a class="nav-link" href="${root}/">홈</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${root}/notice/list.do">공지사항</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">QnA</a>
				</li>
				<c:if test="${not empty name}">
					<li class="nav-item">
						<a class="nav-link" href="${root}/interest/list.do">관심지역 보기</a>
					</li>
				</c:if>
			</ul>
		</div>
	</nav>
</section>

