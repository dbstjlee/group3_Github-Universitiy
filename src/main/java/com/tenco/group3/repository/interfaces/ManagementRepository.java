package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;

public interface ManagementRepository {
	// 모든 학생 명단 호출
	List<Student> getAllStudents(int limit, int offset);

	// 검색된 학생 명단 호출
	List<Student> getAllStudents(String id, String deptId, int limit, int offset);

	// 모든 학생 수 확인
	int getTotalStudentCount();

	// 검색된 학생 수 확인
	int getTotalStudentCount(String id, String deptId);

	// 모든 교수 명단 호출
	List<Professor> getAllProfessors(int limit, int offset);

	// 검색된 교수 명단 호출
	List<Professor> getAllProfessors(String id, String deptId, int limit, int offset);

	// 모든 교수 수 확인
	int getTotalProfessorCount();

	// 검색된 교수 수 확인
	int getTotalProfessorCount(String id, String deptId);

	// 학생 등록 id 반환
	int createStudent(Student student, String pwSalt);
	
	// 교수 등록
	int createProfessor(Professor professor, String pwSalt);

	// 직원 등록
	int createStaff(Staff staff, String pwSalt);

	// 처리중인 휴학 신청이 있는지 조회
	boolean checkBreakAppDone();
	
	// 학생의 학년 학기 업데이트
	void updateGradeAndSemester(List<Student> studentList);

}
