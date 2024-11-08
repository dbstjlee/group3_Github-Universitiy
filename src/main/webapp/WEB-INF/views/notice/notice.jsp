<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notice.css">

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

	<!-- 공지사항 전체 조회 -->
	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>
		<form action="/notice/search" method="get" class="form--container">
			<select class="input--box" name="type">
				<option value="title">제목</option>
				<option value="keyword">제목+내용</option>
			</select> <input type="text" name="keyword" class="input--box" placeholder="검색어를 입력하세요"> <input type="submit" class="button" value="검색">
		</form>
		<table class="table">
			<tbody>
				<tr class="first--tr">
					<td>번호</td>
					<td>말머리</td>
					<td>제목</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
				<c:forEach var="noticeList" items="${noticeList}">
					<tr class="second--tr" onclick="location.href='/notice/read?id=${noticeList.id}'">
						<td>${noticeList.id}</td>
						<td>${noticeList.category}</td>
						<td>${noticeList.title}</td>
						<td><fmt:formatDate value="${noticeList.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${noticeList.views}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="paging--container">
		<c:forEach begin="1" end="${totalPages}"  var="i" >
			<c:choose>
				<c:when test="${ i == currentPage }">
					<span class="current-page">${i}&nbsp;&nbsp;</span>
				</c:when>
				<c:otherwise>
					<span><a href="/notice/list?page=${i}">${i}</a>&nbsp;&nbsp;</span>	
				</c:otherwise>
			</c:choose>
		</c:forEach>
			<c:if test="${principal.userRole == 'staff'}">
			<a href="/notice/create" class="button">등록</a>
			</c:if>
		</div>
	</main>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>