<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/myInfo.css">
<!-- My 상세 메뉴 -->

<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>MY</h2>
		</div>

		<!-- 좌측 메뉴 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td>
							<a href="info/student" class="selected--menu">내 정보 조회</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/password">비밀번호 변경</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/break/application">휴학 신청</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/break/list">휴학 내역 조회</a>
						</td>

					</tr>
					<tr>
						<td>
							<a href="/tuition/list">등록금 내역 조회</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/tuition/payment">등록금 납부 고지서</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 개인 정보 수정  -->
	<form action="/info/update" method="post">
		<main>
			<h1>개인 정보 수정</h1>
			<div class="split--div"></div>
			<c:choose>
				<c:when test="${principal.userRole == 'student'}">
					<p>학생영역</p>
					<table class="update--table">
						<tbody>
							<tr>
								<td>
									<label for="address">주소</label>
								</td>
								<td>
									<input type="text" name="address" id="address" class="input--box" value="${studentInfo.address}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="tel">전화번호</label>
								</td>
								<td>
									<input type="text" name="tel" id="tel" class="input--box" value="${studentInfo.tel}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="email">이메일</label>
								</td>
								<td>
									<input type="text" name="email" id="email" class="input--box" value="${studentInfo.email}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="password">비밀번호 확인</label>
								</td>
								<td>
									<input type="password" name="password" id="password" class="input--box">
								</td>
							</tr>
						</tbody>
					</table>
					<br>
					<button type="submit" class="btn btn-dark update--button">수정하기</button>
				</c:when>
				<c:when test="${principal.userRole == 'professor'}">
					<p>교수영역</p>
					<table class="update--table">
						<tbody>
							<tr>
								<td>
									<label for="address">주소</label>
								</td>
								<td>
									<input type="text" name="address" id="address" class="input--box" value="${professorInfo.address}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="tel">전화번호</label>
								</td>
								<td>
									<input type="text" name="tel" id="tel" class="input--box" value="${professorInfo.tel}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="email">이메일</label>
								</td>
								<td>
									<input type="text" name="email" id="email" class="input--box" value="${professorInfo.email}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="password">비밀번호 확인</label>
								</td>
								<td>
									<input type="password" name="password" id="password" class="input--box">
								</td>
							</tr>
						</tbody>
					</table>
					<br>
					<button type="submit" class="btn btn-dark update--button">수정하기</button>
				</c:when>
				<c:when test="${principal.userRole == 'staff'}">
					<p>직원영역</p>
					<table class="update--table">
						<tbody>
							<tr>
								<td>
									<label for="address">주소</label>
								</td>
								<td>
									<input type="text" name="address" id="address" class="input--box" value="${staffInfo.address}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="tel">전화번호</label>
								</td>
								<td>
									<input type="text" name="tel" id="tel" class="input--box" value="${staffInfo.tel}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="email">이메일</label>
								</td>
								<td>
									<input type="text" name="email" id="email" class="input--box" value="${staffInfo.email}">
								</td>
							</tr>
							<tr>
								<td>
									<label for="password">비밀번호 확인</label>
								</td>
								<td>
									<input type="password" name="password" id="password" class="input--box">
								</td>
							</tr>
						</tbody>
					</table>
					<br>
					<button type="submit" class="btn btn-dark update--button">수정하기</button>
				</c:when>
			</c:choose>
		</main>
	</form>
</div>
</body>
</html>