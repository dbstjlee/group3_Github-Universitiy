package com.tenco.group3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO 물어보고 삭제 예정
public class MyEvaluation {

	private Integer professorId;
	private String name;
	private Integer answer1;
	private Integer answer2;
	private Integer answer3;
	private Integer answer4;
	private Integer answer5;
	private Integer answer6;
	private Integer answer7;
	private String improvements;
	
	public String answerSum() {
		 double answerSum = (double)(answer1 + answer2 + answer3 + answer4 + answer5 + answer6 + answer7) / 7;
		 String result = String.format("%.2f", answerSum);
	 return result;
	}
}
