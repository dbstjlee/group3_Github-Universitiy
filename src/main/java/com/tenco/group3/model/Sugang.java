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
public class Sugang {

	private String collegeName;
	private String departName;
	private int subjectId;
	private String subjectType;
	private String subjectName;
	private String professorName;
	private int grades;
	private String subjectDay;
	private int startTime;
	private int endTime;
	private String roomId;
	private int numOfStudent;
	private int capacity;
	private int deptId;
	private int studentId;
	private boolean hasConfirmed;
	private int result;
	private int sub;
	private int sugangId;

}
