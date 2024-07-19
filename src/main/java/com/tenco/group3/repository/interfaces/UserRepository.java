package com.tenco.group3.repository.interfaces;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;

public interface UserRepository {

	User getUserById(int id);
	Student getStudentByNameAndEmail(String username, String email);
	Professor getProfessorByNameAndEmail(String username, String email);
	Staff getStaffByNameAndEmail(String username, String email);
	
}
