package com.tenco.group3.repository.interfaces;

import com.tenco.group3.model.College;

public interface CollegeRepository {

	int addCollege(College college);
	int deleteById(int id);
	
}
