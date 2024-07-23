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
							<a href="/management/tuition">등록금 고지서 발송</a>
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
				</tbody>
			</table>
		</div>
	</div>
	<main>
		<h1>휴학 처리</h1>
		<div class="split--div"></div>
		<c:choose>
			<c:when test="${isBreak == 1}">
				<c:choose>
					<c:when test="${breakAppList.size() > 0}">
						<div class="d-flex flex-column align-items-center" style="width: 100%">

							<table border="1" class="list--table">
								<thead>
									<tr>
										<th>신청일자</th>
										<th>신청자 학번</th>
										<th>구분</th>
										<th>시작학기</th>
										<th>종료학기</th>
										<th>신청서 확인</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach var="breakApp" items="${breakAppList}">
										<tr>
											<td>${breakApp.appDate}</td>
											<td>${breakApp.studentId}</td>
											<td>${breakApp.type}휴학</td>
											<td>${breakApp.fromYear}년도&nbsp;${breakApp.fromSemester}학기</td>
											<td>${breakApp.toYear}년도&nbsp;${breakApp.toSemester}학기</td>
											<td>
												<a href="/management/breakDetail?id=${breakApp.id}">Click</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					<a href="/management/breakEnd"><button type="submit" class="btn btn-primary ">휴학 신청 기간 종료</button></a>
					</c:when>

					<c:otherwise>
						<p class="no--list--p">대기 중인 신청 내역이 없습니다.</p>
					<a href="/management/breakEnd"><button type="submit" class="btn btn-primary ">휴학 신청 기간 종료</button></a>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<a href="/management/breakStart"><button type="submit" class="btn btn-primary ">휴학 신청 기간 시작</button></a>
			</c:otherwise>
		</c:choose>
	</main>
</div>
</body>
</html>