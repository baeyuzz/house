<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

<link rel='stylesheet' href='${root}/css/top_menu.css'>

<style type="text/css">
	.content-box {
		text-align: center;
	}
	.article-box {
		width: 700px;
		margin: auto;
	}
	#content-cell {
		max-height: 700px;
		overflow: scroll;
	}
</style>

</head>
<body>
	<jsp:include page="top_menu.jsp" />
	<section class="content-box">
		<h1>공지 사항 보기</h1>
		<div class="article-box">
			<table class="table table-borderless shadow-lg p-3 rounded">
				<tr>
					<td class="border border-dark">${notice.no}</td>
					<td class="border border-dark">${notice.title}</td>
					<td class="border border-dark">${notice.userid}</td>
				</tr>
				<tr class="border border-bottom-0 border-dark bg-white">
					<td colspan="2"></td>
					<td class="text-muted">조회수: ${notice.hitCount}</td>
				</tr>
				<tr class="bg-white">
					<td id="content-cell" class="border border-top-0 border-dark text-left" colspan="3">${notice.content}</td>
				</tr>
			</table>
			<div class="d-flex justify-content-end">
				<a class="btn btn-primary m-2"
				   href="${root}/notice/list.do"
				   title="목록으로">
					<i class="fas fa-clipboard-list"></i>
				</a>
				<c:if test="${isAdmin}">
				<a class="btn btn-warning m-2"
				   href="${root}/notice/notice_modify.go?no=${notice.no}&userid=${notice.userid}&hitCount=${notice.hitCount}&title=${notice.title}&content=${notice.content}"
				   title="수정하기">
					<i class="fas fa-tools"></i>
				</a>
				<a class="btn btn-danger m-2"
				   href="${root}/notice/delete.do?no=${notice.no}"
				   title="삭제하기">
					<i class="fas fa-trash-alt"></i>
				</a>
				</c:if>
			</div>
		</div>
	</section>
</body>
</html>