package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;

public interface StuSubRepository {

	// 직전 학기 각 학과, 학년 별 상위 5명 선별
	List<RankedStudent> selectRankedStudent();

	// 정원 만족된 과목 조회
	List<Subject> getAllSubjectSatisfied();

	// 해당하는 모든 과목의 예비 수강 신청 내역 삭제(수강신청으로 이관)
	void deletePreConfirmSubject(List<Subject> subjectList);

	// 과목 id로 예비 수강 신청 내역 조회
	List<Sugang> getAllPreBySubject(List<Subject> subjectList);

	// stu_sub_tb 에 insert
	void addSugang(List<Sugang> sugangList);

	// 정원 초과된 과목 학생 수 0명으로
	void updateSubjectOver();

	// 수강 신청 기간 종료시 예비 수강 신청 리스트 비우기
	void deleteAllPre();

	// stu_sub_tb에서 현재 학기에 해당하는 과목과 학생 id 받아옴
	List<Sugang> getAllSugang();
	
	// stu_sub_detail_tb에 insert
	void addStuSubDetail(List<Sugang> sugangList);
	
	void updateGrade(String grade, int id);
}
