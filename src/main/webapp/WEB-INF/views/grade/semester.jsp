<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<ul>
		<li><a href="${pageContext.request.contextPath}/grade/thisSemester">이번 학기 성적 조회</a></li>
		<li><a href="${pageContext.request.contextPath}/grade/semester">학기별 성적 조회</a></li>
		<li><a href="${pageContext.request.contextPath}/grade/total">전체 성적 조회</a></li>
	</ul>
	<h1>학기별 성적 조회</h1>
	<hr>
	<form action="${pageContext.request.contextPath}/grade/semester">
		<select name="year">
			<option value="2023">2023년</option>
			<option value="2024">2024년</option>
			<option value="2025">2025년</option>
		</select> <select name="semester">
			<option value="1">1학기</option>
			<option value="2">2학기</option>
		</select> <select name="type">
			<option value="all">전체</option>
			<option value="전공">전공</option>
			<option value="교양">교양</option>
		</select>
		<button type="submit">조회</button>
	</form>

	<div>
		<c:choose>
			<c:when test="${not empty gradeList}">
				<h2>과목별 성적</h2>
				<table border="1">
					<tr>
						<th>연도</th>
						<th>학기</th>
						<th>과목번호</th>
						<th>과목명</th>
						<th>강의구분</th>
						<th>학점</th>
					</tr>
					<c:forEach var="grade" items="${gradeList}">
						<tr>
							<td>${grade.subYear}년</td>
							<td>${grade.semester}학기</td>
							<td>${grade.subjectId}</td>
							<td>${grade.subjectName}</td>
							<td>${grade.type}</td>
							<td>${grade.grade}</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p>조회할 성적이 없습니다.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>