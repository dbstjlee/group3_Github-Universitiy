<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/collegeTuition.css">
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<title>단대별 등록금</title>

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
					<td><a href="${pageContext.request.contextPath}/admin/subject">강의</a></td>
				</tr>
				<tr>
					<td><a href="${pageContext.request.contextPath}/admin/tuition" class="selected--menu">단과대학별 등록금</a></td>
				</tr>
			</table>
		</div>
	</div>

	<main>
		<h1>단과대학별 등록금</h1>
		<div class="split-div"></div>
		<div class="select-button">
			<%-- <a href="${pageContext.request.contextPath}/admin/tuition?showForm=true" class="button">등록</a> --%> <a href="${pageContext.request.contextPath}/admin/tuition?showUpdateButtons=true"
				class="button">수정</a> <a href="${pageContext.request.contextPath}/admin/tuition?showDeleteButtons=true" class="button">삭제</a>
		</div>
		<br>

		<%-- <!-- 등록금 등록 -->
		<c:if test="${param.showForm == 'true'}">
			<form action="${pageContext.request.contextPath}/admin/addCollTuit" method="post" class="tuition-form">
				<div class="insert-form">
					<div class="form-header">
						<span class="form-title">등록금 등록하기</span>
					</div>
					<div class="form-body">
						<select name="collegeId" class="input-box">
							<c:forEach var="tuition" items="${collegeTuitions}">
								<option value="${tuition.college_id}">${tuition.college_name}</option>
							</c:forEach>
						</select> <input type="text" id="amount" class="input-box" name="amount" placeholder="등록금을 입력하세요"> <input type="submit" value="입력" class="button">
					</div>
				</div>
			</form>
		</c:if> --%>


		<!-- 단과대학별 등록금 수정 -->
<c:if test="${param.showUpdateButtons == 'true'}">
    <form action="${pageContext.request.contextPath}/admin/updateCollTuit" method="post" class="tuition-update-form">
        <div class="insert-form">
            <div class="form-header">
                <span class="form-title">등록금 수정하기</span>
            </div>
            <select name="id" class="input-box">
                <c:forEach var="tuition" items="${collegeTuitions}">
                    <option value="${tuition.college_id}">${tuition.college_name}</option>
                </c:forEach>
            </select>
            <input type="text" class="input-box" name="amount" placeholder="등록금을 입력하세요">
            <input type="submit" value="수정" class="button">
        </div>
    </form>
</c:if>




		<!-- 단과대학별 등록금 조회 -->
		<div class="total-container">
			<table class="table-container">
				<thead>
					<tr class="first-tr">
						<th>ID</th>
						<th>단과대학</th>
						<th>금액</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tuition" items="${collegeTuitions}">
						<tr>
							<td>${tuition.college_id}</td>
							<td>${tuition.college_name}</td>
							<td>${tuition.amount}</td>
							<td><c:if test="${param.showDeleteButtons == 'true'}">
									<form action="${pageContext.request.contextPath}/admin/deleteCollTuit" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
										<input type="hidden" name="id" value="${tuition.college_id}"> <input type="submit" value="삭제" class="button">
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