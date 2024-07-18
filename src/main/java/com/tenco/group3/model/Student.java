package com.tenco.group3.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Student {
	private int id;
	private String name;
	private Date birth_date;
	private String gender;
	private String address;
	private String tel;
	private String email;
	private int dept_id;
	private int grade;
	private int semester;
	private Date entrance_date;
	private Date graduation_date;
}
