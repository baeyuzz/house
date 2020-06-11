<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>어서오세요 공지사항</title>

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
	.table-box {
		width: 700px;
		margin: auto;
	}
	.sms-form-center {
		justify-content: center;
	}
	.table {
	margin-top: 20px;
	table-layout: fixed;
	}
	td {
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
</style>

</head>
<body>
	<jsp:include page="top_menu.jsp" />
	<section class="content-box">
		<h1>공지 게시판</h1>
		<form action="${root}/notice/search.do" method="post">
			<div class="form-inline sms-form-center">
				<label class="mr-sm-2" for="title">제목 검색</label>
				<input class="form-control mb-2 mr-sm-2" type="text" name="title" placeholder="검색할 단어를 입력하시오">
				<button class="btn btn-success mb-2 mr-sm-2" type="submit"> 검색 </button>
			</div>
		</form>
		<div class="table-box">
			<table class="table table-striped table-hover table-bordered">
				<thead class="bg-secondary">
					<tr>
						<th>번호</th>
						<th colspan="4">제목</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty noticeList}">
							<c:forEach items="${noticeList}" var="notice">
								<tr onclick="location.href='${root}/notice/detail.do?no=${notice.no}'">
									<td title="${notice.no}">${notice.no}</td>
									<td colspan="4" title="${notice.title}">${notice.title}</td>
									<td>${notice.hitCount}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<c:if test="${isAdmin}">
			<div class="d-flex justify-content-end">
				<a class="btn btn-info mb-2 mr-sm-2" href="${root}/notice/notice_write.go">
					<i class="fas fa-pencil-alt" title="글쓰기"></i>
				</a>
			</div>
			</c:if>
		</div>
		<!-- 시간 될 때 여기에 Paging 추가 -->
	</section>
</body>
</html>