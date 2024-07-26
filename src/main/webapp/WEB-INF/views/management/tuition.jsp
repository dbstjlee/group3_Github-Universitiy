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
							<a href="/management/tuition" class="selected--menu">등록금 고지서 발송</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/break">휴학 처리</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/sugang">수강 신청 기간 설정</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>등록금 고지서 발송</h1>
		<div class="split--div"></div>
		<c:choose>
			<c:when test="${tuition == 1}">
				<a href="/management/billEnd"><button type="submit" class="btn btn-primary" id="btn--danger">등록금 납부 기한 종료</button></a>
				<p class="no--list--p">등록금을 납부하지 않은 학생은 자동으로 제적 처리됩니다. 신중히 눌러주세요!!</p>
				<p class="no--list--p">등록금을 납부하지 않은 학생은 자동으로 제적 처리됩니다. 신중히 눌러주세요!!</p>
			</c:when>
			<c:when test="${tuition == 2}">
				<p class="no--list--p">이번 학기 등록금 납부 기간이 종료되었습니다.</p>
			</c:when>
			<c:otherwise>
				<a href="/management/bill"><button type="submit" class="btn btn-primary">등록금 고지서 발송</button></a>
			</c:otherwise>
		</c:choose>
	</main>
</div>
</body>
</html>