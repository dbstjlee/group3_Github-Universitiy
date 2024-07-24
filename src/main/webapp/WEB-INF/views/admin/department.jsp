<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>학과</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/department.css">
</head>
<body>
	<div class="t-main">
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
					<a href="${pageContext.request.contextPath}" class="button">등록</a> <a href="${pageContext.request.contextPath}" class="button">수정</a> <a
						href="${pageContext.request.contextPath}" class="button">삭제</a>
				</div>

				<div class="container">
					<form action="${pageContext.request.contextPath}/admin/department" method="post" class="department-form">
						<div class="insert-form">
						<ul class="form-header">
							<li style="height: 24px; margin-right: 2px;"><span class="material-symbols-outlined">school</span>
							<li style="height: 24px;"><span class="form-title">등록하기</span>
						</ul>

						<input type="text" class="input-box" name="name" placeholder="학과를 입력해주세요"> <select name="collegeId" class="input-box">
							<c:forEach var="college" items="${colleges}">
								<option value="${college.id}">${college.name}</option>
							</c:forEach>
						</select> <input type="submit" class="button" value="입력">
						</div>
					</form>


					<!-- 학과 조회 -->
					<div class="total-container">
						<table class="total-container">
							<thead>
								<tr class="first-tr">
									<th>ID</th>
									<th>학과</th>
									<th>단과대학ID</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="department" items="${departments}">
									<tr>
										<td>${department.id}</td>
										<td>${department.name}</td>
										<td>${department.collegeId}</td>
										<td>
											<form action="${pageContext.request.contextPath}/admin/deleteCollege" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
												<input type="hidden" name="id" value="${department.id}"> <input type="submit" value="삭제" class="button-delete">
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<br>



			</main>


		</div>
	</div>
</body>
</html>
