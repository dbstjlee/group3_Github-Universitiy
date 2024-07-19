<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴학 신청 상세 내역</title>
</head>
<body>
	<div>
		<div>
			<table border="1">
				<c:set var="breakApp" value="${breakApp}" />
				<h3>휴학 내역</h3>
				<tbody>
					<tr>
						<th>단과대</th>
						<td>${breakApp.collegeName}</td>
						<th>학과</th>
						<td>${breakApp.departmentName}</td>
					</tr>
					<tr>
						<th>학번</th>
						<td>${breakApp.studentId}</td>
						<th>학년</th>
						<td>${breakApp.studentGrade}</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>${breakApp.studentTel}</td>
						<th>성명</th>
						<td>${breakApp.studentName}</td>
					</tr>
					<tr>
						<th>주소</th>
						<td>${breakApp.studentAdds}</td>
					</tr>
					<tr>
						<th>기간</th>
						<td>${breakApp.fromYear}년${breakApp.fromSemester}학기 부터 ${breakApp.toYear}년 ${breakApp.toSemester}학기 까지</td>
					</tr>
					<tr>
						<th>휴학 구분</th>
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
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>