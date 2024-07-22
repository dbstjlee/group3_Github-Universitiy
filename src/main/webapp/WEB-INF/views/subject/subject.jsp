<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수업-전체 강의 조회</title>
<title>수업-전체 강의 조회</title>
<style>
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
</style>
</head>
<body>
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

    <div><h1>강의 목록</h1><div><h3>[총]</h3></div>
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

</body>
</html>