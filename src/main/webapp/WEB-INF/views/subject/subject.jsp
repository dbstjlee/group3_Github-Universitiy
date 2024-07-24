<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/subject.css">
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<title>수업-전체 강의 조회</title>

</head>
<body>

<div class="d-flex justify-content-center align-items-start"
	style="min-width: 100em;">
	
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>수업</h2>
		</div>

		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tr>
					<td><a href="/subject/allSubject" class="selected--menu">전체 강의 조회</a></td>
				</tr>
				
					<tr>
						<td><a href="/professor/subject">내 강의 조회</a></td>
					</tr>
				
				
					<tr>
						<td><a href="/evaluation/read"> 내 강의 평가</a></td>
					</tr>
				
			</table>
		</div>
	</div>

	
	<main>
		<h1>전체 강의 조회</h1>
		<div class="split--div"></div>


	
		<div class="sub--filter">
			<form action="/subject/list/search" method="get">
				<div>
				
					<label for="subYear">연도 </label> <input type="number"
						value="2023" name="subYear" id="subYear"
						min="2005" max="2033">
			
					<label for="subSemester">학기 </label> <select name="semester"
						id="subSemester">
						<option value="1">1학기</option>
						<option value="2">2학기</option>
					</select>
				
					<label for="deptId">개설학과</label>
					 <select name="deptId" id="deptId">
						<option value="-1">전체</option>
						
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
					
					<label for="subName">강의명</label> <input type="text" name="name"
						list="subName">
					<datalist id="subName">
						
							<option value="데이터통신">
						
							<option value="딥러닝의 기초">
						
							<option value="컴퓨터의 개념 및 실습">
						
							<option value="컴퓨터 프로그래밍">
						
							<option value="공학설계 입문">
						
							<option value="반도체 공학">
						
							<option value="융합전자연구">
						
							<option value="기초 전기실험">
						
							<option value="물리화학">
						
							<option value="반응공학">
						
							<option value="사고와 표현">
						
							<option value="과학과 기술">
						
							<option value="고체역학">
						
							<option value="자유정의진리">
						
							<option value="정보적 사고">
						
							<option value="CAD기초">
						
							<option value="에너지재료">
						
							<option value="나노재료합성">
						
							<option value="신소재공학개론">
						
							<option value="신소재기초실습">
						
							<option value="칸트철학">
						
							<option value="불교철학사">
						
							<option value="대륙합리론">
						
							<option value="심리철학">
						
							<option value="역사학개론">
						
							<option value="동아시아사">
						
							<option value="한국근대사">
						
							<option value="한국사입문">
						
							<option value="의미론">
						
							<option value="형태론">
						
							<option value="컴퓨터언어학">
						
							<option value="이태리어">
						
							<option value="고전문학연습">
						
							<option value="국어정서법">
						
							<option value="한국현대작가론">
						
							<option value="국문학개론">
						
							<option value="중세영문학">
						
							<option value="영어발달사">
						
							<option value="현대영국소설론">
						
							<option value="영문학입문">
						
							<option value="일반심리학">
						
							<option value="적응심리학">
						
							<option value="성격심리학">
						
							<option value="인지심리학">
						
							<option value="비교정치론">
						
							<option value="외교정책론">
						
							<option value="국제정치경제론">
						
							<option value="한국정치론">
						
							<option value="현대사회심리">
						
							<option value="인간행동과 사회환경">
						
							<option value="사회복지학개론">
						
							<option value="사회복지행정론">
						
							<option value="언론정보학개론">
						
							<option value="방송의이해">
						
							<option value="광고의이해">
						
							<option value="한국언론사">
						
							<option value="문화인류학">
						
							<option value="세계화와 다문화주의">
						
							<option value="의료인류학">
						
							<option value="도시와문화">
						
							<option value="기업경영의이해">
						
							<option value="경영학원론">
						
							<option value="마케팅의 이해">
						
							<option value="마케팅 조사론">
						
							<option value="경제학원론">
						
							<option value="미시경제학">
						
							<option value="거시경제학">
						
							<option value="신자유주의 경제학">
						
							<option value="재무회계">
						
							<option value="회계감사">
						
							<option value="원가회계">
						
							<option value="관리회계">
						
							<option value="농업생산경제학">
						
							<option value="농산물 가격분석">
						
							<option value="농산물 유통학">
						
							<option value="농업 정책론">
						
							<option value="무역상무론">
						
							<option value="국제경영학">
						
							<option value="국제무역론 입문">
						
							<option value="한국무역법">
						
					</datalist>
					
					<button type="submit">
						<ul class="d-flex justify-content-center" style="margin: 0;">
							<li style="height: 24px; margin-right: 2px;">조회
							<li style="height: 24px;"><span
								class="material-symbols-outlined"
								style="font-size: 18px; padding-top: 4px;">search</span>
						</ul>
					</button>
				</div>
			</form>
		</div>
    <c:choose>
<c:when test="${subjectList.isEmpty() == false}">
				<h4>
					<span style="font-weight: 600;">강의 목록</span>&nbsp; <span style="color: gray; font-size: 18px;">[총 80건]</span>
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
                                        <li style="height: 24px;"><a href="/subject/syllabus/${subject.id}" onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;">조회</a>
                                        <li style="height: 24px;"><a href="/subject/syllabus/${subject.id}" onclick="window.open(this.href, '_blank', 'width=1000, height=1000'); return false;"><span class="material-symbols-outlined">content_paste_search</span></a>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
            
                        
                    </tbody>
                    </table>
                    <c:if test="${pageCount != null}">
                        <ul class="page--list">
                            <c:forEach var="i" begin="1" end="${pageCount}" step="1">
                                <c:choose>
                                    <c:when test="${i == page}">
                                        <li><a href="/subject/list/${i}" style="font-weight: 700; color: #007bff">${i}</a>									
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="/subject/list/${i}">${i}</a>									
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </c:if>
    </c:when>
            <c:otherwise>
                <p class="no--list--p">검색 결과가 없습니다.</p>
            </c:otherwise>
    </c:choose>
		
	</main>
</div>
