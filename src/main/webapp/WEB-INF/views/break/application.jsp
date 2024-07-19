<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴학 신청 화면</title>
</head>
<body>
	<div>
		<form action="">
			<div>
				<h3>휴학 신청서</h3>
				<table border="1">
					<tbody>
						<tr>
							<th>단과대</th>
							<td>공과대학</td>
							<th>학과</th>
							<td>컴퓨터공학과</td>
						</tr>
						<tr>
							<th>학번</th>
							<td>2023000001</td>
							<th>학년</th>
							<td>1학년</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>010-5267-1815</td>
							<th>성명</th>
							<td>박시우</td>
						</tr>
						<tr>
							<th>주소</th>
							<td>부산시 남구</td>
						</tr>
						<tr>
							<th>기간</th>
							<td colspan="3">
								<select>
									<option>2024</option>
									<option>2025</option>
									<option>2026</option>
								</select>
								년도
								<select>
									<option>1</option>
									<option>2</option>
								</select>
								학기
							</td>
						</tr>
						<tr>
							<th>휴학 구분</th>
							<td>
								<input type="radio">
								<label>일반 휴학</label>
								<input type="radio">
								<label>임신·출산·육아휴학</label>
								<input type="radio">
								<label>질병휴학</label>
								<input type="radio">
								<label>창업휴학</label>
								<input type="radio">
								<label>군입대 휴학</label>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</body>
</html>