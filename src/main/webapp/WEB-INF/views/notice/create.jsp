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
								<td><a href="/notice" class="selected--menu">공지사항</a></td>
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

		<!-- 공지 등록 -->
		<div class="write--div">
			<form action="/notice/create" method="post">
				<div class="title--container">
					<div class="category">
						<select name="category" id="category" class="input--box">
							<option value="[일반]">[일반]</option>
							<option value="[학사]">[학사]</option>
							<option value="[장학]">[장학]</option>
						</select>
					</div>
					<div class="title">
						<input type="text" class="form-control form-control-sm" name="title" placeholder="제목을 입력하세요" required="required" style="width: 900px;">
					</div>
				</div>

				<div class="content--container">
					<textarea name="content" class="form-control" cols="100" rows="20" placeholder="내용을 입력하세요"></textarea>
				</div>
				<a href="/notice/list" class="button">목록</a> <input type="submit" class="button" value="등록">
			</form>
		</div>
	</main>
</div>
</body>
</html>