<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<style type="text/css">
* {
    margin: 0;
    padding: 0;
    font-family: 'Noto Sans KR', sans-serif;
    box-sizing: border-box;
}

body {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f8f9fa;
}

.container {
    width: 350px;
    padding: 20px;
    border: 1px solid #d0d7de;
    border-radius: 8px;
    background-color: #fff;
}

.header--top {
    width: 100%;
    height: 40px;
}

.section--header {
    text-align: center;
    margin-bottom: 20px;
}

.search--table {
    width: 100%;
}

.search--table td {
    padding: 8px 0;
}

.search--table label {
    font-size: 14px;
}

.search--table input[type="text"],
.search--table input[type="email"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #d0d7de;
    border-radius: 4px;
}

.search--table input[type="radio"] {
    margin-left: 10px;
}

.submit--button {
    width: 100%;
    padding: 10px 0;
    border: none;
    border-radius: 8px;
    color: white;
    background-color: #00b918;
    cursor: pointer;
    font-size: 16px;
    margin-top: 20px;
}

.button--container {
    text-align: center;
}
</style>
</head>
<body>
    <div class="container">
        <div class="header--top"></div>
        <section>
            <div class="section--header">
                <h2>아이디 찾기</h2>
            </div>
            <form action="/user/findId" method="post">
                <table class="search--table">
                    <colgroup>
                        <col class="col1">
                        <col class="col2">
                    </colgroup>
                    <tbody>
                        <tr>
                            <td><label for="name">이름</label></td>
                            <td><input type="text" name="name" id="name"></td>
                        </tr>
                        <tr>
                            <td><label for="email">이메일</label></td>
                            <td><input type="email" name="email" id="email"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <label for="student">학생</label> 
                                <input type="radio" name="userRole" value="student" id="student"> 
                                <label for="professor">교수</label> 
                                <input type="radio" name="userRole" value="professor" id="professor"> 
                                <label for="staff">직원</label> 
                                <input type="radio" name="userRole" value="staff" id="staff">
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="button--container">
                    <button type="submit" class="submit--button">아이디 찾기</button>
                </div>
            </form>
        </section>
    </div>
</body>
</html>
