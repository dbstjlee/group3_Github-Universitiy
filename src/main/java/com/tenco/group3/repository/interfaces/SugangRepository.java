package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;

public interface SugangRepository {

	List<Sugang> getAllSubject(int limit, int offset, int year, int semester);

	List<Sugang> getSubjectBySearch(Sugang sugang, int pageSize, int offset, int year, int semester);

	List<Sugang> getApplicationSubject(int studentId, int limit, int offset, int year, int semester);

	int getSearchSubjectCount(Sugang sugang, int year, int semester);

	List<Sugang> getApplicatedSubjectList(int studentId, int year, int semester);

	int getSubjectGrade(int studentId, int year, int semester);

	int getPreSubjectGrade(int studentId, int year, int semester);

	boolean getConfirmSubject(int studentId, int year, int semester);

	int deleteConfirmSubject(int subjectId);

	List<Sugang> getPreApplicationSubject(int studentId, int limit, int offset, int year, int semester);

	int deletePreConfirmSubject(int subjectId);

	int addPreEnrolment(int studentId, int subjectId);

	void resetStudentCount(int subjectId);

	List<Sugang> resultStudentCount();

	List<Sugang> getPreApplicatedSubjectList(int studentId, int year, int semester);

	List<Sugang> getResetPreSubject(int studentId, int year, int semester);

	int addEnrolment(int studentId, int subjectId, int grade);

	List<Sugang> getPreSubjectBySearch(Sugang sugang, int limit, int offset, int year, int semester);

	List<Sugang> getAppSubjectBySearch(Sugang sugang, int limit, int offset, int year, int semester);

	int isTotalGradeWithinLimit(int studentId);

	int isPreTotalGradeWithinLimit(int studentId, int year, int semester);

	int submitPreToEnrolment(int studentId, int subjectId);

	boolean isBreakedApp(int studentId);

	boolean isWeeding(int studentId);

	int getAllSubjectCount(int year, int semester);

	;
}
