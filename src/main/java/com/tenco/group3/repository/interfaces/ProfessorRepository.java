package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Syllabus;

public interface ProfessorRepository {

	int updateProfessorPassword(Professor professor,String id); // 비밀번호 변경
	
	int updateProfessor(Professor professor,String id); // 교수 정보 수정
	
	List<Professor> getProfessorList (); // 교수 전체 조회
	
	Professor getProfessorById(int id); // 아이디로 교수 조회
	
	Professor getProfessorByDepartmentId (int deptId); // 과 아이디로 교수조회
	
	Professor getProfessorAmount(); // 교수 수 조회
	
	int addProfessor( Professor professor); // 교수 추가
	void updateSyllabus(Syllabus syllabus);
	
	Syllabus veiwSyllabus(int subjectId);
}