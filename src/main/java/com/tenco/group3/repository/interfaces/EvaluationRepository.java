package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Evaluation;
import com.tenco.group3.model.Subject;

public interface EvaluationRepository {
	int addEvaluation(Evaluation evaluation);

	boolean isEvaluation(int studentId, int subjectId);
	
	List<Subject> getAllSubjectEvaluation(int professorId);
	
	List<Evaluation> getAllEvaluation(int professorId);
	
	List<Evaluation> getAllEvaluationBySubjectId(int professorId, int subjectId);
}
