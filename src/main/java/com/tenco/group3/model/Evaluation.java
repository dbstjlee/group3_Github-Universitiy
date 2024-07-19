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
public class Evaluation {
	private int evaluationId;
	private int studentId;
	private int subjectId;
	private String answer1; 
	private String answer2; 
	private String answer3; 
	private String answer4; 
	private String answer5; 
	private String answer6; 
	private String answer7; 
	private String improvments; 
	 
}
