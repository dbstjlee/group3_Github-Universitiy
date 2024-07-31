<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/document.css">
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
						<td><a href="${pageContext.request.contextPath}/info/student" class="selected--menu">내 정보 조회</a></td>

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
						<td><a href="${pageContext.request.contextPath}/tuition/check">등록금 내역 조회</a></td>

					</tr>
					<tr>
						<td><a href="${pageContext.request.contextPath}/tuition/payment">등록금 납부 고지서</a></td>

					</tr>
				</table>
			</div>
		</div>
		<main>
			<h1>등록금 납부 페이지</h1>
			<div class="split--div"></div>
			<div class="d-flex flex-column align-items-center" style="min-width: 100%">
				<div class="document--layout">
					<h3>등록금 고지서</h3>
					<p>${paymentTuition.year}년도 ${paymentTuition.semester}학기</p>
					<table border="1">
						<thead class="tuition--payment--table">
							<tr>
								<th>단 과 대</th>
								<td>${paymentTuition.collgeName}</td>
								<th>학 과</th>
								<td>${paymentTuition.deptName}</td>
							</tr>
							<tr>
								<th>학번</th>
								<td>${paymentTuition.studentId}</td>
								<th>성명</th>
								<td>${paymentTuition.studentName}</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th colspan="2">장 학 유 형</th>
								<th colspan="2"><c:choose>
										<c:when test="${paymentTuition.scholarType eq 0}">
											해당 없음
										</c:when>
										<c:otherwise>
											${checkTuition.scholarType}
										</c:otherwise>
									</c:choose></th>
							</tr>
							<tr>
								<th colspan="2">등 록 금</th>
								<th colspan="2"><fmt:formatNumber value="${paymentTuition.collAmount}" pattern="#,###" /></th>
							</tr>
							<tr>
								<th colspan="2">장 학 금</th>
								<th colspan="2"><fmt:formatNumber value="${paymentTuition.scholarAmount}" pattern="#,###" /></th>
							</tr>
							<tr>
								<th colspan="2">납 부 금</th>
								<th colspan="2"><fmt:formatNumber value="${paymentTuition.totalAmount}" pattern="#,###" /></th>
							</tr>
							<!-- 고정된 값 ?? -->
							<tr>
								<th colspan="2">납 부 계 좌</th>
								<th colspan="2">그린은행 483-531345-536</th>
							</tr>
							<!-- 고정된 값 ?? -->
							<tr>
								<th colspan="2">납 부 기 간</th>
								<th colspan="2">~ 2023.05.02</th>
							</tr>
						</tbody>
					</table>
				</div>
				<c:choose>
					<c:when test="${paymentTuition.status == 0}">
						<form action="${pageContext.request.contextPath}/tuition/payment" method="post">
							<button type="submit" onclick="return confirm('등록금을 납부 하시겠습니까?');">등록금 납부</button>
						</form>
					</c:when>
					<c:otherwise>
						<p class="no--list--p">이미 납부 하셨습니다.</p>
					</c:otherwise>
				</c:choose>
			</div>
		</main>
	</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>