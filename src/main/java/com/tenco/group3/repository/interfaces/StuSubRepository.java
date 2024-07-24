package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.RankedStudent;

public interface StuSubRepository {

	// 각 학과, 학년 별 상위 5명 선별
	List<RankedStudent> selectRankedStudent();
}
