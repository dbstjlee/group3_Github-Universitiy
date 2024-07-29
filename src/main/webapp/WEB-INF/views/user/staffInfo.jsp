<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/myInfo.css">

<!-- header 부분 다시 확인!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->

<!-- My 상세 메뉴 -->
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>MY</h2>
		</div>

		<!-- 좌측 메뉴 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="/info/staff" class="selected--menu">내 정보 조회</a></td>
				</tr>
				<tr>
					<td><a href="/user/password">비밀번호 변경</a></td>
				</tr>
			</table>
		</div>
	</div>

	<!-- 정보 조회 메인 -->
	<main>
		<h1>내 정보 조회</h1>
		<div class="split--div"></div>
		<table border="1" class="input--table">
			<colgroup>
				<col class="col1">
				<col class="col2">
				<col class="col3">
				<col class="col4">
			</colgroup>
			<tbody>
				<tr>
					<th>ID</th>
					<td>${staffInfo.id}</td>
					<th>입사 날짜</th>
					<td>${staffInfo.hireDate}</td>
				</tr>
			</tbody>
		</table>

		<table border="1" class="input--table">
			<colgroup>
				<col class="col1">
				<col class="col2">
				<col class="col3">
				<col class="col4">
			</colgroup>
			<tbody>
				<tr>
					<th>성명</th>
					<td>${staffInfo.name}</td>
					<th>생년월일</th>
					<td>${staffInfo.birthDate}</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>${staffInfo.gender}</td>
					<th>주소</th>
					<td>${staffInfo.address}</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>${staffInfo.tel}</td>
					<th>email</th>
					<td>${staffInfo.email}</td>
				</tr>
			</tbody>
		</table>
		<button type="button" onclick="location.href='/info/update'"
			class="btn btn-dark update--button">수정하기</button>
		<hr>
	</main>
</div>
</body>
</html>