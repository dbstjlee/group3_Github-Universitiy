package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;

public interface SugangRepository {

	List<Sugang> getAllSubject(int page, int offset);

	List<Sugang> getSubjectByDeptName(Sugang sugang, int page, int offset);

	List<Sugang> getSubjectByType(Sugang sugang, int page, int offset);

	List<Sugang> getSubjectBySubjectName(Sugang sugang, int page, int offset);

	List<Sugang> getApplicationSubject(int studentId);

	int addEnrolment(int studentId, Sugang sugang);

	int cancelEnrolment(Sugang sugang);
}
