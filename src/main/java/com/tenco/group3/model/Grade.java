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
public class Grade {
	private int sub_year;
	private int semester;
	private int subjectId;
	private String subjectName; // 과목 이름
	private String professorName; // 교수 이름
	private String type; // 전공, 교양, 전체
	private String grade; // 학점
	private int grades; // 이수 학점
	private int evaluationId;
	private double sumGrades; // 전체 학점
	private double myGrades; // 전체 이수 학점
	private double average; // 평균 
}
