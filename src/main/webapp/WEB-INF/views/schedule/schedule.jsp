<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">

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
					<td><a href="/schedule/list" class="selected--menu">학사일정</a></td>
				</tr>
				<c:if test="${principal.userRole == 'staff'}">
					<tr>
						<td><a href="/schedule/create"> 학사일정 등록</a></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>

	<main>
		<h1>학사일정</h1>
		<form action="/schedule/list" method="get" class="form--container">
			<div class="container">
				<c:forEach var="scheduleList" items="${scheduleList}">
					<table class="room--table">
						<tbody>
							<tr>
								<td class="month" width="100px;"><fmt:formatDate value="${scheduleList.startDay}" pattern="M" /></td>
								<td class="line"><fmt:formatDate value="${scheduleList.startDay}" pattern="MM-dd" />~<fmt:formatDate value="${scheduleList.endDay}" pattern="MM-dd" /></td>
								<td class="line">${scheduleList.information}</td>
							</tr>
						</tbody>
					</table>
				</c:forEach>
			</div>
		</form>
	</main>
</div>
</body>
</html>