package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;
import com.tenco.group3.repository.interfaces.StuSubRepository;
import com.tenco.group3.util.DBUtil;
import com.tenco.group3.util.SemesterUtil;

public class StuSubRepositoryImpl implements StuSubRepository {

	private static final String SELECT_RANKED_STUDENT = " WITH avg_grades AS ( "
			+ "    SELECT ss.student_id, AVG(g.grade_value * ss.complete_grade) AS avg_grade " + "    FROM stu_sub_tb ss "
			+ "    JOIN grade_tb g ON ss.grade = g.grade " + "    JOIN subject_tb s ON ss.subject_id = s.id "
			+ "    WHERE s.sub_year = ? AND s.semester = ? " + "    GROUP BY ss.student_id " + " ), " + " ranked_grades AS ( "
			+ "    SELECT tg.student_id, s.dept_id, s.grade " + " , RANK() OVER (PARTITION BY s.dept_id, s.grade ORDER BY avg_grade DESC) AS ranking "
			+ "    FROM avg_grades tg " + "    JOIN student_tb s ON tg.student_id = s.id " + " ) " + " SELECT student_id, dept_id, grade, ranking "
			+ " FROM ranked_grades " + " WHERE ranking <= 5 ";

	// 예비 수강 취소
	private static final String DELETE_PRE_CONFIRM_SUBJECT_SQL = " DELETE FROM pre_stu_sub_tb WHERE subject_id = ? ";

	// 정원이 만족된 과목 조회
	private static final String SELECT_SUBJECT_SATIFIED = " SELECT * FROM subject_tb WHERE capacity >= num_of_student AND sub_year = 2023 AND semester = 1 ";

	// 과목 id로 예비 수강 신청 내역 조회
	private static final String SELECT_PRE_BY_SUBJECT = " SELECT * FROM pre_stu_sub_tb WHERE subject_id = ? ";

	// stu_sub_tb에 insert
	private static final String ADD_SUGANG = " INSERT INTO stu_sub_tb (student_id, subject_id, complete_grade) VALUES (?, ?, ?) ";

	// 정원 초과된 과목 학생 수 0명으로
	private static final String UPDATE_SUBJECT_OVER = " UPDATE subject_tb SET num_of_student = 0 WHERE capacity < num_of_student ";

	// 수강 신청 기간 종료시 예비 수강 신청 리스트 비우기
	private static final String DELETE_ALL_PRE = " DELETE FROM pre_stu_sub_tb ";

	// stu_sub_tb에서 현재 학기에 해당하는 과목과 학생 id 받아옴
	private static final String SELECT_ALL_SUGANG = " SELECT ss.id, ss.student_id, ss.subject_id FROM stu_sub_tb ss JOIN subject_tb s ON ss.subject_id = s.id WHERE s.sub_year = ? AND s.semester = ? ";

	// stu_sub_detail_tb에 insert
	private static final String ADD_SUBJECT_DETAIL = " INSERT INTO stu_sub_detail_tb (id, student_id, subject_id) VALUES (?,?,?) ";

	@Override
	public List<RankedStudent> selectRankedStudent() {
		List<RankedStudent> studentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_RANKED_STUDENT)) {
			pstmt.setInt(1, SemesterUtil.getCurrentYear());
			pstmt.setInt(2, SemesterUtil.getCurrentSemester());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				studentList.add(RankedStudent.builder()
					.studentId(rs.getInt("student_id"))
					.deptId(rs.getInt("dept_id"))
					.grade(rs.getInt("grade"))
					.ranking(rs.getInt("ranking"))
					.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public List<Subject> getAllSubjectSatisfied() {
		List<Subject> subjectList = new ArrayList<Subject>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SELECT_SUBJECT_SATIFIED)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Subject subject = Subject.builder().id(rs.getInt("id")).grades(rs.getInt("grades")).build();
					subjectList.add(subject);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public List<Sugang> getAllPreBySubject(List<Subject> subjectList) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			for (Subject subject : subjectList) {

				try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PRE_BY_SUBJECT)) {
					pstmt.setInt(1, subject.getId());
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						Sugang sugang = Sugang.builder()
							.studentId(rs.getInt("student_id"))
							.subjectId(rs.getInt("subject_id"))
							.grades(subject.getGrades())
							.build();
						sugangList.add(sugang);
					}
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public void deletePreConfirmSubject(List<Subject> subjectList) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			for (Subject subject : subjectList) {
				try (PreparedStatement pstmt = conn.prepareStatement(DELETE_PRE_CONFIRM_SUBJECT_SQL)) {
					pstmt.setInt(1, subject.getId());
					pstmt.executeUpdate();
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSugang(List<Sugang> sugangList) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			for (Sugang sugang : sugangList) {
				try (PreparedStatement pstmt = conn.prepareStatement(ADD_SUGANG)) {
					pstmt.setInt(1, sugang.getStudentId());
					pstmt.setInt(2, sugang.getSubjectId());
					pstmt.setInt(3, sugang.getGrades());
					pstmt.executeUpdate();
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateSubjectOver() {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_SUBJECT_OVER)) {
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAllPre() {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_ALL_PRE)) {
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Sugang> getAllSugang() {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUGANG)) {
				pstmt.setInt(1, SemesterUtil.getCurrentYear());
				pstmt.setInt(2, SemesterUtil.getCurrentSemester());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Sugang sugang = Sugang.builder().sugangId(rs.getInt("id")).studentId(rs.getInt("student_id")).subjectId(rs.getInt("subject_id")).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}
	
	@Override
	public void addStuSubDetail(List<Sugang> sugangList) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			for (Sugang sugang : sugangList) {
				try (PreparedStatement pstmt = conn.prepareStatement(ADD_SUBJECT_DETAIL)) {
					pstmt.setInt(1, sugang.getSugangId());
					pstmt.setInt(2, sugang.getStudentId());
					pstmt.setInt(3, sugang.getSubjectId());
					pstmt.executeUpdate();
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
