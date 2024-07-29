package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.College;
import com.tenco.group3.model.Department;

public interface DepartmentRepository {

	List<Department> getAllDepartment(); // 학과 전체 조회
	int addDepartment(Department department); // 학과 등록
	int updateDepartment(Department department); // 학과 수정
	int deleteDepartment(int id); // 학과 삭제
	Department getDepartmentById(int id);
	List<College> getAllColleges();
}
