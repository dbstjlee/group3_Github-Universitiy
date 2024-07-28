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
					<td><a href="${pageContext.request.contextPath}/grade/thisSemester">이번 학기 성적 조회</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/grade/semester">학기별 성적 조회</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/grade/total">전체 성적 조회</a></td>

				</tr>
			</table>
		</div>
	</div>
	<h1>이번학기 성적 조회</h1>
	<hr>
	<c:choose>
		<c:when test="${not empty gradeList}">
			<h3>과목별 성적</h3>
			<table border="1">
				<tr>
					<th>연도</th>
					<th>학기</th>
					<th>과목번호</th>
					<th>과목명</th>
					<th>강의구분</th>
					<th>이수학점</th>
					<th>성적</th>
					<th>강의평가</th>
				</tr>
				<c:forEach var="grade" items="${gradeList}">
					<tr>
						<td>${grade.subYear}년</td>
						<td>${grade.semester}학기</td>
						<td>${grade.subjectId}</td>
						<td>${grade.subjectName}</td>
						<td>${grade.type}</td>
						<td>${grade.grades}</td>
						<td>${grade.grade}</td>
						<td>
							<form action="${pageContext.request.contextPath}/evaluation" method="get" target="popupWindow" onsubmit="window.open('', 'popupWindow', 'width=1000,height=1200,scrollbars=yes');">
								<input type="hidden" name="subjectId" value="${grade.subjectId}">
								<button type="submit">Click</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p>조회할 성적이 없습니다.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>