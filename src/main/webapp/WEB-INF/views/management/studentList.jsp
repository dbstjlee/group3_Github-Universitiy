<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>임시 타이틀</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Noto Sans KR', sans-serif;
}

.page--list {
	display: flex;
	justify-content: center;
}

ul {
	list-style-type: disc;
	margin-block-start: 1em;
	margin-block-end: 1em;
	margin-inline-start: 0px;
	margin-inline-end: 0px;
	padding-inline-start: 40px;
	unicode-bidi: isolate;
	margin-top: 0;
	margin-bottom: 1rem;
}

li {
	list-style: none;
}

a {
	text-decoration: none;
	color: black;
	font-size: 20px;
}

.selected--page {
	color: blue;
}

.selected--menu {
	color: blue;
}

main {
	min-width: 1150px;
	padding: 20px;
	margin-bottom: 50px;
}
.sub--filter form {
    display: flex;
}
</style>
</head>
<body>
	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
		<div class="sub--menu">
			<div class="sub--menu--top">
				<h2>학사 관리</h2>
			</div>
			<div class="sub--menu--mid">
				<table class="sub--menu--table" border="1">
					<tbody>
						<tr>
							<td>
								<a href="/management/studentList" class="selected--menu">학생 명단 조회</a>
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
								<a href="/management/tuition">등록금 고지서 발송</a>
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
			<h1>학생 명단 조회</h1>
			<div class="split--div"></div>
			<div class="sub--filter">
				<form action="/management/studentList" method="get">
					<div>
						<!-- 개설연도 숫자 -->
						<label for="deptId">학과 번호</label> <input type="text" name="deptId" id="deptId"> <label for="studentId">학번</label> <input type="text" name="studentId" list="studentId">
						<!-- 검색 버튼 -->
						<button type="submit">
							<ul class="d-flex justify-content-center" style="margin: 0;">
								<li style="height: 24px; margin-right: 2px;">조회
								<li style="height: 24px;"><span class="material-symbols-outlined" style="font-size: 18px; padding-top: 4px;">search</span>
							</ul>
						</button>
						<button type="button" onclick="location.href='/user/student/update'" style="margin-left: 10px;">
							<ul>
								<li style="height: 24px;">새학기 적용
							</ul>
						</button>
					</div>
				</form>
			</div>
			<ul class="page--list">
				<c:forEach begin="1" end="${totalPages}" var="i">
					<c:choose>
						<c:when test="${i == currentPage}">
							<li><a href="/management/studentList?page=${i}" class="selected--page">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="/management/studentList?page=${i}">${i}</a></li>
						</c:otherwise>
					</c:choose>
			&nbsp;&nbsp;
		</c:forEach>
			</ul>
		</main>
	</div>
</body>
</html>