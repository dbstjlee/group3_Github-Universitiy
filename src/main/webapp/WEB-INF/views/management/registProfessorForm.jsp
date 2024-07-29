<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin.css">
<style>
</style>
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
							<a href="/management/professor" class="selected--menu">교수 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/staff">직원 등록</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/new-semester">새학기 적용</a>
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
					<tr>
						<td>
							<a href="/management/tuition">등록금 고지서 발송</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>교수 등록</h1>
		<div class="split--div"></div>
		<form action="/management/professor" method="post">
			<table class="table--container">
				<tr>
					<td>
						<label for="name">이름</label>
					</td>
					<td>
						<input type="text" name="name" id="name" class="input--box">
					</td>
				</tr>
				<tr>
					<td>
						<label for="birthDate">생년월일</label>
					</td>
					<td>
						<input type="date" name="birthDate" id="birthDate" class="input--box">
					</td>
				</tr>
				<tr>
					<td style="padding-top: 7px">
						<label>성별</label>
					</td>
					<td style="padding-top: 7px">
						<label for="male">남성</label> <input type="radio" value="남성" name="gender" id="male" checked="checked"> &nbsp; <label for="female">여성</label> <input type="radio"
							value="여성" name="gender" id="female"
						>
					</td>
				</tr>
				<tr>
					<td>
						<label for="address">주소</label>
					</td>
					<td>
						<input type="text" name="address" id="address" class="input--box">
					</td>
				</tr>
				<tr>
					<td>
						<label for="tel">전화번호</label>
					</td>
					<td>
						<input type="text" name="tel" id="tel" class="input--box">
					</td>
				</tr>
				<tr>
					<td>
						<label for="email">이메일</label>
					</td>
					<td>
						<input type="email" name="email" id="email" class="input--box">
					</td>
				</tr>
				<tr>
					<td>
						<label for="deptId">과 ID</label>
					</td>
					<td>
						<input type="text" name="deptId" id="deptId" class="input--box">
					</td>
				</tr>
			</table>
			<div class="button--container">
				<input type="submit" value="입력">
			</div>
		</form>
	</main>
</div>
</body>
</html>