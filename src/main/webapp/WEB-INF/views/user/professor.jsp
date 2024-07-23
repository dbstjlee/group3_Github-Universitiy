<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/myInfo.css">

	<!-- header 부분 -->
	
	<!-- 메인 메뉴 -->
	
	<!-- My 상세 메뉴 -->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>My</h2>
		</div>
	
	<!-- 좌측 메뉴 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
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
				<tr>
					<th>학번</th>
					<td>값</td>
					<th>소속</th>
					<td>값&nbsp;값</td>
				</tr>
				<tr>
					<th>학년</th>
					<td>값</td>
					<th>학기</th>
					<td>값</td>
				</tr>
				<tr>
					<th>입학일</th>
					<td>값</td>
					<th>졸업일(졸업예정일)</th>
					<td></td>
				</tr>
			</table>
			
			<table border="1" class="input--table">
				<colgroup>
					<col class="col1">
					<col class="col2">
					<col class="col3">
					<col class="col4">
				</colgroup>
				
				<tr>
					<th>성명</th>
					<td>값</td>
					<th>생년월일</th>
					<td>값</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>값</td>
					<th>주소</th>
					<td>값</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>값</td>
					<th>email</th>
					<td>값</td>
				</tr>
			</table>
			
			<button type="button" onclick="location.herf= '/update'" class="update--button">수정하기</button>
			
			<hr>
			
			<h4><span style="font-weight: 600;">학적 변동 내역</span></h4>
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

</body>
</html>