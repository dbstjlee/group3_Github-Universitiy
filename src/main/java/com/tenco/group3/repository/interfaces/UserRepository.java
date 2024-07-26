package com.tenco.group3.repository.interfaces;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;

public interface UserRepository {

	User getUserById(int id); // ID로 로그인
	
	User getStudentByNameAndEmail(String username, String email); // 이름과 이메일로 학생 id 조회
	
	User getProfessorByNameAndEmail(String username, String email); // 이름과 이메일로 교수 id 조회
	
	User getStaffByNameAndEmail(String username, String email); // 이름과 이메일로 직원 id 조회
	
	Student getStudentInfo(int id); // 학생 정보 조회
	
	Professor getProfessorInfo(int id); // 교수 정보 조회
	
	Staff getStaffInfo(int id); // 직원 정보 조회
	
	void getStudentInfoUpdate(Student student); // 학생 정보 수정
	
	void getProfessorInfoUpdate(Professor professor); // 교수 정보 수정
	
	void getStaffInfoUpdate(Staff staff); // 직원 정보 수정
	
	Student getStudentInfoMain(int id); // 학생 프로필 정보 조회
	
}
