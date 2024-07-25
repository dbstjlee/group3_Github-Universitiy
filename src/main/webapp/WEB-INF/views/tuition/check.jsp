<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
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
					<td><a href="${pageContext.request.contextPath}/tuition/check">등록금 내역 조회</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/tuition/payment">등록금 납부 고지서</a></td>

				</tr>
			</table>
		</div>
	</div>
	<h1>등록금 내역 조회</h1>
	<hr>
	<c:choose>
		<c:when test="${not empty tuition}">
			<table border="1">
				<tr>
					<th>등록연도</th>
					<th>등록학기</th>
					<th>장학금 유형</th>
					<th>등록금</th>
					<th>장학금</th>
					<th>납입금</th>
					<th>제출 내역</th>
				</tr>
				<!-- 등록금 샘플 데이터 입력 후 출력 받기 -->
				<tr>
					<td>${tuition.year}년</td>
					<td>${tuition.semester}학기</td>
					<c:choose>
						<c:when test="${tuition.scholarType == 0}">
							<td>해당 없음</td>
						</c:when>
						<c:otherwise>
							<td>${tuition.scholarType}유형</td>
						</c:otherwise>
					</c:choose>
					<td><fmt:formatNumber value="${tuition.collAmount}" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${tuition.scholarAmount}" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${tuition.totalAmount}" pattern="#,###" /></td>
					<td><c:choose>
							<c:when test="${tuition.status == 0}">
								<p>납부 전</p>
							</c:when>
							<c:otherwise>
								<p>납부 완료</p>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<p>등록금 납부 내역이 없습니다.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>