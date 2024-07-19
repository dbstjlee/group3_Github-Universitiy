<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>break 화면</title>
</head>
<body>
	<h2>휴학 신청 내역 리스트 화면</h2>
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
			<td>${breakList.fromYear}년도 ${breakList.fromSemester}학기 부터 ${breakList.toYear}년도 ${breakList.toSemester} 까지</td>
			<c:choose>
				<c:when test="${type == nomalBreak}">
					<td>일반 휴학</td>
				</c:when>
				<c:when test="${type == familyBreak}">
					<td>임신·출산·육아휴학</td>
				</c:when>
				<c:when test="${type == medicalBreak}">
					<td>질병 휴학</td>
				</c:when>
				<c:when test="${type == businessBreak}">
					<td>창업 휴학</td>
				</c:when>
				<c:when test="${type == militaryBreak}">
					<td>군입대 휴학</td>
				</c:when>
			</c:choose>
			<td>${breakList.appDate}</td>
			<td>${breakList.status}</td>
			<td>
			<a href="${pageContext.request.contextPath}/break/detail?id=${breakList.id}">조회</a>
			</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>
