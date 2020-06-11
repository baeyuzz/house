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
		
<link rel='stylesheet' href='${root}/css/top_menu.css'>

<style type="text/css">
	.content-box {
		text-align: center;
		width: 700px;
		margin: auto;
	}
	input, textarea {
		border: 0;
		width: 100%;
		height: 100%;
	}
	input: focus, textarea: focus {
		outline: none;
		border: 0;
	}
	textarea {
		resize: none;
	}
	#content-cell {
		height: 500px;
	}
</style>

</head>
<body>
	<jsp:include page="top_menu.jsp" />
	<section class="content-box">
		<h1>공지사항 수정</h1>
		<form method="post" action="${root}/notice/modify.do">
			<input type="hidden" name="no" value="${notice.no}">
			<table class="table table-borderless shadow-lg p-3 rounded">
				<tr>
					<td class="border border-dark">${notice.no}</td>
					<td class="border border-dark bg-white">
						<input type="text" name="title" value="${notice.title}">
					</td>
					<td class="border border-dark">${notice.userid}</td>
				</tr>
				<tr class="border border-bottom-0 border-dark bg-white">
					<td colspan="2"></td>
					<td class="text-muted">조회수: ${notice.hitCount}</td>
				</tr>
				<tr class="bg-white">
					<td id="content-cell" class="border border-top-0 border-dark text-left" colspan="3">
						<textarea name="content">${notice.content}</textarea>
					</td>
				</tr>
			</table>
			<button class="btn btn-primary" type="submit">수정</button>
		</form>
	</section>
</body>
</html>