package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.StuSubDetail;
import com.tenco.group3.model.Subject;

public interface StuSubDetailRepository {

	StuSubDetail getDetailById(int id);
	List<StuSubDetail> getAllDetailBySubjectId(int subjectId);
	void updateDetailById(StuSubDetail stuSubDetail);
	Subject getSubjectByDetailId(int id);
}
