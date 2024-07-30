<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">

<style>
.room--table {
	text-align: center;
	margin-top: 20px;
	margin: 10px;
}

.room--table td {
	padding: 10px;
	width: 300px;
}

.first--tr {
	font-weight: bold;
}

.mouth {
	background-color: #f5f5f5;
}

.line {
	
}

.container {
	margin-top: 100px;
}

.table {
	width: 800px;
}

.first--tr {
	background-color: #f7f6f6;
	font-weight: bolder;
	overflow: hidden;
	text-overflow: ellipsis;
}

.button {
	padding: 5px;
	border: 1px solid #031734;
	border-radius: 3px;
	background-color: #031734;
	color: #ccc;
	text-decoration: none;
	margin-left: 770px;
}
</style>
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="/notice/list">공지사항</a></td>
				</tr>
				<tr>
					<td><a href="/schedule/list">학사일정</a></td>
				</tr>
				<c:if test="${principal.userRole == 'staff'}">
					<tr>
						<td><a href="/schedule/create" class="selected--menu"> 학사일정 등록</a></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>

	<main>
		<h1>학사일정</h1>
		<form action="/schedule/add">
			<div class="container">
				<table class="table">
					<thead>
						<tr class="first--tr">
							<th>ID</th>
							<th>날짜</th>
							<th>내용</th>
						</tr>
					</thead>
					<c:forEach var="scheduleListById" items="${scheduleListById}">
						<tbody>
							<tr onclick="location.href='/schedule/detail?id=${scheduleListById.id}'">
								<td>${scheduleListById.id}</td>
								<td><fmt:formatDate value="${scheduleListById.startDay}" pattern="MM-dd" />~<fmt:formatDate value="${scheduleListById.endDay}" pattern="MM-dd" /></td>
								<td>${scheduleListById.information}</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
		</form>
		<a href="/schedule/add" class="button">등록</a>

	</main>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>