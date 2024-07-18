package com.tenco.group3.repository;

import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.repository.interfaces.ManagementRepository;

public class ManagementRepositoryImpl implements ManagementRepository {

	@Override
	public List<Student> getAllStudents(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Professor> getAllProfessors(int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalStudentCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getTotalProfessorCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int createStudent(Student student) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createProfessor(Professor professor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createStaff(Staff staff) {
		// TODO Auto-generated method stub
		return 0;
	}


}
