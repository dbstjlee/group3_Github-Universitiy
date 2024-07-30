<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/subject.css">
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<title>수업-전체 강의 조회</title>

</head>
<body>

	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
		<div class="sub--menu">
			<div class="sub--menu--top">
				<h2>수업</h2>
			</div>
			<div class="sub--menu--mid">
				<table class="sub--menu--table" border="1">
					<tr>
						<td><a href="/subject/allSubject" class="selected--menu">전체 강의 조회</a></td>
					</tr>

		<c:if test="${principal.userRole == 'professor'}">
					<tr>
						<td><a href="/professor/mySubject">내 강의 조회</a></td>
					</tr>
					<tr>
						<td><a href="/evaluation/read"> 내 강의 평가</a></td>
					</tr>
		</c:if>
				</table>
			</div>
		</div>


		<main>
			<h1>전체 강의 조회</h1>
			<div class="split--div"></div>



			<div class="sub--filter">
				<form action="/subject/search" method="get">
					<div>
						<label for="subYear">연도 </label> <input type="number" value="2023" name="subYear" id="subYear" min="2005" max="2033"> <label for="subSemester">학기 </label> <select
							name="subSemester" id="subSemester">
							<option value="1">1학기</option>
							<option value="2">2학기</option>
						</select> <label for="deptId">개설학과</label> <select name="deptId" id="deptId">
							<option value="-1">전체</option>
							<c:forEach var="depart" items="${departList}">
								<option value="${depart.id}">${depart.name}</option>
							</c:forEach>
						</select> <label for="subName">강의명</label> <input type="text" name="name" list="subName">
						<button type="submit">
							<ul class="d-flex justify-content-center" style="margin: 0;">
								<li style="height: 24px; margin-right: 2px;">조회
								<li style="height: 24px;"><span class="material-symbols-outlined" style="font-size: 18px; padding-top: 4px;">search</span>
							</ul>
						</button>
					</div>
				</form>
			</div>
			<c:choose>
				<c:when test="${subjectList.isEmpty() == false}">
					<h4>
						<span style="font-weight: 600;">강의 목록</span>&nbsp; <span style="color: gray; font-size: 18px;">[총 ${totalCount}건]</span>
					</h4>
					<table border="1" class="sub--list--table">
						<thead>
							<tr>
								<th>연도/학기</th>
								<th>단과대학</th>
								<th>개설학과</th>
								<th>학수번호</th>
								<th>강의구분</th>
								<th style="width: 200px;">강의명</th>
								<th>담당교수</th>
								<th>학점</th>
								<th>수강인원</th>
								<th>정원</th>
								<th>강의계획서</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="subject" items="${subjectList}">
								<tr>
									<td>${subject.subYear}-${subject.semester}학기</td>
									<td>${subject.collName}</td>
									<td>${subject.deptName}</td>
									<td>${subject.id}</td>
									<td>${subject.type}</td>
									<td class="sub--list--name">${subject.name}</td>
									<td>${subject.professorName}</td>
									<td>${subject.grades}</td>
									<td>${subject.numOfStudent}</td>
									<td>${subject.capacity}</td>
									<td>
										<ul class="d-flex justify-content-center sub--plan--view" style="margin: 0;">
											<li style="height: 24px;"><a href="/subject/syllabus?subjectId=${subject.id}" onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;">조회</a>
											<li style="height: 24px;"><a href="/subject/syllabus?subjectId=${subject.id}" onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;"><span
													class="material-symbols-outlined">content_paste_search</span></a>
										</ul>
									</td>
								</tr>
							</c:forEach>


						</tbody>
					</table>
					<div class="page--list">
						<c:forEach begin="1" end="${totalPages}" var="i">
							<c:choose>
								<c:when test="${i == currentPage}">
									<span>${i}</span>
								</c:when>
								<c:otherwise>
									<span><a href="${pageContext.request.contextPath}/subject/allSubject?page=${i}" style="font-weight: 700; color: #007bff">${i}</a></span>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</c:when>
				<c:otherwise>
					<p class="no--list--p">검색 결과가 없습니다.</p>
				</c:otherwise>
			</c:choose>

		</main>
	</div>
	<%@ include file="/WEB-INF/views/layout/footer.jsp"%>