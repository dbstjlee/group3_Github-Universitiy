<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴학 신청 상세 내역</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/breakDetail.css">
</head>
<body>
	<div>
		<h3>휴학 내역</h3>
		<form action="${pageContext.request.contextPath}/break/cancle" method="post">
			<table border="1">
				<c:set var="breakApp" value="${breakApp}" />
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
						<td colspan="3">${breakApp.studentAdds}</td>
					</tr>
					<tr>
						<th>기간</th>
						<td colspan="3">${breakApp.fromYear}년${breakApp.fromSemester}학기부터 ${breakApp.toYear}년 ${breakApp.toSemester}학기까지</td>
					</tr>
					<tr>
						<th>휴학 구분</th>
						<c:choose>
							<c:when test="${breakApp.type == '일반'}">
								<td colspan="3">일반 휴학</td>
							</c:when>
							<c:when test="${breakApp.type == '임신·출산·육아'}">
								<td colspan="3">임신·출산·육아휴학</td>
							</c:when>
							<c:when test="${breakApp.type == '질병'}">
								<td colspan="3">질병 휴학</td>
							</c:when>
							<c:when test="${breakApp.type == '창업'}">
								<td colspan="3">창업 휴학</td>
							</c:when>
							<c:when test="${breakApp.type == '군입대'}">
								<td colspan="3">군입대 휴학</td>
							</c:when>
						</c:choose>
					</tr>
				</tbody>
			</table>
			<c:if test="${breakApp.status == '처리중'}">
				<button type="submit">신청 취소</button>
			</c:if>
		</form>
	</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>