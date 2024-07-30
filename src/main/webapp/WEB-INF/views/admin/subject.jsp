<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/adminSubject.css">
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<title>강의</title>

<div class="sub-main">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>등록</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/college">단과대학</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/department">학과</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/room">강의실</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/subject" class="selected--menu">강의</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/tuition">단과대학별 등록금</a></td>
				</tr>
			</table>
		</div>
	</div>


	<main>
		<h1>강의</h1>
		<div class="split-div"></div>
		<div class="select-button">
			<a href="${pageContext.request.contextPath}/admin/subject?showForm=true" class="button">등록</a> <a
				href="${pageContext.request.contextPath}/admin/subject?showUpdateButtons=true" class="button">수정</a> <a
				href="${pageContext.request.contextPath}/admin/subject?showDeleteButtons=true" class="button">삭제</a>
		</div>
		<br>

		<!-- 강의 등록 -->
		<c:if test="${param.showForm == 'true'}">
			<form action="${pageContext.request.contextPath}/admin/addSubject" method="post" class="subject-form">
				<div class="insert-form">
					<div class="form-header">
                            <span class="form-title">강의 등록하기</span>
                        </div>
                        <div class="subject--form">
						<input type="text" class="input--box" id="name" name="name" placeholder="강의명을 입력하세요"><br>
						<input type="text" class="input--box" id="professorId" name="professorId" placeholder="교수ID를 입력하세요"><br>
						<input type="text" class="input--box" id="roomId" name="roomId" placeholder="강의실을 입력하세요"><br>
						<input type="text" class="input--box" id="deptId" name="deptId" placeholder="학과ID를 입력하세요"><br>
							<label for="major">전공</label> 
							<input type="radio" id="major" name="type" value="전공">
							<label for="culture">교양</label> 
							<input type="radio" id="culture" name="type" value="교양"><br>
							<input type="text" class="input--box" id="subYear" name="subYear" placeholder="연도를 입력하세요"><br>
							<input type="text" class="input--box" id="semester" name="semester" placeholder="학기를 입력하세요"><br>
								<select name="subDay" class="input--box">
									<option value="월">월</option>
									<option value="화">화</option>
									<option value="수">수</option>
									<option value="목">목</option>
									<option value="금">금</option>
								</select> 
							<input type="text" class="input--box" id="startTime" name="startTime" placeholder="시작시간을 입력하세요"><br>
							<input type="text" class="input--box" id="endTime" name="endTime" placeholder="종료시간을 입력하세요"><br>
							<input type="text" class="input--box" id="grades" name="grades" placeholder="학점을 입력하세요"><br>
							<input type="text" class="input--box" name="capacity" name="capacity" placeholder="정원 입력하세요"><br>
						<input type="submit" class="button" value="입력">
						</div>
				</div>
			</form>
		</c:if>

		<!-- 강의 수정 -->
<c:if test="${param.showUpdateButtons == 'true'}">
    <form action="${pageContext.request.contextPath}/admin/updateSubject" method="post" class="subject-update-form">
        <div class="insert-form">
            <div class="form-header">
                <span class="form-title">강의 수정하기</span>
            </div>
            <select name="id" class="input--box">
                <c:forEach var="subject" items="${subjects}">
                    <option value="${subject.id}">${subject.id}</option>
                </c:forEach>
            </select>
            <select name="subDay" class="input--box">
                <option value="월">월</option>
                <option value="화">화</option>
                <option value="수">수</option>
                <option value="목">목</option>
                <option value="금">금</option>
            </select><br>  
            <input type="text" class="input--box" id="name" name="name" placeholder="강의명을 입력하세요"><br> 
            <input type="text" class="input--box" id="roomId" name="roomId" placeholder="강의실을 입력하세요"><br>
            변경 시작시간 
            <select name="startTime" class="input--box">
                <c:forEach var="time" begin="9" end="16">
                    <option value="${time}">${time}</option>
                </c:forEach>
            </select><br> 
            변경 종료시간
            <select name="endTime" class="input--box">
                <c:forEach var="time" begin="11" end="18">
                    <option value="${time}">${time}</option>
                </c:forEach> 
            </select><br> 
            <input type="text" class="input--box" id="capacity" name="capacity" placeholder="정원을 입력하세요"><br> 
            <input type="submit" value="수정" class="button">
        </div>
    </form>
</c:if>

		<!-- 강의 조회 -->
		<div class="total-container">
			<table class="table-container">
				<thead>
					<tr class="first-tr">
						<td>ID</td>
						<td>강의명</td>
						<td>교수</td>
						<td>강의실</td>
						<td>학과ID</td>
						<td>구분</td>
						<td>연도</td>
						<td>학기</td>
						<td>시간</td>
						<td>이수학점</td>
						<td>정원</td>
						<td>신청인원</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="subject" items="${subjects}">
						<tr>
							<td>${subject.id}</td>
							<td>${subject.name}</td>
							<td>${subject.professorId}</td>
							<td>${subject.roomId}</td>
							<td>${subject.deptId}</td>
							<td>${subject.type}</td>
							<td>${subject.subYear}</td>
							<td>${subject.semester}</td>
							<td><c:choose>
									<c:when test="${subject.startTime < 10}">
										${subject.subDay} 0${subject.startTime}:00-${subject.endTime}:00							
									</c:when>
									<c:otherwise>
										${subject.subDay} ${subject.startTime}:00-${subject.endTime}:00							
									</c:otherwise>
								</c:choose></td>
							<td>${subject.grades}</td>
							<td>${subject.capacity}</td>
							<td>${subject.numOfStudent}</td>
							<td>
                                    <c:if test="${param.showDeleteButtons == 'true'}">
                                        <form action="${pageContext.request.contextPath}/admin/deleteSubject" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
                                            <input type="hidden" name="id" value="${subject.id}">
                                            <input type="submit" value="삭제" class="button">
                                        </form>
                                    </c:if>
                                </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</div>
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>