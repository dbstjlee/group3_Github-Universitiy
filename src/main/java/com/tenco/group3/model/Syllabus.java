package com.tenco.group3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Syllabus {

	
    private int subjectId;
    private String name;
    private int semester;
    private int grades;
    private String type;
    private int subYear;
    private String subDay;
    private int startTime;
    private int endTime;
    private String roomId;
    private String collName;
    private String professorName;
    private String deptName;
    private String tel;
    private String email;
    private String overview;
    private String objective;
    private String textbook;
    private String program;

	
	
	
	
}