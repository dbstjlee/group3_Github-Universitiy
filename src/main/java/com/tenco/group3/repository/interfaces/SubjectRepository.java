package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;

public interface SubjectRepository {
	// 강의 시간표 조회
	List<Subject> getSubjectAll();
	
	List<Subject> getSubjectByType(String type);
	
	List<Subject> getSubjectBySemester(int semester);
	
	List<Subject> getStudentBySubject(int id, String type);
	
}