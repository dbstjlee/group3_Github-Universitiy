<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>단과대학</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/college.css">
</head>
<body>
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
				<a href="${pageContext.request.contextPath}/admin/addCollege" class="button">등록</a> <a href="${pageContext.request.contextPath}/admin/deleteCollege" class="button">삭제</a>
			</div>
			<!-- 단과대학 조회 -->
<div class="total-container">
    <table class="table-container">
        <thead>
            <tr class="first-tr">
                <th>단과대학 ID</th>
                <th>이름</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="college" items="${colleges}">
                <tr>
                    <td>${college.id}</td>
                    <td>${college.name}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin/deleteCollege" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
                            <input type="hidden" name="id" value="${college.id}"> 
                            <input type="submit" value="삭제" class="button-delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

			<br>
			<!-- 단과대학 등록 -->
			<form action="${pageContext.request.contextPath}/admin/addCollege" method="post" class="college-form">
				<div class="insert-form">
					<div class="form-header">
						<span class="material-symbols-outlined">단과대학</span> <span class="form-title">등록하기</span>
					</div>
					<div class="form-body">
						<input type="text" id="collegeName" class="input-box" name="collegeName" placeholder="단과대학을 입력해주세요" required> <input type="submit" value="입력" class="button-add">
					</div>
				</div>
			</form>

		</main>
	</div>
</body>
</html>
