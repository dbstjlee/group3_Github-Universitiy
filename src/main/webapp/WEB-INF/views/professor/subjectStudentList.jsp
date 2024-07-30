<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" href="resources/css/subject.css">
<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<!-- 세부 메뉴 div-->
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>수업</h2>
		</div>
		<!-- 메뉴 -->
		<!-- 선택된 메뉴에 class="selected--menu" 추가해주세요 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td>
						<a href="/subject/allSubject">전체 강의 조회</a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="/professor/mySubject" class="selected--menu">내 강의 조회</a>
					</td>
				</tr>
				<c:if test="${principal.userRole.equals(\"professor\") }">
					<tr>
						<td>
							<a href="/professor/evaluation"> 내 강의 평가</a>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>
	<!-- 메인 div -->
	<main>
		<h1>[${subjectName}] 학생 리스트 조회</h1>
		<div class="split--div"></div>
		<c:choose>
			<c:when test="${studentList.isEmpty() == false}">
				<table border="1" class="sub--list--table">
					<thead>
						<tr>
							<th>학생 번호</th>
							<th>이름</th>
							<th>소속</th>
							<th>결석</th>
							<th>지각</th>
							<th>과제점수</th>
							<th>중간시험</th>
							<th>기말시험</th>
							<th>환산점수</th>
							<th>점수 기입</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${studentList}" var="student">
							<tr>
								<td>${student.studentId}</td>
								<td>${student.studentName}</td>
								<td>${student.deptName}</td>
								<td>${student.absent}</td>
								<td>${student.lateness}</td>
								<td>${student.homework}</td>
								<td>${student.midExam}</td>
								<td>${student.finalExam}</td>
								<td>${student.convertedMark}</td>
								<td>
									<a href="/professor/subject/student?id=${student.id}">기입</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p class="no--list--p">해당 강의를 수강하는 학생이 존재하지 않습니다.</p>
			</c:otherwise>
		</c:choose>


	</main>
</div>
</body>
</html>