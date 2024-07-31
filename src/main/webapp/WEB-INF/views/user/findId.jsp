<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
* {
	margin: 0 auto;
	padding: 0 auto;
	font-family: 'Noto Sans KR', sans-serif;
}

.header--top {
	width: 100%;
	height: 40px;
	background-color: #d0d7de;
}

.section--header {
	text-align: center;
	margin-top: 20px;
}

.search--table td {
	padding: 4px;
	text-align: left;
	
}

.col1 {
	width: 20%;
}

.col2 {
	width: 80%;
}

.submit--button {
	margin-top: 20px;
	margin-left: 200px;
	padding: 10px 15px;
	border: none;
	border-radius: 10px;
	color: white;
	background-color: #00b918;
	cursor: pointer; 
}
</style>
</head>
<body>
	<header>
		<div class="header--top"></div>
	</header>
	<section>
		<div class="section--header">
			<h2>아이디 찾기</h2>
			<br>
		</div>
		<form action="/user/findId" method="post">
		<table class="search--table">
			<colgroup>
				<col class="col1">
				<col class="col2">
			</colgroup>
			<tbody>
				<tr>
					<td>
						<label for="name">이름</label>
					</td>
					<td>
						<input type="text" name="name" id="name">
					</td>
				</tr>
				<tr>
					<td>
						<label for="email">이메일</label>
					</td>
					<td>
						<input type="email" name="email" id="email">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<label for="student">학생</label>
						<input type="radio" name="userRole" value="student" id="student">
						
						<label for="professor">교수</label>
						<input type="radio" name="userRole" value="professor" id="professor">
						
						<label for="staff">직원</label>
						<input type="radio" name="userRole" value="staff" id="staff">
					</td>
				</tr>
			</tbody>
		</table>
			<div class="button--container">
				<button type="submit" class="submit--button">아이디 찾기</button>
			</div>
		
		</form>
	</section>
