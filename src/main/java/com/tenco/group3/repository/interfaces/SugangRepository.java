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

}
