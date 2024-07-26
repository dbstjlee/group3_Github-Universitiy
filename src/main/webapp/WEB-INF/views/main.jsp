<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainpage.css">

<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">

	<div>
		<img src="${pageContext.request.contextPath}/resources/images/university.jpg" class="main--page--img">
		<div class="d-flex justify-content-center align-items-start">
			<div class="main--page--div">
				<div class="d-flex">
					<!-- 공지사항 -->
					<div class="main--page--notice">
						<h3>
							<a href="/notice/list">공지사항</a>
						</h3>
						<div class="main--page--split"></div>
						<c:forEach var="notice" items="${noticeList}">
							<table>
								<tbody>
									<tr>
										<td><a href="/notice/read?id=${notice.id}">${notice.category}&nbsp;${notice.title}</a></td>
										<td><fmt:formatDate value="${notice.createdTime}" pattern="yyyy-MM-dd" /></td>
									</tr>
								</tbody>
							</table>
						</c:forEach>
					</div>

					<!-- 학사일정 -->
					<div class="main--page--calander">
						<h3>
							<a href="/schedule/list">학사일정</a>
						</h3>
						<div class="main--page--split"></div>
						<c:forEach var="schedule" items="${scheduleList}">
							<table>
								<tbody>
									<tr>
										<td><fmt:formatDate value="${schedule.startDay}" pattern="MM-dd" />&nbsp;-&nbsp;<fmt:formatDate value="${schedule.endDay}" pattern="MM-dd" /></td>
										<td>${schedule.information}</td>
									</tr>
								</tbody>
							</table>
						</c:forEach>
					</div>
				</div>
			</div>

			<div>
				<!-- 사용자 프로필 -->
				<div class="main--page--profile">
					<ul class="d-flex align-items-start" style="margin: 0;">
						<li style="font-weight: 600; font-size: 18px;">${studentInfo.name}님,&nbsp;환영합니다.</li>
					</ul>
					<hr style="width: 100%;">
					<c:choose>
						<c:when test="">
							<table>
								<tr>
									<td>이메일</td>
									<td>psw@green.com</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>컴퓨터공학과</td>
								</tr>
								<tr>
									<td>학기</td>
									<td>1학년&nbsp;1학기</td>
								</tr>
								<tr>
									<td>학적상태</td>
									<td>재학</td>
								</tr>
							</table>
						</c:when>
						
						<c:when test="">
							<table>
								<tr>
									<td>이메일</td>
									<td>psw@green.com</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>컴퓨터공학과</td>
							</table>
						</c:when>
						
						<c:when test="">
							<table>
								<tr>
									<td>이메일</td>
									<td>psw@green.com</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>컴퓨터공학과</td>
							</table>
						</c:when>
					</c:choose>
					<div class="profile--button--div">
						<a href="/info/student"><button>마이페이지</button></a> <a href="/user/logOut"><button>로그아웃</button></a>
					</div>
				</div>
				<br>
			</div>
		</div>
	</div>
</div>
</body>
</html>