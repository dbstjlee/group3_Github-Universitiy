<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>My</h2>
		</div>

		<!-- 좌측 메뉴 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="${pageContext.request.contextPath}/info/student" class="selected--menu">내 정보 조회</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/password">비밀번호 변경</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/break/application">휴학 신청</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/break/list">휴학 내역 조회</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/tuition/check">등록금 내역 조회</a></td>

				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/tuition/payment">등록금 납부 고지서</a></td>

				</tr>
			</table>
		</div>
	</div>
	<h1>휴학 신청</h1>
	<hr>
	<div>
		<form action="${pageContext.request.contextPath}/break/application" method="post">
			<div>
				<h3>휴학 신청서</h3>
				<table border="1">
					<tbody>
						<tr>
							<th>단과대</th>
							<td>${student.collname}</td>
							<th>학과</th>
							<td>${student.deptname}</td>
						</tr>
						<tr>
							<th>학번</th>
							<td>${student.id}</td>
							<th>학년</th>
							<td>${student.grade}</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>${student.tel}</td>
							<th>성명</th>
							<td>${student.name}</td>
						</tr>
						<tr>
							<th>주소</th>
							<td>${student.address}</td>
						</tr>
						<tr>
							<th>기간</th>
							<c:set var="today" value="<%=new java.util.Date()%>" />
							<fmt:formatDate value="${thisYear}" pattern="yyyy" var="thisYear" />
							<td colspan="3">${thisYear}년도 %{thisSemester}학기 부터 <select id="breakYear" name="breakYear">
									<c:forEach begin="0" end="2" var="i">
										<option value="${thisYear + i}">${thisYear + i}</option>
									</c:forEach>
							</select> 년도 <select id="semester" name="semester">
									<option value="1">1</option>
									<option value="2">2</option>
							</select> 학기 까지
							</td>
						</tr>
						<tr>
							<th>휴학 구분</th>
							<td><input type="radio" name="breakType" id="nom" value="nom" checked="checked"> <label for="nomalBreak">일반 휴학</label> <input type="radio" name="breakType"
								id="fam" value="fam"> <label for="familyBreak">임신·출산·육아 휴학</label> <input type="radio" name="breakType" id="med" value="med"> <label for="medicalBreak">질병
									휴학</label> <input type="radio" name="breakType" id="bus" value="bus"> <label for="businessBreak">창업 휴학</label> <input type="radio" name="breakType" id="mil" value="mil">
								<label for="militaryBreak">군입대 휴학</label></td>
						</tr>
					</tbody>
				</table>
				<button type="submit">제출</button>
			</div>
		</form>
	</div>
</body>
</html>