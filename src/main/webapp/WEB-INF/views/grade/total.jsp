<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
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
		<main>
			<h1>전체 누계 성적</h1>
			<div class="split--div"></div>
			<c:choose>
				<c:when test="${not empty gradeList}">
					<h4 style="font-weight: 600">학점 평균</h4>
					<c:forEach var="gradeList" items="${gradeList}">
					<table border="1" class="sub--list--table">
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
								<td>${gradeList.subYear}년</td>
								<td>${gradeList.semester}학기</td>
								<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${gradeList.sumGrades}" /></td>
								<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${gradeList.myGrades}" /></td>
								<td>${gradeList.average}</td>
							</tr>
						</tbody>
					</table>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p class="no--list--p">강의 신청 및 수강 이력 확인 바랍니다.</p>
				</c:otherwise>
			</c:choose>
		</main>
	</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>