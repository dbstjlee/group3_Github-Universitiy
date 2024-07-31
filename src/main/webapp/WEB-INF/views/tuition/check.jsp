<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/student.css">
<body>
	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
		<div class="sub--menu">
			<div class="sub--menu--top">
				<h2>MY</h2>
			</div>

			<!-- 좌측 메뉴 -->
			<div class="sub--menu--mid">
				<table class="sub--menu--table" border="1">
					<tr>
						<td><a href="${pageContext.request.contextPath}/info/student">내 정보 조회</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/user/password">비밀번호 변경</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/break/application">휴학 신청</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/break/list">휴학 내역 조회</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/tuition/check"  class="selected--menu">등록금 내역 조회</a></td>
					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/tuition/payment">등록금 납부 고지서</a></td>
					</tr>
				</table>
			</div>
		</div>
		<main>
			<h1>등록금 내역 조회</h1>
			<div class="split--div"></div>
			<c:choose>
				<c:when test="${not empty checkTuitionList}">
					<table border="1" class="list--table">
						<thead>
							<tr>
								<th>등록연도</th>
								<th>등록학기</th>
								<th>장학금 유형</th>
								<th>등록금</th>
								<th>장학금</th>
								<th>납입금</th>
								<th>상태</th>
							</tr>
						</thead>
						<c:forEach var="checkTuitionList" items="${checkTuitionList}">
							<tbody>
								<tr>
									<td>${checkTuitionList.year}년</td>
									<td>${checkTuitionList.semester}학기</td>
									<c:choose>
										<c:when test="${checkTuitionList.scholarType eq 0}">
											<td>해당 없음</td>
										</c:when>
										<c:otherwise>
											<td>${checkTuitionList.scholarType}유형</td>
										</c:otherwise>
									</c:choose>
									<td><fmt:formatNumber value="${checkTuitionList.collAmount}" pattern="#,###" /></td>
									<td><fmt:formatNumber value="${checkTuitionList.scholarAmount}" pattern="#,###" /></td>
									<td><fmt:formatNumber value="${checkTuitionList.totalAmount}" pattern="#,###" /></td>
									<td><c:choose>
											<c:when test="${checkTuitionList.status == 1}">납부</c:when>
											<c:otherwise>미납</c:otherwise>
										</c:choose></td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<p class="no--list--p">등록금 납부 내역이 없습니다.</p>
				</c:otherwise>
			</c:choose>
		</main>
	</div>
	<%@ include file="/WEB-INF/views/layout/footer.jsp"%>