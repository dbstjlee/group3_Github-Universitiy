package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;

public interface SubjectRepository {
	// 강의 시간표 조회
	List<Subject> getSubjectAll();
	
	List<Subject> getSubjectByType(String type);
	
	List<Subject> getSubjectByDeptId(int deptId);
	
	List<Subject> getSubjectByName(String name);
	
	List<Subject> getSubjectByDeptIDAndType(int deptId, String type);
	
	// 수강 신청
	Subject subjectEnrolment(int studentId, Subject subject);
	
	// 수강 신청 내역 조회
	List<Subject> getSubjectEnrolmented(int studentId, Subject subject);
}