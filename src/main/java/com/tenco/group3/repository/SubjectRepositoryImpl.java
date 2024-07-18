package com.tenco.group3.repository;

import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.interfaces.SubjectRepository;

public class SubjectRepositoryImpl implements SubjectRepository {

	@Override
	public List<Subject> getSubjectAll() {
		List<Subject> subjectList = new ArrayList<Subject>();
		String query = "  ";
		return subjectList;
	}

	@Override
	public List<Subject> getSubjectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByDeptId(int deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByDeptIDAndType(int deptId, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject subjectEnrolment(int studentId, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectEnrolmented(int studentId, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

}
