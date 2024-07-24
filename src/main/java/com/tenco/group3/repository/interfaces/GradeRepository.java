package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Grade;
import com.tenco.group3.model.Student;

public interface GradeRepository {
	List<Grade> getThisSemester(int studentId, int semester, int sub_year);

	List<Grade> getSemester(int studentId, Grade grade);
	
	List<Grade> getSemesterByType(int studentId, Grade grade);

	Grade getTotalGrade(int studentId);
	
	Grade getSemester(int studentId);
	
	Grade getSubYear(int studentId);
	
	Student getStudentInfo(int studentId);
}
