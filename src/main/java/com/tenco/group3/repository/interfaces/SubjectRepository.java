package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;

public interface SubjectRepository {
	// 강의 시간표 조회
	List<Subject> getSubjectAll(int limit, int offset);
	
	List<Subject> searchSubject (int subYear,int semester,String name,int deptId,  int limit, int offset);

	List<Subject> getSubjectByType(String type);

	List<Subject> getSubjectBySemester(int professorId, int subYear, int semester);

	List<Subject> getStudentBySubject(int id, String type);

	List<Subject> getSubjectById(int id, int year, int semester , String name);
	
	List<Subject> selectIdByLessNumOfStudent();
	int getSearchSubjectCount(int year, int semester, String name, int deptId);

	int getAllSubjectCount();
	
	Syllabus getSyllabusById(int subjectId);
	
	List<Subject> getAllSubject(); // 강의 전체 조회
	int addSubject(Subject subject); // 강의 등록
	int updateSubject(Subject subject); // 강의 수정
	int deleteSubject(int id); // 강의 삭제
	Subject getAndminSubjectById(int id);
}