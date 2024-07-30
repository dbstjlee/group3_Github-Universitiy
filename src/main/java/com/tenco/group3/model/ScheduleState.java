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
public class ScheduleState {

	public static final int FALSE = 0; // 아직 학사일정 기간이 오지않음
	public static final int TRUE = 1; // 학사일정 진행 중
	public static final int END = 2; // 이번 학기 해당 학사일정 기간 종료
	public static final int PRE = 3; // 예비 수강신청 기간

	private int breakApp;
	private int sugang;
	private int tuition;
	private int year;
	private int semester;

}
