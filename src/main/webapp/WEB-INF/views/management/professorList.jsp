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
@font-face {
  font-family: 'Material Symbols Outlined';
  font-style: normal;
  font-weight: 100 700;
  src: url(https://fonts.gstatic.com/s/materialsymbolsoutlined/v199/kJEhBvYX7BgnkSrUwT8OhrdQw4oELdPIeeII9v6oFsI.woff2) format('woff2');
}
.material-symbols-outlined {
    font-family: 'Material Symbols Outlined';
    font-weight: normal;
    font-style: normal;
    font-size: 24px;
    line-height: 1;
    letter-spacing: normal;
    text-transform: none;
    display: inline-block;
    white-space: nowrap;
    word-wrap: normal;
    direction: ltr;
    -webkit-font-feature-settings: 'liga';
    -webkit-font-smoothing: antialiased;
	font-variation-settings: 'FILL' 0, 'wght' 300, 'GRAD' 0, 'opsz' 48;
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
								<a href="/management/studentList">학생 명단 조회</a>
							</td>
						</tr>
						<tr>
							<td>
								<a href="/management/professorList" class="selected--menu">교수 명단 조회</a>
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
			<h1>교수 명단 조회</h1>
			<div class="split--div"></div>
			<div class="sub--filter">
				<form action="/management/professorList" method="get">
					<div>
						<!-- 개설연도 숫자 -->
						<label for="deptId">학과 번호</label> <input type="text" name="deptId" id="deptId"> <label for="professorId">사번</label> <input type="text" name="professorId" list="professorId">
						<!-- 검색 버튼 -->
						<button type="submit">
							<ul class="d-flex justify-content-center" style="margin: 0;">
								<li style="height: 24px; margin-right: 2px;">조회
								<li style="height: 24px;"><span class="material-symbols-outlined" style="font-size: 18px; padding-top: 4px;">search</span>
							</ul>
						</button>
						<!-- <button type="button" onclick="location.href='/user/student/update'" style="margin-left: 10px;">
							<ul>
								<li style="height: 24px;">새학기 적용
							</ul>
						</button> -->
					</div>
				</form>
			</div>
			<c:choose>
				<c:when test="${!professorList.isEmpty()}">
					<h4>
						<span style="font-weight: 600;">교수 목록</span>
					</h4>
					<table border="1" class="sub--list--table">
						<thead>
							<tr>
								<th>사번</th>
								<th>이름</th>
								<th>생년월일</th>
								<th>성별</th>
								<th>주소</th>
								<th>전화번호</th>
								<th>이메일</th>
								<th>학과번호</th>
								<th>고용일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="professor" items="${professorList}">
								<tr>
									<td>${professor.id}</td>
									<td>${professor.name}</td>
									<td>${professor.birthDate}</td>
									<td>${professor.gender}</td>
									<td>${professor.address}</td>
									<td>${professor.tel}</td>
									<td>${professor.email}</td>
									<td>${professor.deptId}</td>
									<td>${professor.hireDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</c:when>
				<c:otherwise>
					<p class="no--list--p">검색 결과가 없습니다.</p>
				</c:otherwise>
			</c:choose>
			<ul class="page--list">
				<c:forEach begin="1" end="${totalPages}" var="i">
					<c:choose>
						<c:when test="${i == currentPage}">
							<li><a href="/management/professorList?page=${i}" class="selected--page">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="/management/professorList?page=${i}">${i}</a></li>
						</c:otherwise>
					</c:choose>
			&nbsp;&nbsp;
		</c:forEach>
			</ul>
		</main>
	</div>
</body>
</html>