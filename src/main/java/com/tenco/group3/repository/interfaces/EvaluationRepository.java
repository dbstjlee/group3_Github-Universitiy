package com.tenco.group3.repository.interfaces;

import com.tenco.group3.model.Evaluation;

public interface EvaluationRepository {
	int addEvaluation(Evaluation evaluation);

	boolean isEvaluation(int studentId, int subjectId);
}
