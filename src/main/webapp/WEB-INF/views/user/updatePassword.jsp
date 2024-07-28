<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" href="/resources/css/admin.css">

<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>MY</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<c:choose>
					<c:when test="${principal.userRole == 'student'}">
						<tr>
							<td><a href="/info/student">내 정보 조회</a></td>
						</tr>
					</c:when>
					<c:when test="${principal.userRole == 'professor'}">
						<tr>
							<td><a href="/info/professor">내 정보 조회</a></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td><a href="/info/staff">내 정보 조회</a></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<tr>
					<td><a href="/user/password" class="selected--menu">비밀번호
							변경</a></td>
				</tr>
				<c:if test="${principal.userRole == 'student'}">
					<tr>
						<td><a href="/break/application">휴학 신청</a></td>
					</tr>
					<tr>
						<td><a href="/break/list">휴학 내역 조회</a></td>
					</tr>
					<tr>
						<td><a href="/tuition/list">등록금 내역 조회</a></td>
					</tr>
					<tr>
						<td><a href="/tuition/payment">등록금 납부 고지서</a></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>

	<!-- 메인 div -->
	<main>
		<h1>비밀번호 변경</h1>
		<div class="split--div"></div>
		<form action="/user/password" method="post" class="form--container">
			<input type="hidden" name="_method" value="put" />
			<table class="table--container">
				<tr>
					<td><label for="currentPassword">현재 비밀번호</label></td>
					<td><input type="password" name="currentPassword"
						class="input--box" id="currentPassword"></td>
				</tr>
				<tr>
					<td><label for="newPassword">변경할 비밀번호</label></td>
					<td><input type="password" name="newPassword"
						class="input--box" id="newPassword"></td>
				</tr>
				<tr>
					<td><label for="confirmPassword">변경할 비밀번호 확인</label></td>
					<td><input type="password" name="confirmPassword"
						class="input--box" id="confirmPassword"></td>
				</tr>
			</table>
			<br>
			<button type="submit" class="btn btn-dark update--button">수정하기</button>
		</form>
	</main>
</div>
</body>
</html>