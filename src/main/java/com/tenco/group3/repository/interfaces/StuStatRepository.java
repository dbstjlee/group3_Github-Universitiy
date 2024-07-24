package com.tenco.group3.repository.interfaces;

import java.util.List;

public interface StuStatRepository {
	
	// 학생 id 리스트를 받아서 상태를 모두 원하는 상태로 변경
	int updateStatusById(List<Integer> idList, String status);
	
}
