<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/document.css">
<body>
	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
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
						<td><a href="${pageContext.request.contextPath}/user/password">비밀번호 변경</a></td>

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
		<main>
			<h1>휴학 신청</h1>
			<div class="split--div"></div>
			<div class="d-flex flex-column align-items-center">
				<form action="${pageContext.request.contextPath}/break/application" method="post" class="d-flex flex-column align-items-center">
					<div class="document--layout">
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
									<td colspan="3">${student.address}</td>
								</tr>
								<tr>
									<th>기간</th>
									<td colspan="3">${thisYear}년도${thisSemester}학기부터<select id="breakYear" name="breakYear">
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
									<td colspan="3"><input type="radio" name="breakType" id="일반" value="일반" checked="checked"> <label for="일반">일반 휴학</label> <input type="radio"
										name="breakType" id="임신·출산·육아" value="임신·출산·육아"> <label for="임신·출산·육아">임신·출산·육아 휴학</label> <input type="radio" name="breakType" id="질병" value="질병"> <label
										for="질병">질병 휴학</label> <input type="radio" name="breakType" id="창업" value="창업"> <label for="businessBreak">창업 휴학</label> <input type="radio"
										name="breakType" id="군입대" value="군입대"> <label for="군입대">군입대 휴학</label></td>
								</tr>
								<tr>
									<td colspan="4">
										<p>위와 같이 휴학하고자 하오니 허가하여 주시기 바랍니다.</p> <br> <c:set var="now" value="<%=new java.util.Date()%>" />
										<p>
											<fmt:formatDate value="${now}" pattern="yyyy년 MM월 dd일" />
										</p>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<button type="submit" class="btn btn-dark" onclick="return confirm('휴학 신청서를 제출 하시겠습니까?')">제출</button>
				</form>
			</div>
		</main>
	</div>
</body>
</html>