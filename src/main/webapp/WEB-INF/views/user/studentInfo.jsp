<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/myInfo.css">

<!-- My 상세 메뉴 -->
<form action="${pageContext.request.contextPath}/info/student" method="get">
	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
		<div class="sub--menu">
			<div class="sub--menu--top">
				<h2>MY</h2>
			</div>

			<!-- 좌측 메뉴 -->
			<div class="sub--menu--mid">
				<table class="sub--menu--table" border="1">
					<tbody>
						<tr>
							<td><a href="${pageContext.request.contextPath}/info/student" class="selected--menu">내 정보 조회</a></td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/password">비밀번호 변경</a></td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/break/application">휴학 신청</a></td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/break/list">휴학 내역 조회</a></td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/tuition/list">등록금 내역 조회</a></td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/tuition/payment">등록금 납부 고지서</a></td>
						</tr>
					</tbody>
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
						<th>학번</th>
						<td>${studentInfo.id}</td>
						<th>소속</th>
						<td>${studentInfo.collname}&nbsp;${studentInfo.deptname}</td>
					</tr>
					<tr>
						<th>학년</th>
						<td>${studentInfo.grade}</td>
						<th>학기</th>
						<td>${studentInfo.semester}</td>
					</tr>
					<tr>
						<th>입학일</th>
						<td>${studentInfo.entranceDate}</td>
						<th>졸업일(졸업예정일)</th>
						<td>${studentInfo.graduationDate}</td>
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
						<td>${studentInfo.name}</td>
						<th>생년월일</th>
						<td>${studentInfo.birthDate}</td>
					</tr>
					<tr>
						<th>성별</th>
						<td>${studentInfo.gender}</td>
						<th>주소</th>
						<td>${studentInfo.address}</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>${studentInfo.tel}</td>
						<th>email</th>
						<td>${studentInfo.email}</td>
					</tr>
				</tbody>
			</table>

			<button type="button" onclick="location.href='/info/update'" class="btn btn-dark update--button">수정하기</button>
			<hr>
			<h4>
				<span style="font-weight: 600;">학적 변동 내역</span>
			</h4>
			<table border="1" class="stat--table">
				<thead>
					<tr>
						<th>변동 일자</th>
						<th>변동 구분</th>
						<th>세부</th>
						<th>승인 여부</th>
						<th>복학 예정 연도/학기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td>재학</td>
						<td></td>
						<td>승인</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</main>
	</div>
</form>
</body>
</html>