<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	background-color: #031734;
}

.section--header {
	text-align: center;
}

.search--table {
	width: 400px;
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

.section--content {
	margin: 20px 50px;
	text-align: center;
}
</style>
</head>
<body>
	<header>
		<div class="header--top"></div>
	</header>
	<section>
		<div class="section--header">
			<h2>비밀번호 찾기</h2>
			<br>
		</div>
			<div class="section--content">
				${username}의 임시 비밀번호는
				 <br>
				 <span style="font-weight: bold;">${password}</span>입니다.
				 <br>
				  보안을 위해 로그인 후 비밀번호를 변경해주세요.
			</div>
	</section>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>