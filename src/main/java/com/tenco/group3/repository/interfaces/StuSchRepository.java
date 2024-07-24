package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.RankedStudent;

public interface StuSchRepository {
	
	// 학생별 장학금 유형 등록
	void insertStuSch(List<RankedStudent> rankedStudentList);
	
}
