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
			<h1>이번학기 성적 조회</h1>
			<div class="split--div"></div>
			<c:choose>
				<c:when test="${not empty gradeList}">
					<h4 style="font-weight: 600">과목별 성적</h4>
					<div>
						<table border="1" class="sub--list--table">
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
							<c:forEach var="grade" items="${gradeList}">
								<tr>
									<td>${grade.subYear}년</td>
									<td>${grade.semester}학기</td>
									<td>${grade.subjectId}</td>
									<td class="sub--list--name">${grade.subjectName}</td>
									<td>${grade.type}</td>
									<td>${grade.grades}</td>
									<td>${grade.grade}</td>
									<td><c:choose>
											<c:when test="${grade.evaluationId == 0}">
												<form action="${pageContext.request.contextPath}/evaluation" method="get" target="popupWindow"
													onsubmit="window.open('', 'popupWindow', 'width=1000,height=1200,scrollbars=yes');">
													<input type="hidden" name="subjectId" value="${grade.subjectId}">
													<button type="submit">Click</button>
												</form>
											</c:when>
											<c:otherwise>
												<p>작성완료</p>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</table>
						<p style="color: #888; margin-bottom: 40px;">※ 강의 평가 후 성적 조회 가능</p>
					</div>
					<hr>
					<br>
					<div>
						<h4 style="font-weight: 600">누계 성적</h4>
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
									<td>${thisGrade.subYear}년</td>
									<td>${thisGrade.semester}학기</td>
									<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${thisGrade.sumGrades}" /></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${thisGrade.myGrades}" /></td>
									<td>${thisGrade.average}</td>
								</tr>
							</tbody>
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