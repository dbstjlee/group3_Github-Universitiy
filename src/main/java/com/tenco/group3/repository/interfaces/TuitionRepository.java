package com.tenco.group3.repository.interfaces;

import com.tenco.group3.model.Tuition;

public interface TuitionRepository {
	Tuition getTuitionByStudentId(int studentId);
	Tuition getSummaryTuitionByStudentId(int studentId);
	
}
