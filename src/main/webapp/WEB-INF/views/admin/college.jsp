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

	<form action="${pageContext.request.contextPath}/admin/college" method="get">
		<div class="sub-main">
			<div class="sub-menu">
				<div class="sub-menu-top">
					<h2>등록</h2>
				</div>
				<div class="sub-menu-mid">
					<table class="mid-table">
						<tbody>
							<tr>
								<td><a href="${pageContext.request.contextPath}/admin/college" class="selected-menu">단과대학</a></td>
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
								<td><a href="${pageContext.request.contextPath}/admin/tuition">단과대학별 등록금</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<main>
				<h1>단과대학</h1>
				<div class="split-div"></div>
				<div class="select-button">
					<a href="${pageContext.request.contextPath}" class="button">등록</a> <a href="${pageContext.request.contextPath}" class="button">삭제</a>
				</div>

				<!-- 단과대학 조회 -->
				<div class="total-container">
					<table class="table-container">
						<tr class="first-tr">
							<td>단과대학 ID</td>
							<td>이름</td>
						</tr>
						<c:forEach var="college" items="${colleges}">
							<div class="college-item">
								<tr>
									<td><a>${college.id}</a></td>
									<td><a>${college.name}</a></td>
								</tr>
							</div>
						</c:forEach>
					</table>
				</div>

				<!-- 단과대학 등록 -->
				<form class="college-form" action="${pageContext.request.contextPath}/admin/addCollege" method="post">
				<textarea name="collegeName" rows="1" placeholder="단과대학을 입력해주세요" required></textarea>
				<button type="submit">등록</button>
			</form>

			</main>
		</div>
	</form>
</body>
</html>