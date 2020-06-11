<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>상세 정보 페이지</title>
		
		<!-- BS4 포함 -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
		
		<link rel='stylesheet' href='${root}/css/top_menu.css'>
		
		<style type="text/css">
			.content-box {
				margin-top: 0px;
				text-align: center;
			}
			.image-box {
				width: 700px;
				margin: auto;
				margin-top: 10px;
			}
			.image-box img {
				width: 700px;
				height: 400px;
			}
			.info-box {
				width: 700px;
				margin: auto;
				margin-bottom: 20px;
			}
		</style>
	</head>
	<body>
		<jsp:include page="top_menu.jsp" />
		<div class="content-box">
			<div class="image-box">
				<img class="img-thumbnail" alt="${house.img}" src="${root}/img/${house.img}">
			</div>
			<div class="info-box table-responsive">
				<table class="table">
					<thead>
						<tr>
							<td colspan="6"><h3><i class="fas fa-building"></i>건물 정보</h3></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>동</th>
							<th>아파트 이름</th>
							<th>건축 년도</th>
							<th>면적</th>
							<th>층</th>

							<c:if test="${house.type > 2}">
							<th>임대료</th>
							</c:if>
						</tr>
						<tr>
							<td>${house.dong}</td>
							<td>${house.aptName}</td>
							<td>${house.buildYear}</td>
							<td>${house.area}</td>
							<td>${house.floor}</td>

							<c:if test="${house.type > 2}">
							<td>${house.rentMoney}</td>
							</c:if>
					</tbody>
				</table>
				<table class="table">
					<thead>
						<tr>
							<td colspan="6"><h3><i class="fas fa-barcode"></i>거래 정보</h3></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>거래 유형</th>
							<th>거래 금액</th>
							<th>거래 날짜</th>
						</tr>
						<tr>
							<td>${house.typeString}</td>
							<td>${house.dealAmount}</td>
							<td>${house.dealYear}.${house.dealMonth}.${house.dealDay}</td>
						</tr>
					</tbody>
				</table>
				<a class="btn btn-info" href="${root}/">메인으로 돌아가기</a>
			</div>
		</div>
	</body>
</html>