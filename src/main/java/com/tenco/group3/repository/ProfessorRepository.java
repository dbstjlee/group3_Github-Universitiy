package com.tenco.group3.repository;

import com.tenco.group3.model.Professor;

public interface ProfessorRepository {

	Professor getProfessorById(int id);
	int updateProfessorPassword(Professor professor,String id);
	
}
