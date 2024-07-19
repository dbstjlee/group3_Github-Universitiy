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
	<h1>등록금 납부 페이지</h1>
	<div>
		<h3>등록금 고지서</h3>
		<p>2023년도 1학기</p>
		<table border="1">
			<thead>
				<tr>
					<th>단 과 대</th>
					<td>공과 대학</td>
					<th>학 과</th>
					<td>컴퓨터 공학과</td>
				</tr>
				<tr>
					<th>학번</th>
					<td>2023000003</td>
					<th>성명</th>
					<td>김지우</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th colspan="2">장 학 유 형</th>
					<th colspan="2">2 유 형</th>
				</tr>
				<tr>
					<th colspan="2">등 록 금</th>
					<th colspan="2">4,868,500</th>
				</tr>
				<tr>
					<th colspan="2">장 학 금</th>
					<th colspan="2">2,547,400</th>
				</tr>
				<tr>
					<th colspan="2">납 부 금</th>
					<th colspan="2">2,321,100</th>
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
		<%-- <c:choose>
			<!-- status 파악후 제출시 <p>태그로 알림 or <butten> 으로 제출 -->
			<c:when test=""></c:when>
		</c:choose> --%>
	</div>
</body>
</html>