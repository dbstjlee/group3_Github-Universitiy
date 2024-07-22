<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

.submit--button {
	margin: 20px 50px;
	text-align: 200px;
}
.section--content {
	margin: 20px 50px;
	text-align: center;
}
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath}/user/findSuccess" method="get">
		<header>
			<div class="header--top"></div>
		</header>
		<section>
			<div class="section--header">
				<h2>아이디 찾기</h2>
				<br>
			</div>
			
			<c:choose>
				<c:when test="${student}">
					<div class="section--content">
					${student.name}의 아이디는
					<br>
					<span style="font-weight: bold;">${student.id}</span>입니다.
					</div>
				</c:when>
				
				<c:when test="${professor}">
					<div class="section--content">
					${professor.name}의 아이디는
					<br>
					<span style="font-weight: bold;">${professor.id}</span>입니다.
					</div>
				</c:when>
				
				<c:otherwise>
					<div class="section--content">
					${staff.name}의 아이디는
					<br>
					<span style="font-weight: bold;">${staff.id}</span>입니다.
					</div>
				</c:otherwise>
			</c:choose>
		</section>
	</form>
</body>
</html>