<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="/resources/css/logIn.css">
</head>
<body onload="showClock()">
	<div class="login--div">
			<div class="logo--section">
				<a href="${pageContext.request.contextPath}/"><img class="logo" src="${pageContext.request.contextPath}/resources/images/logo.gif" ></a>
				</div>
				<div id="divClock" class="clock">
 				</div>
		
		<div class="logo">
			<a></a>
		</div>
	<form action="${pageContext.request.contextPath}/user/logIn" method="post" class="main--container">
		<div class="login--container">
			<div class="id--container">
				<div class="login--id">
					<label for="userId"></label>
					<input type="text" name="id" id="userId" placeholder="아이디를 입력하세요." required value="${cookie.id.value}">
					<div class="checkbox--id">
					<c:choose>
					<c:when test="${cookie.id != null}">
					<input type="checkbox" name="rememberId" checked >ID 저장
					</c:when>
					<c:otherwise>
					<input type="checkbox" name="rememberId" >ID 저장
					</c:otherwise>
					</c:choose>
					
					</div>
				</div>
			</div>
			<div class="pwd--container">
				<div class="login--pwd">
					<label for="password"></label>
					<input type="password" name="password" id="password" placeholder="비밀번호를 입력하세요." required>
				</div>
			</div>
		</div>
		<div>
			<input type="submit" value="로그인" id="input--submit">
		</div>
		<ul class="login--info">
			<li><a href="${pageContext.request.contextPath}/user/findId" onclick="window.open(this.href,'_blank', 'width=500, height=300'); return false;">ID 찾기</a></li>
			<li><a href="${pageContext.request.contextPath}/user/findPwd" onclick="window.open(this.href, '_blank', 'width=500, height=350'); return false;">비밀번호 찾기</a></li>
		</ul>
	</form>
	</div>
	<div>
		<pre class="exam">학생 2023000001 교직원 230001 교수 23000001 비밀번호 123123</pre>
	</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>