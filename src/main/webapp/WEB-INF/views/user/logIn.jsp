<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/logIn.css">
</head>
<body>
	<div class="login-div">
		<div class="logo">
			<a></a>
		</div>
	<form action="${pageContext.request.contextPath}/user/logIn" method="post" class="main-container">
		<div class="login-container">
			<div class="id-container">
				<div class="login-id">
					<label for="userId"></label>
					<input type="text" name="id" id="userId" placeholder="아이디를 입력하세요." required>
					<div class="checkbox-id">
					<input type="checkbox" name="rememberId">ID 저장
					</div>
				</div>
			</div>
			<div class="pwd-container">
				<div class="login-pwd">
					<label for="password"></label>
					<input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요." required>
				</div>
			</div>
		</div>
		<div>
			<input type="submit" value="로그인" id="input-submit">
		</div>
		<ul class="login-info">
			<li><a href="${pageContext.request.contextPath}/user/findId" onclick="window.open(this.href,'_blank', 'width=500, height=300'); return false;">ID 찾기</a></li>
			<li><a href="${pageContext.request.contextPath}/user/findPwd" onclick="window.open(this.href, '_blank', 'width=500, height=350'); return false;">비밀번호 찾기</a></li>
		</ul>
	</form>
	</div>
	<div>
		<pre class="exam">학생 2023000001 교직원 230001 교수 23000001 비밀번호 123123</pre>
	</div>
</body>
</html>