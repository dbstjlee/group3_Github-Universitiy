<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴학 신청 화면</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/break/application">휴학 신청</a>
	<a href="${pageContext.request.contextPath}/break/list">휴학 신청 내역</a>
	<a href="${pageContext.request.contextPath}/tuition/check">등록금 내역 확인</a>
	<a href="${pageContext.request.contextPath}/tuition/payment">등록금 고지서</a>
	<div>
		<form action="${pageContext.request.contextPath}/break/application" method="post">
			<div>
				<h3>휴학 신청서</h3>
				<table border="1">
					<tbody>
						<tr>
							<th>단과대</th>
							<td>공과대학</td>
							<!-- 학생 정보에서 받아오기 -->
							<th>학과</th>
							<td>컴퓨터공학과</td>
							<!-- 학생 정보에서 받아오기 -->
						</tr>
						<tr>
							<th>학번</th>
							<td>2023000001</td>
							<!-- 학생 정보에서 받아오기 -->
							<th>학년</th>
							<td>1학년</td>
							<!-- 학생 정보에서 받아오기 -->
						</tr>
						<tr>
							<th>전화번호</th>
							<td>010-5267-1815</td>
							<!-- 학생 정보에서 받아오기 -->
							<th>성명</th>
							<td>박시우</td>
							<!-- 학생 정보에서 받아오기 -->
						</tr>
						<tr>
							<th>주소</th>
							<td>부산시 남구</td>
							<!-- 학생 정보에서 받아오기 -->
						</tr>
						<tr>
							<th>기간</th>
							<!-- 학생 정보에서 받아오기 -->
								<c:set var="today" value="<%=new java.util.Date()%>" />
							 	<fmt:formatDate value="${today}" pattern="yyyy" var="currentYear"/>
							<td colspan="3">${currentYear}년도 1학기 부터
							<!-- 값 전송 -->
							<!-- TODO 현재 년도와 학기와 동일한 값은 선택 불가능 하게 수정 -->
							 <select id="breakYear" name="breakYear">
							 	<c:forEach begin="0" end="2" var="i">
									<option value="${currentYear + i}">${currentYear + i}</option>
							 	</c:forEach>
							</select> 년도
							<select id="semester" name="semester">
									<option value="1">1</option>
									<option value="2">2</option>
							</select> 학기 까지
							</td>
						</tr>
						<tr>
							<th>휴학 구분</th>
							<td>
								<input type="radio" name="breakType" id="nomalBreak" value="nomalBreak" checked="checked">
								<label for="nomalBreak">일반 휴학</label>
								<input type="radio" name="breakType" id="familyBreak" value="familyBreak">
								<label for="familyBreak">임신·출산·육아휴학</label>
								<input type="radio" name="breakType" id="medicalBreak" value="medicalBreak">
								<label for="medicalBreak">질병 휴학</label>
								<input type="radio" name="breakType" id="businessBreak" value="businessBreak">
								<label for="businessBreak">창업 휴학</label>
								<input type="radio" name="breakType" id="militaryBreak" value="militaryBreak">
								<label for="militaryBreak">군입대 휴학</label>
							</td>
						</tr>
					</tbody>
				</table>
				<button type="submit">제출</button>
			</div>
		</form>
	</div>
</body>
</html>