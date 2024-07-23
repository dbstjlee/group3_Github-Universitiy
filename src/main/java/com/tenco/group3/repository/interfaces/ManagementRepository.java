package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;

public interface ManagementRepository {
	// 모든 학생 명단 호출
	List<Student> getAllStudents(int limit, int offset);

	// 모든 학생 수 확인
	int getTotalStudentCount();

	// 모든 교수 명단 호출
	List<Professor> getAllProfessors(int limit, int offset);

	// 모든 교수 수 확인
	int getTotalProfessorCount();

	// 학생 등록
	boolean createStudent(Student student);

	// 교수 등록
	boolean createProfessor(Professor professor);

	// 직원 등록
	boolean createStaff(Staff staff);

	// 학사일정 스케줄 상태 변경
	void updateSchedule(String columName, boolean state);

	// 학사일정 스케줄 상태 조회
	int getScheduleStat(String columName);

	// 처리중인 휴학 신청이 있는지 조회
	boolean checkBreakAppDone();

}
