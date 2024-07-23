<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/myInfo.css">
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

<!-- 개인 정보 수정  -->
<form action="${pageContext.request.contextPath}/user/update" method="get">
	<main>
		<h1>개인 정보 수정</h1>
		<div class="split--div"></div>
		<table class="update--table">
			<tbody>
				<tr>
					<td><label for="address">주소</label></td>
					<td><input type="text" name="address" id="address" class="input--box" value="">값</td>
				</tr>
				<tr>
					<td><label for="tel">전화번호</label></td>
					<td><input type="text" name="tel" id="tel" class="input--box" value="">값</td>
				</tr>
				<tr>
					<td><label for="email">이메일</label></td>
					<td><input type="text" name="email" id="email" class="input--box" value="">값</td>
				</tr>
				<tr>
					<td><label for="password">비밀번호 확인</label></td>
					<td><input type="password" name="password" id="password" class="input--box" value="">값</td>
				</tr>
			</tbody>
		</table>

		<br>

		<button type="submit" class="btn btn-dark update--button">수정하기</button>

	</main>
</form>

</body>
</html>