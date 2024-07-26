package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Student;

public interface StuStatRepository {
	
	// 학생 id 리스트를 받아서 상태를 모두 원하는 상태로 변경
	int updateStatusById(List<Integer> idList, String status);
	
	// 재학 중인 학생의 현재 학년, 학기를 조회
	List<Student> getCurrentGrade();
}
