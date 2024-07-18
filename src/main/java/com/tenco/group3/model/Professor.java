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
public class Professor {
	private int id;
	private String name;
	private Date birthdate;
	private String gender;
	private String address;
	private String email;
	private int deptId;
	private Date hireDate;
		
}
