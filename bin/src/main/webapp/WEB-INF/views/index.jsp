<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>어서오세요 Happy House</title>

<!-- BS4 포함 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<!-- CSS 파일 포함 -->
<link rel='stylesheet' href='${root}/include/index.css'>

<script>
	function pageMove(pg) { 
		location.href="${root}/house/pageNav.do?pg=" + pg;
	}
	
	function clickSort(no) {
		location.href="${root}/house/sort.do?by=" + no;
	}
</script>
</head>
<body>
	<jsp:include page="top_menu.jsp" />
	
	<section class="search">
		<div class="search-form">
			<form action="${root}/house/search.do" method="post">
				<div class="form-box">
					<div class="form-group">
						<div class="form-check-inline">
							<label class="form-check-label">
								아파트 매매
								<input type="checkbox" class="form-check-input" name="aptdeal" value="ok" ${adck} />
							</label> 
						</div>
						<div class="form-check-inline">
							<label class="form-check-label">
								연립,다세대 매매
								<input type="checkbox" class="form-check-input" name="housedeal" value="ok" ${hdck} />
							</label>
						</div>
						<div class="form-check-inline">
							<label class="form-check-label">
								아파트 전월세
								<input type="checkbox" class="form-check-input" name="aptrent" value="ok" ${arck} />
							</label> 
						</div>
						<div class="form-check-inline">
							<label class="form-check-label">
								연립,다세대 전월세
								<input type="checkbox" class="form-check-input" name="houserent" value="ok" ${hrck} />
							</label> 
						</div>					
					</div>
					<div class="form-group form-inline sms-form-center">
						<label for="dong" class="mr-sm-2">동 이름:</label>
						<input type="text" class="form-control mr-sm-2" 
							name="dong" value="${donginp}" placeholder="동 이름을 입력해주세요"/>
						<label for="aptname" class="mr-sm-2">아파트 이름:</label>
						<input type="text" class="form-control mr-sm-2" 
							name="aptname" value="${aptinp}" placeholder="아파트 이름을 입력해주세요"/>
						<button type="submit" class="btn btn-primary">검색</button>
					</div>
				</div>
			</form>
		</div>
	</section>
	<section class="information">
		<table class="table table-hover table-bordered">
			<thead>
				<tr id="head-row">
					<th onclick="clickSort('no');" title="번호">
					<i class="fas fa-angle-down"></i>번호</th>
					<th onclick="clickSort('dong');" title="동 이름">
					<i class="fas fa-angle-down"></i>동 이름</th>
					<th onclick="clickSort('AptName');" title="아파트 이름">
					<i class="fas fa-angle-down"></i>아파트 이름</th>
					<th onclick="clickSort('buildYear');" title="건축 년도">
					<i class="fas fa-angle-down"></i>건축 년도</th>
					<th onclick="clickSort('dealAmount');" title="거래 금액">
					<i class="fas fa-angle-down"></i>거래 금액</th>
					<th onclick="clickSort('dealYear');" title="거래 년도">
					<i class="fas fa-angle-down"></i>거래 년도</th>
					<th onclick="clickSort('dealMonth');" title="거래 월">
					<i class="fas fa-angle-down"></i>거래 월</th>
					<th onclick="clickSort('area');" title="면적">
					<i class="fas fa-angle-down"></i>면적</th>
					<th onclick="clickSort('floor');" title="층수">
					<i class="fas fa-angle-down"></i>층수</th>
					<th onclick="clickSort('type');" title="거래 유형">
					<i class="fas fa-angle-down"></i>거래 유형</th>
					<th onclick="clickSort('rentMoney');" title="월세">
					<i class="fas fa-angle-down"></i>월세</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="deal" items="${dealList}">
					<tr onclick="location.href='${root}/house/detail.do?no=${deal.no}'">
						<td title="${deal.no}">${deal.no}</td>
						<td title="${deal.dong}">${deal.dong}</td>
						<td title="${deal.aptName}">${deal.aptName}</td>
						<td title="${deal.buildYear}">${deal.buildYear}</td>
						<td title="${deal.dealAmount}">${deal.dealAmount}</td>
						<td title="${deal.dealYear}">${deal.dealYear}</td>
						<td title="${deal.dealMonth}">${deal.dealMonth}</td>
						<td title="${deal.area}">${deal.area}</td>
						<td title="${deal.floor}">${deal.floor}</td>
						<td title="${deal.typeString}">${deal.typeString}</td>
						<td title="${deal.rentMoney}">${deal.rentMoney}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- 페이징 처리 부분 -->
		<nav>
			<!-- page 출력부분 -->
	  		${navigation.navigator}
        </nav>		
	</section>
</body>
</html>