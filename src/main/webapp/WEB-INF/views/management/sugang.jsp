<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<style>
#btn--danger {
	background-color: #dc3545;
	border-color: #dc3545;
}
</style>
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사관리</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td>
							<a href="/management/studentList">학생 명단 조회</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/professorList">교수 명단 조회</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/student">학생 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/professor">교수 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/staff">직원 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/new-semester">새학기 적용</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/break">휴학 처리</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/sugang" class="selected--menu">수강 신청 기간 설정</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/tuition">등록금 고지서 발송</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>수강 신청 기간 설정</h1>
		<div class="split--div"></div>
		<c:choose>
			<c:when test="${sugang == 1}">
				<a href="/management/sugang?state=2"><button type="submit" class="btn btn-primary" id="btn--danger">수강 신청 기간 종료</button></a>
				<p class="no--list--p">수강 신청 기간이 지나면 수강 신청이 불가능합니다. 신중히 눌러주세요!!</p>
			</c:when>
			<c:when test="${sugang == 2}">
				<p class="no--list--p">이번 학기 수강 신청 기간이 종료되었습니다.</p>
			</c:when>
			<c:when test="${sugang == 3}">
				<a href="/management/sugang?state=1"><button type="submit" class="btn btn-primary">수강 신청 기간 시작</button></a>
			</c:when>
			<c:otherwise>
				<a href="/management/sugang?state=3"><button type="submit" class="btn btn-primary">예비 수강 신청 기간 시작</button></a>
			</c:otherwise>
		</c:choose>
	</main>
</div>
</body>
</html>