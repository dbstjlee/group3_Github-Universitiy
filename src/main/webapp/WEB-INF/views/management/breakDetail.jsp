<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<style>
.document--layout {
	border: 1px solid #4c4b4b;
	padding: 50px 30px 30px 30px;
	text-align: center;
	margin-bottom: 30px;
}

.document--layout th {
	text-align: center;
	padding: 2px 20px;
}

.document--layout td {
	text-align: right;
	padding: 2px 8px 2px 50px;
}
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
							<a href="/management/new-semester">새학기 적용</a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="/management/break" class="selected--menu">휴학 처리</a>
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
		<h1>휴학 내역 조회</h1>
		<div class="split--div"></div>

		<div class="d-flex flex-column align-items-center" style="width: 100%">
			<div class="document--layout">
				<h3>휴학 신청서</h3>
				<table border="1">
					<tr>
						<th>단 과 대</th>
						<td>${breakApp.collegeName}</td>
						<th>학 과</th>
						<td>${breakApp.departmentName}</td>
					</tr>
					<tr>
						<th>학 번</th>
						<td>${breakApp.studentId}</td>
						<th>학 년</th>
						<td>${breakApp.studentGrade}학년</td>
					</tr>
					<tr>
						<th>전 화 번 호</th>
						<td>${breakApp.studentTel}</td>
						<th>성 명</th>
						<td>${breakApp.studentName}</td>
					</tr>
					<tr>
						<th>주 소</th>
						<td colspan="3">${breakApp.studentAdds}</td>
					</tr>
					<tr>
						<th>기 간</th>
						<td colspan="3">${breakApp.fromYear}년도${breakApp.fromSemester}학기부터&nbsp;${breakApp.toYear}년도${breakApp.toSemester}학기까지</td>
					</tr>
					<tr>
						<th>휴 학 구 분</th>
						<td colspan="3">${breakApp.type}휴학</td>
					</tr>
					<tr>
						<td colspan="4">
							<p>위와 같이 휴학하고자 하오니 허가하여 주시기 바랍니다.</p>
							<br>
							<p>
								<fmt:formatDate value="${breakApp.appDate}" pattern="yyyy년 MM월 dd일" />
							</p>
						</td>
					</tr>
				</table>
			</div>

			<c:if test="${breakApp.status == '처리중'}">
				<div class="d-flex jusitify-contents-center">
					<form action="/management/break" method="post" class="d-flex flex-column align-items-center">
						<input type="hidden" name="status" value="승인">
						<input type="hidden" name="id" value="${breakApp.id}">
						<input type="hidden" name="studentId" value="${breakApp.studentId}">
						<input type="hidden" name="type" value="${breakApp.type}">
						<button type="submit" class="btn btn-dark" onclick="return confirm('해당 신청을 승인하시겠습니까?')">승인하기</button>
					</form>
					&nbsp; &nbsp; &nbsp;
					<form action="/management/break" method="post" class="d-flex flex-column align-items-center">
						<input type="hidden" name="id" value="${breakApp.id}">
						<input type="hidden" name="status" value="반려">
						<button type="submit" class="btn btn-dark" onclick="return confirm('해당 신청을 반려하시겠습니까?')">반려하기</button>
					</form>
				</div>
			</c:if>

		</div>
	</main>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>