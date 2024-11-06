# Github-University
교직원, 교수, 학생이 각각의 기능을 가진 **대학교 학사관리 사이트**입니다.

![image](https://github.com/user-attachments/assets/1b458306-1272-4f6f-8468-6f51c078bad7)

## 프로젝트 기간
2024.07.18. - 2024.07.31.(10일)


## 🙋‍♀️ 팀원 소개

| 역할         | 팀원   | 주요 작업 |
|--------------|--------|-----------|
| **팀장**     | 이윤서  | **로그인, 아이디/비밀번호 찾기**, 메인 홈페이지, My 페이지, 학사정보(공지사항 CRUD, 학사일정, 학사일정 등록), 유효성 검사 |
| **팀원**     | 강경훈  | **직원 기능 - 학사관리**(새학기 적용, 휴학 처리, 수강 신청 기간 등록, 등록금 고지서 발송, 학생/교수/직원 등록, 학생/교수 명단 조회, 장학금 부여), 유효성 검사, 단방향 암호화(Hash & Salt), 리펙토링, 교수 기능(과목별 학생 리스트 조회, 성적 기입) |
| **팀원**     | 배민혁  | **학생 기능** - 전체 강의 조회(학생), 예비수강신청, 수강신청, 수강신청 내역 조회, 금학기/학기별 성적 조회, 누계 성적, 휴학 신청, 휴학 신청 내역, 등록금 내역, 등록금 고지서 납부|
| **팀원**     | 박준수  | **교수 기능** - 전체 강의 조회(교수), 강의 계획서 조회 및 수정, 내 강의 조회, 내 강의 평가, CSS |
| **팀원**     | 김가령  | **직원 기능 - 등록 관리**(단과대학, 학고, 강의실, 강의, 단대별 등록금) |

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

- **백엔드 프레임워크**: Spring Boot
- **개발 툴**: Eclipse, VS Code
- **데이터베이스**: MySQL
- **템플릿 엔진**: JSP
- **협업 툴**: Github, Discord
- **라이브러리**: HiKariCP, Lombok, SLF4J, jakarta

---

### 사이트맵
![image](https://github.com/user-attachments/assets/10db0d70-58a6-4a2e-bbcf-3a47ba6ffa04)

![image](https://github.com/user-attachments/assets/46fd0d10-ec17-4db9-afe1-fc7e2d0e3749)

![image](https://github.com/user-attachments/assets/28b60fd5-4eec-408e-b2c1-55feb65caeee)

![image](https://github.com/user-attachments/assets/3fbaab72-7ca8-46c6-b306-ca812832ed40)

![image](https://github.com/user-attachments/assets/9438c973-320a-4edf-9a23-098df53095c1)


---

## 📊 프로젝트 주요 링크
[3조 프로젝트.pptx](https://github.com/user-attachments/files/17643797/3.pptx?raw=true)

