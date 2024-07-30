package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Evaluation;
import com.tenco.group3.model.StuSubDetail;
import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.interfaces.EvaluationRepository;
import com.tenco.group3.util.DBUtil;

public class EvaluationRepositoryImpl implements EvaluationRepository {

	private static final String INSERT_EVALUATION_SQL = " INSERT INTO evaluation_tb "
			+ " (student_id, subject_id, answer1, answer2, answer3, "
			+ " answer4, answer5, answer6, answer7, improvements) VALUES (?,?,?,?,?,?,?,?,?,?) ";

	private static final String IS_EVALUATION_SQL = " SELECT * " + " FROM evaluation_tb "
			+ " WHERE student_id = ? AND subject_id = ? ";

	private static final String SELECT_ALL_SUB_EVAL = " SELECT e.subject_id, s.name as subject_name FROM subject_tb s JOIN evaluation_tb e ON e.subject_id = s.id WHERE s.professor_id = ? GROUP BY e.subject_id ";
	private static final String SELECT_ALL_EVAL = " SELECT e.evaluation_id, e.improvements, "
			+ " ROUND((answer1 + answer2 + answer3 + answer4 + answer5 + answer6 + answer7) / 7.0, 2) AS avg,  "
			+ " s.name as subject_name FROM subject_tb s JOIN evaluation_tb e ON e.subject_id = s.id WHERE s.professor_id = ? ";
	private static final String SELECT_ALL_EVAL_BY_SUB = " SELECT e.evaluation_id, e.improvements, "
			+ " ROUND((answer1 + answer2 + answer3 + answer4 + answer5 + answer6 + answer7) / 7.0, 2) AS avg,  "
			+ " s.name as subject_name FROM subject_tb s JOIN evaluation_tb e ON e.subject_id = s.id WHERE s.professor_id = ? AND e.subject_id = ? ";

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

	@Override
	public List<Subject> getAllSubjectEvaluation(int professorId) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUB_EVAL)) {
			pstmt.setInt(1, professorId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList
						.add(Subject.builder().id(rs.getInt("subject_id")).name(rs.getString("subject_name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public List<Evaluation> getAllEvaluation(int professorId) {
		List<Evaluation> evaluationList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_EVAL)) {
			pstmt.setInt(1, professorId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				evaluationList.add(Evaluation.builder().evaluationId(rs.getInt("evaluation_id"))
						.improvments(rs.getString("improvements")).avg(rs.getDouble("avg"))
						.subjectName(rs.getString("subject_name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluationList;
	}

	@Override
	public List<Evaluation> getAllEvaluationBySubjectId(int professorId, int subjectId) {
		List<Evaluation> evaluationList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_EVAL_BY_SUB)) {
			pstmt.setInt(1, professorId);
			pstmt.setInt(2, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				evaluationList.add(Evaluation.builder().evaluationId(rs.getInt("evaluation_id"))
						.improvments(rs.getString("improvements")).avg(rs.getDouble("avg"))
						.subjectName(rs.getString("subject_name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluationList;
	}

}
