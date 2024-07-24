<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<h1>예비 수강 신청</h1>
	<hr>
	<div class="d-flex justify-content-between align-items-start" style="width: 100%">
		<div>
			<h4>
				<span style="font-weight: 600;"> 신청 내역&nbsp; <span style="color: gray; font-size: 18px;">[총 ${totalGrade}학점]</span>
				</span>&nbsp;
			</h4>
			<table border="1" class="sub--list--table">
				<thead>
					<tr>
						<th>학수번호</th>
						<th style="width: 250px;">강의명</th>
						<th>담당교수</th>
						<th>학점</th>
						<th>요일시간 (강의실)</th>
						<th>현재인원</th>
						<th>정원</th>
						<th>수강신청</th>
					</tr>
				</thead>
				<c:forEach var="sugangList" items="${sugangList}">
					<tbody>
						<tr>
							<td>${sugangList.subjectId}</td>
							<td>${sugangList.subjectName}</td>
							<td>${sugangList.professorName}</td>
							<td>${sugangList.grades}</td>
							<td>${sugangList.subjectDay} ${sugangList.startTime}:00 - ${sugangList.endTime}:00 (${sugangList.roomId})</td>
							<td>${sugangList.numOfStudent}</td>
							<td>${sugangList.capacity}</td>
							<td class="sub--list--button--row">
								<form action="${pageContext.request.contextPath}/sugang/pre" method="post">
									<input type="hidden" name="delete" value="${sugangList.subjectId}">
									<button type="submit" onclick="return confirm('예비수강신청을 취소하시겠습니까?');" style="background-color: #a7a7a7;">취소</button>
								</form>
							</td>
						</tr>
					</tbody>
				</c:forEach>
			</table>
			<br> <br>
		</div>
		<!-- if -->
		<!-- 강의 검색으로 가기 -->
		<a href="${pageContext.request.contextPath}/sugang/pre"><button class="preStuSubList--button">강의 검색</button></a>
	</div>
	<p>수강 신청 내역이 없습니다.</p>
</body>
</html>