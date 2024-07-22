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
	<h1>학기별 성적 조회</h1>
	<hr>
	<form action="${pageContext.request.contextPath}/grade/semester" method="post">
		<select name="year">
			<option value="2023">2023년</option>
			<option value="2024">2024년</option>
			<option value="2023">2025년</option>
		</select> <select name="semester">
			<option value="1">1학기</option>
			<option value="2">2학기</option>
		</select> <select name="type">
			<option value="all">전체</option>
			<option value="major">전공</option>
			<option value="culture">교양</option>
		</select>
		<button type="submit">조회</button>
	</form>

	<div>
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
			<tr>
				<td>2023년</td>
				<td>1학기</td>
				<td>10001</td>
				<td>딥러닝의 기초</td>
				<td>전공</td>
				<td>A+</td>
			</tr>
		</table>
	</div>
</body>
</html>