<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Noto Sans KR', sans-serif;
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

.selected--menu {
	color: blue;
}

main {
	min-width: 1150px;
	padding: 20px;
	margin-bottom: 50px;
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
								<a href="/management/staff" class="selected--menu">직원 등록</a>
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
			<h1>직원 등록</h1>
			<div class="split--div"></div>
			<form action="/management/staff" method="post">
				<table class="table--container">
					<tr>
						<td>
							<label for="name">이름</label>
						</td>
						<td>
							<input type="text" name="name" id="name" class="input--box">
						</td>
					</tr>
					<tr>
						<td>
							<label for="birthDate">생년월일</label>
						</td>
						<td>
							<input type="date" name="birthDate" id="birthDate" class="input--box">
						</td>
					</tr>
					<tr>
						<td style="padding-top: 7px">
							<label>성별</label>
						</td>
						<td style="padding-top: 7px">
							<label for="male">남성</label> <input type="radio" value="남성" name="gender" id="male" checked="checked"> &nbsp; <label for="female">여성</label> <input type="radio"
								value="여성" name="gender" id="female"
							>
						</td>
					</tr>
					<tr>
						<td>
							<label for="address">주소</label>
						</td>
						<td>
							<input type="text" name="address" id="address" class="input--box">
						</td>
					</tr>
					<tr>
						<td>
							<label for="tel">전화번호</label>
						</td>
						<td>
							<input type="text" name="tel" id="tel" class="input--box">
						</td>
					</tr>
					<tr>
						<td>
							<label for="email">이메일</label>
						</td>
						<td>
							<input type="text" name="email" id="email" class="input--box">
						</td>
					</tr>
				</table>
				<div class="button--container">
					<input type="submit" value="입력">
				</div>
			</form>
		</main>
</body>
</html>