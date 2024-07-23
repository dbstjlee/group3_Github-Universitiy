<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사관리</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td>
							<a href="/management/studentList">학생 명단 조회</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/professorList">교수 명단 조회</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/student">학생 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/professor">교수 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/staff">직원 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/tuition" class="selected--menu">등록금 고지서 발송</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/break">휴학 처리</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/sugang">수강 신청 기간 설정</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>등록금 고지서 발송</h1>
		<div class="split--div"></div>
		<a href="/management/bill"><button type="submit" class="btn btn-primary">등록금 고지서 발송</button></a>
		<c:if test="${insertCount != null}">
			<%
			out.println("<script>alert('" + request.getAttribute("insertCount") + "개의 등록금 고지서가 생성되었습니다.'); history.back(); </script>");
			%>
		</c:if>
	</main>
</div>
</body>
</html>