<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학과</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/department.css">
<body>

	<form action="${pageContext.request.contextPath}/admin/department" method="get">
		<div class="sub-main">
			<div class="sub-menu">
				<div class="sub-menu-top">
					<h2>등록</h2>
				</div>
				<div class="sub-menu-mid">
					<table class="mid-table">
						<tbody>
							<tr>
								<td><a href="${pageContext.request.contextPath}/admin/college">단과대학</a></td>
							</tr>
							<tr>
								<td><a href="${pageContext.request.contextPath}/admin/department" class="selected-menu">학과</a></td>
							</tr>
							<tr>
								<td><a href="${pageContext.request.contextPath}/admin/room">강의실</a></td>
							</tr>
							<tr>
								<td><a href="${pageContext.request.contextPath}/admin/subject">강의</a></td>
							</tr>
							<tr>
								<td><a href="${pageContext.request.contextPath}/admin/tuition">단과대학별 등록금</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<main>
				<h1>학과</h1>
				<div class="split-div"></div>
				<div class="select-button">
				<a href="${pageContext.request.contextPath}" class="button">등록</a> 
				<a href="${pageContext.request.contextPath}" class="button">수정</a>
				<a href="${pageContext.request.contextPath}" class="button">삭제</a>
			</div>
			</main>
	
	</form>

</body>
</html>