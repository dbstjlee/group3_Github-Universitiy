package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Grade;
import com.tenco.group3.model.Student;

public interface GradeRepository {
	List<Grade> getThisSemester(int studentId, int semester, int sub_year);

	List<Grade> getSemester(int studentId, Grade grade);
	
	List<Grade> getSemesterByType(int studentId, Grade grade);

	Grade getThisSemesterGrade(int studentId, int semester, int sub_year);
	
	Grade getTotalGrade(int studentId);
	
	int getCurrentSemesterBySubject(int studentId);

	int getCurrentYearBySubject(int studentId);
}
