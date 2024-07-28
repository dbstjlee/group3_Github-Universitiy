<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="UTF-8">
<title>그린대학교 학사관리시스템</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
</head>
<body>
	<header>
		<form action="${pageContext.request.contextPath}/" method="get" style="margin-left: 0">
			<div class="header--top">
				<ul>
					<li>${principal.username}님(${principal.id})</li>
					<li style="margin: 0 15px;">|</li>
					<li><a href="${pageContext.request.contextPath}/user/logOut">로그아웃</a></li>
				</ul>
			</div>

			<nav class="main--menu">
				<a href="${pageContext.request.contextPath}/"><img class="logo" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>

				<c:choose>
					<c:when test="${principal.userRole == 'student'}">
						<ul>
							<li><a href="${pageContext.request.contextPath}/">홈</a></li>
							<li><a href="${pageContext.request.contextPath}/info/student">MY</a></li>
							<li><a href="${pageContext.request.contextPath}/sugang/list">수업</a></li>
							<li><a href="${pageContext.request.contextPath}/sugang/subjectList">수강신청</a></li>
							<li><a href="${pageContext.request.contextPath}/grade/thisSemester">성적</a></li>
							<li><a href="${pageContext.request.contextPath}/notice/list">학사정보</a></li>
						</ul>
					</c:when>
					<c:when test="${principal.userRole == 'professor'}">
						<ul>
							<li><a href="${pageContext.request.contextPath}/">홈</a></li>
							<li><a href="${pageContext.request.contextPath}/info/professor">MY</a></li>
							<li><a href="${pageContext.request.contextPath}/subject/allSubject">수업</a></li>
							<li><a href="${pageContext.request.contextPath}/notice/list">학사정보</a></li>
						</ul>
					</c:when>
					<c:when test="${principal.userRole == 'staff'}">
						<ul>
							<li><a href="${pageContext.request.contextPath}/">홈</a></li>
							<li><a href="${pageContext.request.contextPath}/info/staff">MY</a></li>
							<li><a href="${pageContext.request.contextPath}/management/studentList">학사관리</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/college">등록</a></li>
							<li><a href="${pageContext.request.contextPath}/notice/list">학사정보</a></li>
						</ul>
					</c:when>
				</c:choose>
			</nav>
		</form>
	</header>