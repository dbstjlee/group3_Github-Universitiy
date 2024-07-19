package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Professor;

public interface ProfessorRepository {

	int updateProfessorPassword(Professor professor,String id);
	int updateProfessor(Professor professor,String id);
	List<Professor> getProfessorList ();
	Professor getProfessorById(int id);
	Professor getProfessorByDepartmentId (int deptId);
	Professor getProfessorAmount();
}
