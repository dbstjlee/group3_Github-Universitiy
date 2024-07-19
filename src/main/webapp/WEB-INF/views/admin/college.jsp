<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>단과대학</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/college.css">

<body>

	<div class="container">
	
		<div class="sub-menu-top">
			<h2>등록</h2>
		</div>
		
		<div class="sub-menu-mid">
			<table class="sub-menu-table">
				<tr>
				<a href="${pageContext.request.contextPath}/admin/college.jsp" class="selected-menu">단과대학</a>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/department">학과</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/room">강의실</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/subject">강의</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/tuituion">대학별 등록금</a></td>
				</tr>
			</table>
		</div>
	</div>
	
	<main>
	
	</main>
	
</body>
</html>