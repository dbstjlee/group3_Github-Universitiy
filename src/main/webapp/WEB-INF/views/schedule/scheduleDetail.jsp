<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

.mouth {
	background-color: #f5f5f5;
}

.line {
	
}

.container {
	margin-top: 100px;
}

.first--tr {
	background-color: #f7f6f6;
	font-weight: bolder;
	overflow: hidden;
	text-overflow: ellipsis;
}

.info {
	height: 100px;
}

.td {
	background-color: #fff;
	width: 300px;
}

.button {
	padding: 5px;
	border: 1px solid #031734;
	border-radius: 3px;
	background-color: #031734;
	color: #ccc;
	text-decoration: none;
}

.checkbox {
	display: flex;
	justify-content: flex-end;
}

.checkbox>a {
	margin: 0 10px;
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
						<td><a href="/schedule/create"> 학사일정 등록</a></td>
					</tr>
				</c:if>
			</table>
		</div>
	</div>

	<main>
		<form action="/schedule/detail?id=${schedule.id}" method="get" class="form--container">
			<table class="table">
				<thead>
					<tr class="first--tr">
						<th colspan="2">2023년 학교 학사일정</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>시작날짜</td>
						<td><fmt:formatDate value="${schedule.startDay}" pattern="MM-dd" /></td>
					</tr>
					<tr>
						<td>종료날짜</td>
						<td><fmt:formatDate value="${schedule.endDay}" pattern="MM-dd" /></td>
					</tr>
					<tr>
						<td class="td">내용</td>
						<td class="info">${schedule.information}</td>
					</tr>
				</tbody>
			</table>
			<div class="checkbox">
				<a href="/schedule/update?id=${schedule.id}" class="button">수정</a> <a href="/schedule/delete?id=${schedule.id}" class="button">삭제</a>
			</div>
		</form>
	</main>
</div>
</body>
</html>