<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/department.css">
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<title>학과</title>

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
                            <td><a href="${pageContext.request.contextPath}/admin/department" class="selected-menu">학과</a></td>
                        </tr>
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin/room">강의실</a></td>
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
            <h1>학과</h1>
            <div class="split-div"></div>
            <div class="select-button">
                <a href="${pageContext.request.contextPath}/admin/department?showForm=true" class="button">등록</a>
                <a href="${pageContext.request.contextPath}/admin/department?showUpdateButtons=true" class="button">수정</a>
                <a href="${pageContext.request.contextPath}/admin/department?showDeleteButtons=true" class="button">삭제</a>
            </div>
            <br>
            <!-- 학과 등록 -->
            <c:if test="${param.showForm == 'true'}">
                <form action="${pageContext.request.contextPath}/admin/addDepartment" method="post" class="department-form">
                    <div class="insert-form">
                        <div class="form-header">
                            <span class="form-title">학과 등록하기</span>
                        </div>
                        <div class="form-body">
                            <input type="text" id="departmentName" class="input-box" name="departmentName" placeholder="학과이름을 입력하세요">
                            <select name="collegeId" class="input-box">
                                <option value="1">공과대학</option>
                                <option value="2">인문대학</option>
                                <option value="3">사회과학대학</option>
                                <option value="4">상경대학</option>
                            </select>
                            <input type="submit" value="입력" class="button-add">
                        </div>
                    </div>
                </form>
            </c:if>

            <!-- 학과 수정 -->
            <c:if test="${param.showUpdateButtons == 'true'}">
                <form action="${pageContext.request.contextPath}/admin/updateDepartment" method="post" class="department-update-form">
                    <div class="insert-form">
                        <div class="form-header">
                            <span class="form-title">학과 수정하기</span>
                        </div>
                        <select name="id" class="input-box">
                            <option value="101">컴퓨터공학과</option>
                            <option value="102">전자공학과</option>
                            <option value="103">화학공학과</option>
                            <option value="104">기계공학과</option>
                            <option value="105">신소재공학과</option>
                            <option value="106">철학과</option>
                            <option value="107">국사학과</option>
                            <option value="108">언어학과</option>
                            <option value="109">국어국문학과</option>
                            <option value="110">영어영문학과</option>
                            <option value="111">심리학과</option>
                            <option value="112">정치외교학과</option>
                            <option value="113">사회복지학과</option>
                            <option value="114">언론정보학과</option>
                            <option value="115">인류학과</option>
                            <option value="116">경영학과</option>
                            <option value="117">경제학과</option>
                            <option value="118">회계학과</option>
                            <option value="119">농업경영학과</option>
                            <option value="120">무역학과</option>
                        </select>
                        <input type="text" class="input-box" name="name" placeholder="변경할 학과명을 입력하세요">
                        <input type="submit" value="수정" class="button">
                    </div>
                </form>
            </c:if>
            
            <div class="total-container">
                <table class="table-container">
                    <thead>
                        <tr class="first-tr">
                            <th>ID</th>
                            <th>학과명</th>
                            <th>단과대학ID</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="department" items="${departments}">
                            <tr>
                                <td>${department.id}</td>
                                <td>${department.name}</td>
                                <td>${department.collegeId}</td>
                                <td>
                                    <c:if test="${param.showDeleteButtons == 'true'}">
                                        <form action="${pageContext.request.contextPath}/admin/deleteDepartment" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
                                            <input type="hidden" name="id" value="${department.id}">
                                            <input type="submit" value="삭제" class="button-delete">
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
</body>
</html>
