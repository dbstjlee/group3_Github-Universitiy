<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<ul>
		<li><a href="${pageContext.request.contextPath}/grade/thisSemester">이번 학기 성적 조회</a></li>
		<li><a href="${pageContext.request.contextPath}/grade/semester">학기별 성적 조회</a></li>
		<li><a href="${pageContext.request.contextPath}/grade/total">전체 성적 조회</a></li>
	</ul>
	<h1>이번학기 성적 조회</h1>
	<hr>
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
		<tr>
			<td>2023년</td>
			<td>1학기</td>
			<td>10001</td>
			<td>딥러닝의 기초</td>
			<td>전공</td>
			<td>3</td>
			<td>A+</td>
			<td><form action="${pageContext.request.contextPath}/grade/evaluation" target="popupWindow"
					onsubmit="window.open('', 'popupWindow', 'width=1000,height=1200,scrollbars=yes');">
					<input type="hidden" name="id" value="10001">
					<button type="submit">"Click"</button>
				</form></td>
		</tr>
	</table>
</body>
</html>