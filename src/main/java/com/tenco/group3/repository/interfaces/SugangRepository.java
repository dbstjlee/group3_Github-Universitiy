package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;

public interface SugangRepository {
	
	List<Subject> getAllSubject();
	
	List<Subject> getSubjectByDeptName(Subject subject);
	
	List<Subject> getSubjectByType(Subject subject);
	
	List<Subject> getSubjectBySubjectName(Subject subject);
	
	List<Subject> getApplicationSubject(int studentId);
	
	List<Subject> getApplicationSubject(int studentId, String state);
	
	void addEnrolment(int studentId, Subject subject);
}
