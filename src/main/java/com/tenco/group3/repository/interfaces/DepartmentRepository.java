package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Department;

public interface DepartmentRepository {

	int addDeaprtment(Department department);
	Department selectedById(int id);
	int deleteById(int id);
	int updateDepartment(Department department);
	
}
