package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.Evaluation;
import com.tenco.group3.repository.interfaces.EvaluationRepository;
import com.tenco.group3.util.DBUtil;

public class EvaluationRepositoryImpl implements EvaluationRepository {

	private static final String INSERT_EVALUATION_SQL = " INSERT INTO evaluation_tb "
			+ " (student_id, subject_id, answer1, answer2, answer3, "
			+ " answer4, answer5, answer6, answer7, improvements) VALUES (?,?,?,?,?,?,?,?,?,?) ";

	private static final String IS_EVALUATION_SQL = " SELECT * " + " FROM evaluation_tb "
			+ " WHERE student_id = ? AND subject_id = ? ";

	@Override
	public int addEvaluation(Evaluation evaluation) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_EVALUATION_SQL)) {
				pstmt.setInt(1, evaluation.getStudentId());
				pstmt.setInt(2, evaluation.getSubjectId());
				pstmt.setInt(3, evaluation.getAnswer1());
				pstmt.setInt(4, evaluation.getAnswer2());
				pstmt.setInt(5, evaluation.getAnswer3());
				pstmt.setInt(6, evaluation.getAnswer4());
				pstmt.setInt(7, evaluation.getAnswer5());
				pstmt.setInt(8, evaluation.getAnswer6());
				pstmt.setInt(9, evaluation.getAnswer7());
				pstmt.setString(10, evaluation.getImprovments());
				rowCount = pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	@Override
	public boolean isEvaluation(int studentId, int subjectId) {
		boolean isGetEvaluation = false;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(IS_EVALUATION_SQL)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, subjectId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					isGetEvaluation = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isGetEvaluation;
	}

}
