package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;

public interface SugangRepository {

	List<Sugang> getAllSubject(int limit, int offset);

	List<Sugang> getSubjectBySearch(Sugang sugang);

	List<Sugang> getApplicationSubject(int studentId);

	int addEnrolment(int studentId, int subjectId);

	int cancelEnrolment(Sugang sugang);

	int getAllSubjectCount();

	List<Sugang> getApplicatedSubjectList(int studentId);

	int getSubjectGrade(int studentId);
}
