<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<!-- 메뉴 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">
	<div class="sub--menu">
		<div class="sub--menu--top">
			<h2>학사정보</h2>
		</div>
		<div class="sub--menu--mid">
			<table class="sub--menu--table" border="1">
				<tbody>
					<tr>
						<td><a href="/notice" class="selected--menu">공지사항</a></td>
					</tr>
					<tr>
						<td><a href="/schedule">학사일정</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 메인 -->
	<main>
		<h1>공지사항</h1>
		<div class="split--div"></div>
		<form action="/notice/search" method="get" class="form--container">
			<select class="input--box" name="type">
				<option value="title">제목</option>
				<option value="keyword">제목+내용</option>
			</select>
			<input type="text" name="keyword" class="input--box" placeholder="검색어를 입력하세요">
			<input type="submit" class="button" value="검색">
		
		</form>
		<table class="table">
			<tbody>
				<tr class="first--tr">
					<td>번호</td>
					<td>말머리</td>
					<td>제목</td>
					<td>작성일</td>
					<td>조회수</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=6';">
					<td>6</td>
					<td>[장학]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=6';">
					<td>6</td>
					<td>[장학]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=5';">
					<td>5</td>
					<td>[장학]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=4';">
					<td>4</td>
					<td>[일반]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=3';">
					<td>3</td>
					<td>[일반]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=2';">
					<td>2</td>
					<td>[장학]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
				<tr class="second--tr" onclick="location.href='/notice/read?id=1';">
					<td>1</td>
					<td>[학사]</td>
					<td>내용</td>
					<td>날짜 시간</td>
					<td>0</td>
				</tr>
			</tbody>
		</table>
		<div class="paging--container">
			<a href="notice/list/1">1</a>
		</div>

	</main>
</div>
</body>
</html>