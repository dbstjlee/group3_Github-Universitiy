<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="UTF-8">
<title>그린대학교 학사관리시스템</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css?v=1.0">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
				<div class="logo--section">
				<a href="${pageContext.request.contextPath}/"><img class="logo" src="${pageContext.request.contextPath}/resources/images/logo.gif"></a>
				</div>
<body>	
	<header>
		<form action="${pageContext.request.contextPath}/" method="get" style="margin-left: 0">
			<div class="header--top">
			<div class="hamburger-menu">
  					<span></span>
  					<span></span>
  					<span></span>
			</div>
				<ul>
					<li>${principal.username}님(${principal.id})</li>
					<li style="margin: 0 15px;">|</li>
					<li><a href="${pageContext.request.contextPath}/user/logOut">로그아웃</a></li>
				</ul>
			</div>
	<div class="sidebar">
			<nav class="main--menu">

				<c:choose>
					<c:when test="${principal.userRole == 'student'}">
						<ul>
							<li><a href="${pageContext.request.contextPath}/">홈</a></li>
							<li><a href="${pageContext.request.contextPath}/info/student">MY</a></li>
							<li><a href="${pageContext.request.contextPath}/subject/allSubject">수업</a></li>
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
	</div>
		</form>
		
	</header>
	 <script>
        document.querySelector('.hamburger-menu').addEventListener('click', function() {
          document.querySelector('.sidebar').classList.toggle('open');
          document.body.classList.toggle('shift');
        });
    </script>
	
	

  <!-- 사이드바 내용 -->
  
