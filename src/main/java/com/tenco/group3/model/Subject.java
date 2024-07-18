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
public class Subject {
	private int id;
	private String name;
	private int professor_id;
	private String room_id;
	private int dept_id;
	private String type;
	private int sub_year;
	private int semester;
	private String sub_day;
	private int start_time;
	private int end_time;
	private int grades;
	private int capacity;
	private int num_of_student;
}
