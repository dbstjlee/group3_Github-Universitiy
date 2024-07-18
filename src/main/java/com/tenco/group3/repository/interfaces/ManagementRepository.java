package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;

public interface ManagementRepository {
	// 모든 학생 명단 호출
	List<Student> getAllStudents(int limit, int offset);
	
	// 모든 교수 명단 호출
	List<Professor> getAllProfessors(int limit, int offset);
	
	// 학생 등록
	int createStudent(Student student);
	
	// 교수 등록
	int createProfessor(Professor professor);
	
	// 직원 등록
	int createStaff(Staff staff);
}
