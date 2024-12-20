# Github-University
교직원, 교수, 학생이 이용하는 **대학교 학사관리 사이트**입니다.

![image](https://github.com/user-attachments/assets/1b458306-1272-4f6f-8468-6f51c078bad7)

## 프로젝트 기간
2024.07.18. - 2024.07.31.(10일)


## 🙋‍♀️ 팀원 소개

| 역할         | 팀원   | 주요 작업 |
|--------------|--------|-----------|
| **팀장**     | 이윤서  | - **로그인, 아이디/비밀번호 찾기** <br> - 메인 홈페이지, My 페이지 <br> - **학사정보** (공지사항, 학사일정, 학사일정 등록) <br> - **유효성 검사** |
| **팀원**     | 강경훈  | - **직원 기능(학사관리)** <br> 새학기 적용, 휴학 처리, 수강 신청 기간 등록, 등록금 고지서 발송, <br> 학생/교수/직원 등록, 학생/교수 명단 조회, 장학금 부여 <br> - **유효성 검사**, **단방향 암호화 (Hash & Salt)**, **리팩토링** <br> - **교수 기능** (과목별 학생 리스트 조회, 성적 기입) |
| **팀원**     | 배민혁  | - **학생 기능** <br> 전체 강의 조회 (학생), 예비수강신청, 수강신청, 수강신청 내역 조회, <br> 금학기/학기별 성적 조회, 누계 성적, 휴학 신청, 휴학 신청 내역 <br> 등록금 내역, 등록금 고지서 납부 |
| **팀원**     | 박준수  | - **교수 기능** <br> 전체 강의 조회 (교수), 강의 계획서 조회 및 수정, 내 강의 조회, 내 강의 평가 <br> - **CSS** |
| **팀원**     | 김가령  | - **직원 기능(등록 관리)** <br> 단과대학, 학과, 강의실, 강의, 단대별 등록금 |

---

## 😀 프로젝트 주요 기능

1. **학생**
- MY 페이지(내 정보 조회, 비밀번호 변경, 휴학 신청, 휴학 내역 조회, 등록금 내역 조회, 등록금 납부 고지서)
- 수업(전체 강의 조회)
- 수강신청(강의 시간표 조회, 예비수강신청, 수강신청, 수강신청 내역)
- 성적(금학기 성적 조회, 학기별 성적 조회, 누계 성적)

2. **교수**
- MY 페이지(내 정보 조회, 비밀번호 변경)
- 수업(전체 강의 조회, 내 강의 조회, 내 강의 평가)

3. **직원**
- MY 페이지(내 정보 조회, 비밀번호 변경)
- 학사관리(학생/교수 명단 조회, 학생/교수/직원 등록, 등록금 고지서 발송, 휴학 처리, 수강 신청 기간 등록, 새학기 등록)
- 등록관리(단과대학, 학과, 강의실, 강의, 단대별 등록금)

4. **공통**
- 로그인, 아이디/비밀번호 찾기, 학사정보(공지사항, 학사일정, 학사일정 등록(직원만))
---

## 🗂 데이터베이스 설계

프로젝트의 데이터베이스는 MySQL을 기반으로 하였습니다. 전체적인 데이터 구조는 아래 ERD를 통해 확인할 수 있습니다.

![image](https://github.com/user-attachments/assets/58336a60-d95f-4f3e-b9eb-bfceea6188dc)

![image](https://github.com/user-attachments/assets/45cc2dec-f30e-4b1c-a4a4-dab3e29362f4)

---

## 🛠 개발 환경 및 도구 소개

### 사용 기술 스택

- **개발 툴**: Spring tool Suite 4.21.1, VS Code 1.94.0
- **데이터베이스**: MySQL
- **템플릿 엔진**: JSP
- **협업 툴**: Github, Discord
- **라이브러리**: HikariCP 5.1.0, Lombok 1.18.34, 
jakarta servlet jsp jstl 3.0.0, 
jakarta servlet jsp jstl api 3.0.0, <br>
slf4j api 2.0.0 alpha5, 
slf4j simple 2.0.0, 
mysql connector java 8.0.21

---

### 사이트맵
![image](https://github.com/user-attachments/assets/10db0d70-58a6-4a2e-bbcf-3a47ba6ffa04)

![image](https://github.com/user-attachments/assets/46fd0d10-ec17-4db9-afe1-fc7e2d0e3749)

![image](https://github.com/user-attachments/assets/28b60fd5-4eec-408e-b2c1-55feb65caeee)

![image](https://github.com/user-attachments/assets/3fbaab72-7ca8-46c6-b306-ca812832ed40)

![image](https://github.com/user-attachments/assets/9438c973-320a-4edf-9a23-098df53095c1)

---
### 주요 기능 및 화면

로그인
![image](https://github.com/user-attachments/assets/b46b699e-dd77-4ec5-9709-45c9a4cf8af5)

학생 - 전체 강의 조회
![image](https://github.com/user-attachments/assets/aa385969-4297-4e22-b14a-b196326e95b3)

학생 - 수강 신청
![image](https://github.com/user-attachments/assets/95a060ae-cc63-4fcf-8808-4236c0068a9a)

교수 - 내 강의 조회
![image](https://github.com/user-attachments/assets/ea7dd41d-a975-4ae3-ac90-4f44be3144f0)

직원 - 학사관리
![image](https://github.com/user-attachments/assets/7a9222ce-2112-4146-b264-72b54c8d7c81)

직원 - 등록 관리
![image](https://github.com/user-attachments/assets/27b30396-76b1-425b-9012-fd716f8458ad)

---

## 📊 프로젝트 PPT 자료
[Github-University.pptx](https://github.com/user-attachments/files/17643896/Github-University.pptx?raw=true)

