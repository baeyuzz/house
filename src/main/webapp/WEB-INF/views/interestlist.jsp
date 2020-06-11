<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Happy House - 나의 관심지역</title>
<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style type="text/css">
#adddiv{
	text-align: right;
}
#page{
	text-align: center;
}
</style>
<script type="text/javascript">
	function page_ir_Move(pg) {
		location.href="${root}/interest/pageNav.do?pg=" + pg;
	}
	function page_env_Move(pg) {
		location.href="${root}/env/pageNav.do?pg=" + pg;
	}
	function page_shop_Move(pg) {
		location.href="${root}/shop/pageNav.do?pg=" + pg;
	}
</script>
</head>

<body>
	<jsp:include page="top_menu.jsp" />
	
	<div class="container mt-3">
		<!-- 관심지역 목록 -->
		<div class="interest-table">
			<div class="table-responsive">
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th colspan="4">관심지역 정보</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty irList}">
								<c:forEach var="region" items="${irList}">
									<tr>
										<td>${region.province}${region.city} ${region.dong}</td>
										<td>
											<a href="${root}/env/env.do?dong=${region.dong}">
											환경 정보 검색</a>
										</td>
										<td>
											<a href="${root}/shop/shop.do?city=${region.province}&gu=${region.city}&dong=${region.dong}">
											동네 업종 검색</a>
										</td>
										<td>
											<a href="${root}/interest/remove.do?no=${region.no}">
											관심지역에서 제거</a>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">등록된 관심지역이 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
				<nav>
					${irPaging.navigator}
				</nav>
			</div>
		</div>
		
		<!-- 관심지역 추가 -->
		<div id = "adddiv">
			<a class="btn btn-primary" href="${root}/interest/addRegion.go">관심지역 추가</a>
		</div>	
		
		<!-- 검색 리스트 -->
		<div class="information-table mt-3">
			<div class="table-responsive">
				<table class="table table-hover table-bordered">
					<c:if test="${not empty envList}">
						<thead>
							<tr>
								<th>사업체 이름</th>
								<th>환경 영향 구분 코드</th>
								<th>주소</th>
								<th>경도</th>
								<th>위도</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="env" items="${envList}">
								<tr class="table-row">
									<td>${env.name}</td>
									<td>${env.bizcode}</td>
									<td>${env.address}</td>
									<td>${env.lng}</td>
									<td>${env.lat}</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
					<c:if test="${not empty shopList}">
						<thead>
							<tr>
								<th>사업체 이름</th>
								<th >지점명</th>
								<th>분류1</th>
								<th>분류2</th>
								<th>분류3</th>
								<th>분류4</th>
								<th>주소</th>
								<th>경도</th>
								<th>위도</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="shop" items="${shopList}">
								<tr class="table-row">
									<td>${shop.shopName}</td>
									<td>${shop.localName}</td>
									<td>${shop.codename1}</td>
									<td>${shop.codename2}</td>
									<td>${shop.codename3}</td>
									<td>${shop.codename4}</td>
									<td>${shop.address}</td>
									<td>${shop.lng}</td>
									<td>${shop.lat}</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
				</table>
				<nav>
				<c:if test="${not empty envList}">
					${envPaging.navigator}
				</c:if>
				<c:if test="${not empty shopList}">
					${shopPaging.navigator}
				</c:if>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>
