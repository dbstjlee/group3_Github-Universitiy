<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<body>
	<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
		<!-- 좌측 메뉴 -->
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/subjectList">강의 시간표 조회</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/pre">예비 수강 신청</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/preAppList?type=2">수강 신청</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/sugang/list">수강 신청 내역</a></td>
				</tr>
			</table>
		</div>
		<main>
			<c:choose>
				<c:when test="${listType == 1}">
					<h1>예비 수강 신청</h1>
					<div class="split--div"></div>
					<div class="d-flex justify-content-between align-items-start" style="width: 100%">
						<c:choose>
							<c:when test="${not empty sugangPreList}">
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
										<tbody>
											<c:forEach var="preSugang" items="${sugangPreList}">
												<tr>
													<td>${preSugang.subjectId}</td>
													<td>${preSugang.subjectName}</td>
													<td>${preSugang.professorName}</td>
													<td>${preSugang.grades}</td>
													<td>
														<c:choose>
															<c:when test="${preSugang.startTime < 10}">
																${preSugang.subjectDay} 0${preSugang.startTime}:00~${preSugang.endTime}:00&nbsp;(${preSugang.roomId})
															</c:when>
															<c:otherwise>
																${preSugang.subjectDay} ${preSugang.startTime}:00~${preSugang.endTime}:00&nbsp;(${preSugang.roomId})
															</c:otherwise>
														</c:choose>
													</td>
													<td>${preSugang.numOfStudent}</td>
													<td>${preSugang.capacity}</td>
													<td class="sub--list--button--row">
														<form action="${pageContext.request.contextPath}/sugang/pre" method="post">
															<input type="hidden" name="subjectId" value="${preSugang.subjectId}">
															<input type="hidden" name="type" value="1">
															<button type="submit" onclick="return confirm('수강신청을 취소하시겠습니까?');" style="background-color: #a7a7a7;">취소</button>
														</form>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:when>
							<c:otherwise>
								<div>
									<p class="no--list--p">수강 신청 내역이 없습니다.</p>
								</div>
							</c:otherwise>
						</c:choose>
						<!-- 강의 검색으로 가기 -->
						<a href="${pageContext.request.contextPath}/sugang/pre"><button class="preStuSubList--button">강의 검색</button></a>
					</div>
				</c:when>
				<c:when test="${listType == 2}">
					<h1>수강 신청</h1>
					<div class="split--div"></div>
					<div class="d-flex justify-content-between align-items-start" style="width: 100%">
						<div>
							<c:if test="${not empty resetList}">
								<div>
									<h4>
										<span style="font-weight: 600;"> 정원 초과된 신청 내역&nbsp; </span>
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
										<tbody>
											<c:forEach var="resetSugang" items="${resetList}">
												<tr>
													<td>${resetSugang.subjectId}</td>
													<td class="sub--list--name">${resetSugang.subjectName}</td>
													<td>${resetSugang.professorName}</td>
													<td>${resetSugang.grades}</td>
													<td>
														<c:choose>
															<c:when test="${resetSugang.startTime < 10}">
																${resetSugang.subjectDay} 0${resetSugang.startTime}:00~${resetSugang.endTime}:00&nbsp;(${resetSugang.roomId})
															</c:when>
															<c:otherwise>
																${resetSugang.subjectDay} ${resetSugang.startTime}:00~${resetSugang.endTime}:00&nbsp;(${resetSugang.roomId})
															</c:otherwise>
														</c:choose>
													</td>
													<td>${resetSugang.numOfStudent}</td>
													<td>${resetSugang.capacity}</td>
													<td class="sub--list--button--row">
														<c:choose>
															<c:when test="${resetSugang.hasConfirmed == true}">
																<form action="${pageContext.request.contextPath}/sugang/application" method="post">
																	<input type="hidden" name="subjectId" value="${resetSugang.subjectId}">
																	<input type="hidden" name="type" value="1">
																	<button type="submit" onclick="return confirm('해당 강의를 수강 취소 하시겠습니까?');" style="background-color: #a7a7a7;">수강 취소</button>
																</form>
															</c:when>
															<c:otherwise>
																<form action="${pageContext.request.contextPath}/sugang/application" method="post">
																	<input type="hidden" name="subjectId" value="${resetSugang.subjectId}">
																	<input type="hidden" name="type" value="0">
																	<input type="hidden" name="grades" value="${resetSugang.grades}">
																	<button type="submit" onclick="return confirm('해당 강의를 수강 신청 하시겠습니까?');" style="background-color: #548AC2;">수강 신청</button>
																</form>
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<br>
								<hr>
							</c:if>
							<c:if test="${not empty sugangList}">
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
										<tbody>
											<c:forEach var="sugang" items="${sugangList}">
												<tr>
													<td>${sugang.subjectId}</td>
													<td class="sub--list--name">${sugang.subjectName}</td>
													<td>${sugang.professorName}</td>
													<td>${sugang.grades}</td>
													<td>
														<c:choose>
															<c:when test="${sugang.startTime < 10}">
																${sugang.subjectDay} 0${sugang.startTime}:00~${sugang.endTime}:00&nbsp;(${sugang.roomId})
															</c:when>
															<c:otherwise>
																${sugang.subjectDay} ${sugang.startTime}:00~${sugang.endTime}:00&nbsp;(${sugang.roomId})
															</c:otherwise>
														</c:choose>
													</td>
													<td>${sugang.numOfStudent}</td>
													<td>${sugang.capacity}</td>
													<td class="sub--list--button--row">
														<form action="${pageContext.request.contextPath}/sugang/application" method="post">
															<input type="hidden" name="subjectId" value="${sugang.subjectId}">
															<input type="hidden" name="type" value="1">
															<button type="submit" onclick="return confirm('수강신청을 취소하시겠습니까?');" style="background-color: #a7a7a7;">수강 취소</button>
														</form>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</c:if>
							<c:if test="${empty sugangList}">
								<br>
								<div>
									<p class="no--list--p">수강 신청 내역이 없습니다.</p>
								</div>
							</c:if>
						</div>
						<a href="${pageContext.request.contextPath}/sugang/application"><button class="preStuSubList--button">강의 검색</button></a>
					</div>
				</c:when>
			</c:choose>
		</main>
	</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>
