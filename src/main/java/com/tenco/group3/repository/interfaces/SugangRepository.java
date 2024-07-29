package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;

public interface SugangRepository {

	List<Sugang> getAllSubject(int limit, int offset);

	List<Sugang> getSubjectBySearch(Sugang sugang);

	List<Sugang> getApplicationSubject(int studentId, int limit, int offset);

	int addEnrolment(int studentId, int subjectId);

	int getAllSubjectCount();

	int getSearchSubjectCount(Sugang sugang);

	List<Sugang> getApplicatedSubjectList(int studentId);

	int getSubjectGrade(int studentId);

	boolean getConfirmSubject(int studentId);

	int deleteConfirmSubject(int subjectId);

	List<Sugang> getPreApplicationSubject(int studentId, int limit, int offset);

	int deletePreConfirmSubject(int subjectId);

	List<Sugang> getPreSubjectBySearch(Sugang sugang);

	List<Sugang> getAppSubjectBySearch(Sugang sugang);

	int addPreEnrolment(int studentId, int subjectId);

	void resetStudentCount(int subjectId);

	List<Sugang> resultStudentCount();

	List<Sugang> getPreApplicatedSubjectList(int studentId);

	List<Sugang> getResetPreSubject(int studentId);

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
}
