<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notice.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 메뉴 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>

		<c:choose>
			<c:when test="${principal.userRole == 'staff'}">
				<div class="sub--menu--mid">
					<table class="sub--menu--table" border="1">
						<tbody>
							<tr>
								<td><a href="/notice/list" class="selected--menu">공지사항</a></td>
							</tr>
							<tr>
								<td><a href="/schedule/list">학사일정</a></td>
							</tr>
							<tr>
								<td><a href="/schedule/create">학사일정 등록</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:when>

			<c:otherwise>
				<div class="sub--menu--mid">
					<table class="sub--menu--table" border="1">
						<tbody>
							<tr>
								<td><a href="/notice/list" class="selected--menu">공지사항</a></td>
							</tr>
							<tr>
								<td><a href="/schedule/list">학사일정</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>

		<!-- 공지 상세 조회 -->
		<div class="container">
			<table class="table">
				<tr class="title">
					<td class="type">제목</td>
					<td>${notice.category}&nbsp;${notice.title}</td>
				</tr>
				<tr class="content--container">
					<td class="type">내용</td>
					<td>${notice.content}</td>
				</tr>
			</table>
			<div class="select--button">
				<a href="/notice/list" class="button">목록</a>
				<c:if test="${principal.userRole == 'staff'}">
					<a href="/notice/update?id=${notice.id}" class="button">수정</a>
					<a href="/notice/delete?id=${notice.id}" class="button">삭제</a>
				</c:if>
			</div>
		</div>
	</main>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>