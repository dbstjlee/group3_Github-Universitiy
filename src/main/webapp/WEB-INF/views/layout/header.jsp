<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<head>
<meta charset="UTF-8">
<title>그린대학교 학사관리시스템</title>
</head>
<body>
<header>
<form action="${pageContext.request.contextPath}/" method="get">
	<div class="header--top">
		<ul>
			<li>.</li>
			<li>${principal.username}님(${principal.id})</li>
			<li style="margin: 0 15px;">|</li>
			<li>.</li>
			<li><a href="${pageContext.request.contextPath}/user/logOut">로그아웃</a></li>
		</ul>
	</div>
	
	<nav class="main--menu">
		<a href="/${pageContext.request.contextPath}/main"><img class="logo" alt="" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>
		
		<c:choose>
			<c:when test="${principal.userRole == 'student'}">
				<ul>
					<li><a href="${pageContext.request.contextPath}/">홈</a></li>
					<li><a href="${pageContext.request.contextPath}/info/student">My</a></li>
					<li><a href="${pageContext.request.contextPath}/subject/list/1">수업</a></li>
					<li><a href="${pageContext.request.contextPath}/sugang/subjectList/1">수강신청</a></li>
					<li><a href="${pageContext.request.contextPath}/grade/thisSemester">성적</a></li>
					<li><a href="${pageContext.request.contextPath}/notice">학사정보</a></li>
				</ul>
			</c:when>
			<c:when test="${principal.userRole == 'professor'}">
				<ul>
					<li><a href="${pageContext.request.contextPath}/user/main">홈</a></li>
					<li><a href="${pageContext.request.contextPath}/info/professor">My</a></li>
					<li><a href="${pageContext.request.contextPath}/subject/list/1">수업</a></li>
					<li><a href="${pageContext.request.contextPath}/notice">학사정보</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul>
					<li><a href="${pageContext.request.contextPath}/user/main">홈</a></li>
					<li><a href="${pageContext.request.contextPath}/info/staff">My</a></li>
					<li><a href="${pageContext.request.contextPath}/user/studentList">학사관리</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/college">등록</a></li>
					<li><a href="${pageContext.request.contextPath}/notice">학사정보</a></li>
				</ul>	
			</c:otherwise>
		</c:choose>
	</nav>
	</form>
	</header>