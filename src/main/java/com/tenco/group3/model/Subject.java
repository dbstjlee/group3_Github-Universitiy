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
	private int professor_id; // 교수 ID
	private String room_id; // 강의실 ID
	private int dept_id; // 학과 ID
	private String type; // 전공, 교양
	private int sub_year; // 년도
	private int semester; // 학기
	private String sub_day; // 요일
	private int start_time; // 시작 시간
	private int end_time; // 종료 시간
	private int grades; // 학점
	private int capacity; // 학생 정원
	private int num_of_student; // 신청 학생수
}
