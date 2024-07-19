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
public class StuSubDetail {
	private int id;
	private int studentId;
	private int subjectId;
	private int absent;
	private int lateness;
	private int homework;
	private int midExaml;
	private int finalExaml;
	private int convertedMark;
	
}
