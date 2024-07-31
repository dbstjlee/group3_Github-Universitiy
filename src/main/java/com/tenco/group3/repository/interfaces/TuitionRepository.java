package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Tuition;

public interface TuitionRepository {
	// 등록금 내역 조회
	Tuition getTuitionByStudentId(int studentId, int year, int semester);

	// 등록금 고지서 조회
	List<Tuition> getSummaryTuitionByStudentId(int studentId, int year, int semester);

	// 등록금 발송을 위한 데이터 받아오기
	List<Tuition> getTuitions();

	// 등록금 발송 (tuition 테이블에 insert)
	int addAllTuitions(List<Tuition> tuitionList);

	// 등록금 미납 학생 모두 조회
	List<Integer> getAllStudentsNon();

	int submitTuition(int studnetId);

}
