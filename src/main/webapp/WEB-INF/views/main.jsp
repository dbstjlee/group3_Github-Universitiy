<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainpage.css">


<h2>메인 페이지</h2>

<div class="">
	<div>
		<img src="${pageContext.request.contextPath}/resources/images/university.jpg" class="main--page--img">
	</div>
	<div class="justify--containers">
		<div class="main--page--div">
			<div class="main--page--notice">
				<h3>
					<a href="${pageContext.request.contextPath}/notice">공지사항</a>
				</h3>
				<div class="main--page--split"></div>
				<table>
					<tbody>
						<tr>
							<td><a href="${pageContext.request.contextPath}/notice/read?id=6">공지사항 제목 6번</a></td>
							<td>날짜</td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/notice/read?id=5">공지사항 제목 5번</a></td>
							<td>날짜</td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/notice/read?id=4">공지사항 제목 4번</a></td>
							<td>날짜</td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/notice/read?id=3">공지사항 제목 3번</a></td>
							<td>날짜</td>
						</tr>
						<tr>
							<td><a href="${pageContext.request.contextPath}/notice/read?id=2">공지사항 제목 2번</a></td>
							<td>날짜</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="main--page--calandar" style="width: 500px;">
				<h3>
					<a href="${pageContext.request.contextPath}/shedule">학사일정</a>
				</h3>
				<div class="main--page--split"></div>
				<table>
					<tbody>
						<tr>
							<td>날짜&nbsp;-&nbsp;날짜</td>
							<td>일정1</td>
						</tr>
						<tr>
							<td>날짜&nbsp;-&nbsp;날짜</td>
							<td>일정2</td>
						</tr>
						<tr>
							<td>날짜&nbsp;-&nbsp;날짜</td>
							<td>일정3</td>
						</tr>
						<tr>
							<td>날짜&nbsp;-&nbsp;날짜</td>
							<td>일정4</td>
						</tr>
						<tr>
							<td>날짜&nbsp;-&nbsp;날짜</td>
							<td>일정5</td>
						</tr>
						<tr>
							<td>날짜&nbsp;-&nbsp;날짜</td>
							<td>일정6</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 프로필 -->
		<div class="main--page--profile" style="width: 240px;">
			<ul class="">
				<li>아이콘</li>
				<li style="font-weight: 600; font-size: 18px;">${principal.username}님,&nbsp;환영합니다.</li>
			</ul>
			<table>
				<tbody>
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
				</tbody>
			</table>
			<div class="profile--button--div">
				<a href="${pageContext.request.contextPath}/info/student"><button>마이페이지</button></a> 
				<a href="${pageContext.request.contextPath}/user/logOut"><button>로그아웃</button></a>
			</div>
		</div>
	</div>
</div>
<!-- TODO - footer -->
</body>
</html>