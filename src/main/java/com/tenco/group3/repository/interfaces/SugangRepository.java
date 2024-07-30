package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;

public interface SugangRepository {

	List<Sugang> getAllSubject(int limit, int offset);

	List<Sugang> getSubjectBySearch(Sugang sugang, int pageSize, int offset);

	List<Sugang> getApplicationSubject(int studentId, int limit, int offset);

	int getAllSubjectCount();

	int getSearchSubjectCount(Sugang sugang);

	List<Sugang> getApplicatedSubjectList(int studentId);

	int getSubjectGrade(int studentId);

	boolean getConfirmSubject(int studentId);

	int deleteConfirmSubject(int subjectId);

	List<Sugang> getPreApplicationSubject(int studentId, int limit, int offset);

	int deletePreConfirmSubject(int subjectId);

	int addPreEnrolment(int studentId, int subjectId);

	void resetStudentCount(int subjectId);

	List<Sugang> resultStudentCount();

	List<Sugang> getPreApplicatedSubjectList(int studentId);

	List<Sugang> getResetPreSubject(int studentId);
	int addEnrolment(int studentId, int subjectId, int grade);

	boolean getTotalGrade(int studentId);

	List<Sugang> getPreSubjectBySearch(Sugang sugang, int limit, int offset);

	List<Sugang> getAppSubjectBySearch(Sugang sugang, int limit, int offset);
}
