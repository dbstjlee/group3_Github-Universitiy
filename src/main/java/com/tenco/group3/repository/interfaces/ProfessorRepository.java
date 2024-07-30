package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;

public interface ProfessorRepository {

	int addProfessor(Professor professor); // 교수 추가

	void updateSyllabus(Syllabus syllabus); // 과목 수정

	Syllabus veiwSyllabus(int subjectId); // 과목 불러오기

	List<Subject> veiwProfessorsubjectBySemesterAndYear(int professorId, int subYear, int semester);

	List<Subject> veiwProfessorsubjectByProfessorId(int professorId);
}