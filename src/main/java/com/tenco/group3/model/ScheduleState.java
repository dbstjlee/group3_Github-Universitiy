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
	
	public static final int FALSE = 0;
	public static final int TRUE = 1;
	public static final int END = 2;
	
	private int breakApp;
	private int sugang;
	private int tuition;
	private int year;
	private int semester;

}
