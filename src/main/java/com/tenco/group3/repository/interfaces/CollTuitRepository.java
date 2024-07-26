package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.CollegeTuition;

public interface CollTuitRepository {

	List<CollegeTuition> getAllColTuit(); // 단과대학 별 등록금
	int addCollegeTuition(CollegeTuition collegeTuition); // 단과대학 별 등록금 추가
	int deleteCollegeTuition(int id); // 단과대학 별 등록금 삭제
	CollegeTuition getCollegeTutionById(int id);
}
