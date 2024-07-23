package com.tenco.group3.repository.interfaces;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;

public interface UserRepository {

	User getUserById(int id); // 유저 아이디 조회
	
	User getStudentByNameAndEmail(String username, String email); // 이름과 이메일로 학생 id 조회
	
	User getProfessorByNameAndEmail(String username, String email); // 이름과 이메일로 교수 id 조회
	
	User getStaffByNameAndEmail(String username, String email); // 이름과 이메일로 직원 id 조회
	
}
