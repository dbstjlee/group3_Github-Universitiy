package com.tenco.group3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StuSubDetail {

	private int id;
	private int studentId;
	private int subjectId;
	private int absent;
	private int lateness;
	private int homework;
	private int midExam;
	private int finalExam;
	private int convertedMark;
	private String deptName;
	private String studentName;

}
