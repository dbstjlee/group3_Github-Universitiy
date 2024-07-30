package com.tenco.group3.model;

import java.sql.Date;

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
public class BreakApp {

	private int id;
	private int studentId; // 학생 ID
	private int studentGrade; // 학년
	private int fromYear; // 시작 년도
	private int fromSemester; // 시작 학기
	private int toYear; // 종료 년도
	private int toSemester; // 종료 학기
	private String type; // 휴학 종류(일반휴학 임신·출산·육아휴학 질병휴학 창업휴학 군입대휴학)
	private Date appDate; // 제출 날짜
	private String status; // 신청 상태(처리중 등)
	private String studentName; // 학생이름 <- 학생 ID 값 참조
	private String departmentName; // 학과 이름
	private String studentTel; // 학생 전화번호
	private String studentAdds; // 학생 주소
	private String collegeName; // 공대

}
