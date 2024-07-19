package com.tenco.group3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Subject {
	private int id;
	private String name; // 수업 명
	private int professorId; // 교수 ID
	private String roomId; // 강의실 ID
	private int deptId; // 학과 ID
	private String type; // 전공, 교양
	private int subYear; // 년도
	private int semester; // 학기
	private String subDay; // 요일
	private int startTime; // 시작 시간
	private int endTime; // 종료 시간
	private int grades; // 학점
	private int capacity; // 학생 정원
	private int numOfStudent; // 신청 학생수
<<<<<<< HEAD
=======
<<<<<<< HEAD
	private String professorName;
	private String deptName;
=======
>>>>>>> dev
>>>>>>> MH
}
