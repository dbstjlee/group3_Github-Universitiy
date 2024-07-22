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
	private int collAmount;
	private int scholarType;
	private int scholarAmount;
	private int totalAmount;
	private int status;
	private int year;
	private int semester;
}