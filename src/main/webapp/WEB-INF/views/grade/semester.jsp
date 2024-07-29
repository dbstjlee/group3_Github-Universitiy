<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<div class="d-flex justify-center align-items-start" style="min-width: 100em;">
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
			<h1>학기별 성적 조회</h1>
			<div class="split--div"></div>
			<c:choose>
				<c:when test="${not empty gradeList}">
					<div class="sub--fillter">
						<form action="${pageContext.request.contextPath}/grade/semester">
							<c:forEach items="${gradeList}">
								<select name="year">
									<option value="${gradeList.subYear}">${gradeList.subYear}년</option>
								</select>
								<select name="semester">
									<option value="${gradeList.semester}">${gradeList.semester}학기</option>
								</select>
							</c:forEach>
							<select name="type">
								<option value="all">전체</option>
								<option value="전공">전공</option>
								<option value="교양">교양</option>
							</select>
							<button type="submit">조회</button>
						</form>
					</div>
					<div>
						<h4>과목별 성적</h4>
						<table border="1" class="sub--list--table">
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
					</div>
				</c:when>
				<c:otherwise>
					<p class="no--list--p">강의 신청 및 수강 이력 확인 바랍니다.</p>
				</c:otherwise>
			</c:choose>
		</main>
	</div>
</body>
</html>