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
	<h1>전체 누계 성적</h1>
	<hr>
	<c:choose>
		<c:when test="${grade != null}">
			<table border="1">
				<thead>
					<tr>
						<th>연도</th>
						<th>학기</th>
						<th>신청학점</th>
						<th>취득학점</th>
						<th>평점평균</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${grade.subYear}년</td>
						<td>${grade.semester}학기</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${grade.sumGrades}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${grade.myGrades}" /></td>
						<td>${grade.average}</td>
					</tr>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<p>조회할 성적이 없습니다.</p>
		</c:otherwise>
	</c:choose>
</body>
</html>