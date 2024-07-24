<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<h1>휴학 신청 내역</h1>
	<hr>
	<c:if test="${breakList.size() != 0}">
		<table border="1">
			<tr>
				<th>학과</th>
				<th>학번</th>
				<th>이름</th>
				<th>학년</th>
				<th>기간</th>
				<th>종류</th>
				<th>제출 일</th>
				<th>상태</th>
				<th></th>
			</tr>
			<c:forEach var="breakList" items="${breakList}">
				<tr>
					<td>${breakList.departmentName}</td>
					<td>${breakList.studentId}</td>
					<td>${breakList.studentName}</td>
					<td>${breakList.studentGrade}</td>
					<td>${breakList.fromYear}년도${breakList.fromSemester}학기 부터${breakList.toYear}년도${breakList.toSemester}학기 까지</td>
					<c:choose>
						<c:when test="${breakList.type eq 'nom'}">
							<td>일반 휴학</td>
						</c:when>
						<c:when test="${breakList.type eq 'fam'}">
							<td>임신·출산·육아휴학</td>
						</c:when>
						<c:when test="${breakList.type eq 'med'}">
							<td>질병 휴학</td>
						</c:when>
						<c:when test="${breakList.type eq 'bus'}">
							<td>창업 휴학</td>
						</c:when>
						<c:when test="${breakList.type eq 'mil'}">
							<td>군입대 휴학</td>
						</c:when>
					</c:choose>
					<td>${breakList.appDate}</td>
					<td>${breakList.status}</td>
					<td><form action="${pageContext.request.contextPath}/break/detail" target="popupWindow" onsubmit="window.open('', 'popupWindow', 'width=600,height=400,scrollbars=yes');">
							<input type="hidden" name="breakId" value="${breakList.id}">
							<button type="submit">조회</button>
						</form></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${breakList.size() == 0}">
		<p>휴학 신청 내역이 없습니다.</p>
	</c:if>
</body>
</html>
