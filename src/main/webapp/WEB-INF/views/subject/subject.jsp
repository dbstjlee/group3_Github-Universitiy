<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수업-전체 강의 조회</title>
<style>
    body{
        display: inline;
        
    
    }
    .top---title{
        border-bottom: 1px solid gray;
    }
	.sub_choice{
		background-color: #F0F0F0;
        padding:13px 13px 7px 10px ;
	}
    .subYear{
        margin: 0px 10px 0px 0px;
        padding: 0px 0px 0px 3px;
        margin-right: 10px;
        border-radius: 5px;
        border-width: 1px;
    }
    .page--list{

    }
</style>
</head>
<body>

    <div class="top---title"><h1>전체 강의조회</h1></div>
	<div class=" sub_choice">
		<label for="subYear">연도</label>
        <input class="subYear" name="subYear" id= "subYear" type="number" value="2024">
		<label for="subYear">학기</label>
        <input class="subYear" name="subYear" id= "subYear" type="number" value="2024">
		<label for="deptId">개설학과</label>
        <select  name="deptId" id= "deptId">
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
		<label for="subYear">강의명</label>
        <input class="subYear" name="subYear" id= "subYear" type="number" value="2024">
	</div>

    <c:choose>
    <c:when test="${subjectList.isEmpty() == false}">
    <div><h3>강의 목록</h3><div><h4>[총]</h4></div>
    </div>
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
</body>
</html>