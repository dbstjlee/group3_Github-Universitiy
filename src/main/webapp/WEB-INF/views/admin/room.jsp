<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>강의실</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/room.css">
</head>
<body>
	 <div class="sub-main">
        <div class="sub-menu">
            <div class="sub-menu-top">
                <h2>등록</h2>
            </div>
            <div class="sub-menu-mid">
                <table class="mid-table">
                    <tbody>
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin/college">단과대학</a></td>
                        </tr>
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin/department">학과</a></td>
                        </tr>
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin/room" class="selected-menu">강의실</a></td>
                        </tr>
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin/subject">강의</a></td>
                        </tr>
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin/tuition">단과대학별 등록금</a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <main>
            <h1>강의실</h1>
            <div class="split-div"></div>
            <div class="select-button">
                <a href="${pageContext.request.contextPath}/admin/addRoom" class="button">등록</a>
            </div>
            <!-- 강의실 조회 -->
            <div class="total-container">
                <table class="table-container">
                    <thead>
                        <tr class="first-tr">
                            <th>강의실</th>
                            <th>단과대학ID</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="room" items="${rooms}">
                            <tr>
                                <td>${room.id}</td>
                                <td>${room.collegeId}</td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/deleteRoom" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
                                        <input type="hidden" name="id" value="${room.id}">
                                        <input type="submit" value="삭제" class="button-delete">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <br>
            <!-- 강의실 등록 -->
            <form action="${pageContext.request.contextPath}/admin/addRoom" method="post" class="room-form">
                <div class="insert-form">
                    <div class="form-header">
                        <span class="material-symbols-outlined">강의실</span> <span class="form-title">등록하기</span>
                    </div>
                    <div class="form-body">
                        <input type="text" id="roomName" class="input-box" name="roomId" placeholder="강의실을 입력하세요">
                        <input type="text" name="collegeId" class="input-box" placeholder="단과대 번호를 입력하세요">
                        <input type="submit" value="입력" class="button-add">
                    </div>
                </div>
            </form>
        </main>
    </div>
</body>
</html>
