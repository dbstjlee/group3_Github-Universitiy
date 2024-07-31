<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/mainpage.css">

    <script type="text/javascript">
        function openPopup() {
            
            window.open('popup.jsp', 'popup', 'width=400,height=600','scrollbars=no','resizable=yes');
        }
    </script>
    <body2 onload="openPopup()">
    </body2>
<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">

	<div>
		
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
										<td><fmt:formatDate value="${notice.createdTime}"
												pattern="yyyy-MM-dd" /></td>
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
										<td><fmt:formatDate value="${schedule.startDay}"
												pattern="MM-dd" />&nbsp;-&nbsp;<fmt:formatDate
												value="${schedule.endDay}" pattern="MM-dd" /></td>
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
					<c:choose>
						<c:when test="${principal.userRole == 'student'}">
							<ul class="d-flex align-items-start" style="margin: 0;">
								<li style="font-weight: 600; font-size: 18px;">${student.name}님,&nbsp;환영합니다.</li>
							</ul>
							<hr style="width: 100%;">
							<table>
								<tr>
									<td>이메일</td>
									<td>${student.email}</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>${student.deptname}</td>
								</tr>
								<tr>
									<td>학기</td>
									<td>${student.grade}학년&nbsp;${student.semester}학기</td>
								</tr>
								<tr>
									<td>학적상태</td>
									<td>${student.status}</td>
								</tr>
							</table>
							<div class="profile--button--div">
								<a href="/info/student"><button>마이페이지</button></a> 
								<a href="/user/logOut"><button>로그아웃</button></a>
							</div>
						</c:when>

						<c:when test="${principal.userRole == 'professor'}">
							<ul class="d-flex align-items-start" style="margin: 0;">
								<li style="font-weight: 600; font-size: 18px;">${professor.name}님,&nbsp;환영합니다.</li>
							</ul>
							<hr style="width: 100%;">
							<table>
								<tr>
									<td>이메일</td>
									<td>${professor.email}</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>${professor.deptname}</td>
							</table>
							<div class="profile--button--div">
								<a href="/info/professor"><button>마이페이지</button></a> 
								<a href="/user/logOut"><button>로그아웃</button></a>
							</div>
						</c:when>

						<c:when test="${principal.userRole == 'staff'}">
							<ul class="d-flex align-items-start" style="margin: 0;">
								<li style="font-weight: 600; font-size: 18px;">${staff.name}님,&nbsp;환영합니다.</li>
							</ul>
							<hr style="width: 100%;">
							<table>
								<tr>
									<td>이메일</td>
									<td>${staff.email}</td>
								</tr>
								<tr>
									<td>소속</td>
									<td>교직원</td>
							</table>
							<div class="profile--button--div">
								<a href="/info/staff"><button>마이페이지</button></a> <a
									href="/user/logOut"><button>로그아웃</button></a>
							</div>
						</c:when>
					</c:choose>
				</div>
				<br>
			</div>
		</div>
	</div>
</div>
<div class="center--imgBox">
<img class="side--img" src="${pageContext.request.contextPath}/resources/images/버튼왼쪽.png">
<img class="center--img" src="${pageContext.request.contextPath}/resources/images/back1.png">
<img class="side--img" src="${pageContext.request.contextPath}/resources/images/버튼오른쪽.png">
</div>
 
    
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>