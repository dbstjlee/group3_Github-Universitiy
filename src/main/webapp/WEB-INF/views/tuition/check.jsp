<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<ul>
		<li><a href="${pageContext.request.contextPath}/break/application">휴학 신청</a></li>
		<li><a href="${pageContext.request.contextPath}/break/list">휴학 신청 내역</a></li>
		<li><a href="${pageContext.request.contextPath}/tuition/check">등록금 내역 확인</a></li>
		<li><a href="${pageContext.request.contextPath}/tuition/payment">등록금 고지서</a></li>
	</ul>
	<h1>등록금 내역 조회</h1>
	<hr>
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
			<td>${tuition.scholarType}유형</td>
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
</body>
</html>