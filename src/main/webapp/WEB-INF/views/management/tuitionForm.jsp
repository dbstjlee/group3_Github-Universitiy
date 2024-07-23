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
			<h1>학생 명단 조회</h1>
			<div class="split--div"></div>
			<a href="/management/bill"><button type="submit" class="btn btn-primary create--tui">등록금 고지서 발송</button></a>
		</main>
	</div>
</body>
</html>