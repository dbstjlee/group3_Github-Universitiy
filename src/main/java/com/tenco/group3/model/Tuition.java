package com.tenco.group3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tuition {
	private int studentId;
	private String studentName;
	private String deptName;
	private String collgeName;
	private int collTution;
	private String scholarType; // 변경된 부분
	private int scholar;
	private int totalTuition;
}