package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Grade;
import com.tenco.group3.repository.interfaces.GradeRepository;
import com.tenco.group3.util.DBUtil;

public class GradeRepositoryImpl implements GradeRepository {

	private static final String GET_THIS_SEMESTER_SQL = " SELECT su.sub_year, su.semester, st.subject_id, su.name as '과목이름',pr.name as '교수이름', su.type,gr.grade, su.grades, RANK() OVER(ORDER BY gr.grade_value DESC) 석차, ev.evaluation_id "
			+ " FROM stu_sub_tb AS st " + " INNER JOIN subject_tb AS su " + " ON st.subject_id = su.id "
			+ " INNER JOIN professor_tb AS pr " + " ON su.professor_id = pr.id " + " INNER JOIN grade_tb AS gr "
			+ " ON st.grade = gr.grade " + " INNER JOIN student_tb AS stud " + " on st.student_id = stud.id "
			+ " LEFT JOIN evaluation_tb AS ev " + " ON st.subject_id = ev.subject_id "
			+ " WHERE st.student_id = ? AND su.semester = ? AND su.sub_year = ? " + " ORDER BY st.subject_id ";

	private static final String GET_SEMESTER_SQL = " SELECT su.sub_year, su.semester, st.subject_id, su.name as '과목이름',pr.name as '교수이름', su.type, gr.grade,su.grades "
			+ " FROM stu_sub_tb AS st " + " INNER JOIN subject_tb AS su " + " ON st.subject_id = su.id "
			+ " INNER JOIN professor_tb AS pr " + " ON su.professor_id = pr.id " + " INNER JOIN grade_tb AS gr "
			+ " ON st.grade = gr.grade " + " INNER JOIN student_tb AS stud " + " on st.student_id = stud.id "
			+ " WHERE st.student_id = ? AND su.sub_year = ? AND su.semester = ? ";

	private static final String GET_SEMESTER_BY_TYPE_SQL = " SELECT su.sub_year, su.semester, st.subject_id, su.name as '과목이름',pr.name as '교수이름', su.type, gr.grade, su.grades "
			+ " FROM stu_sub_tb AS st " + " INNER JOIN subject_tb AS su " + " ON st.subject_id = su.id "
			+ " INNER JOIN professor_tb AS pr " + " ON su.professor_id = pr.id " + " INNER JOIN grade_tb AS gr "
			+ " ON st.grade = gr.grade " + " INNER JOIN student_tb AS stud " + " on st.student_id = stud.id "
			+ " WHERE st.student_id = ? AND su.sub_year = ? AND su.semester = ? AND su.type = ? ";

	private static final String GET_TOTAL_GRADE = " SELECT su.sub_year, su.semester, SUM(su.grades) AS sum_grades,SUM(st.complete_grade) AS my_grades, AVG(gr.grade_value) AS average "
			+ " FROM stu_sub_tb AS st " + " LEFT JOIN subject_tb AS su " + " ON st.subject_id = su.id "
			+ " LEFT JOIN grade_tb AS gr " + " ON st.grade = gr.grade " + " WHERE st.student_id = ? "
			+ " GROUP BY su.sub_year, su.semester ";

	private static final String GET_SEMESTER = " select su.semester " + " FROM stu_sub_tb AS st "
			+ " INNER JOIN subject_tb AS su " + " ON st.subject_id = su.id " + " WHERE st.student_id = ?"
			+ " GROUP BY su.semester ";
	private static final String GET_SUBYEAR = " select su.sub_year " + " FROM stu_sub_tb AS st "
			+ " INNER JOIN subject_tb AS su " + " ON st.subject_id = su.id\r\n" + " WHERE st.student_id = ? "
			+ " group by su.sub_year " + " ORDER BY su.sub_year DESC ";

	@Override
	public List<Grade> getThisSemester(int studentId, int semester, int sub_year) {
		List<Grade> gradeList = new ArrayList<Grade>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_THIS_SEMESTER_SQL)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, semester);
			pstmt.setInt(3, sub_year);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Grade grade = Grade.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
							.subjectId(rs.getInt("subject_id")).subjectName(rs.getString("과목이름"))
							.professorName(rs.getString("교수이름")).type(rs.getString("type")).grade(rs.getString("grade"))
							.grades(rs.getInt("grades")).build();
					gradeList.add(grade);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradeList;
	}

	@Override
	public List<Grade> getSemester(int studentId, Grade grade) {
		List<Grade> gradeList = new ArrayList<Grade>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SEMESTER_SQL)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, grade.getSubYear());
			pstmt.setInt(3, grade.getSemester());
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					grade = Grade.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
							.subjectId(rs.getInt("subject_id")).subjectName(rs.getString("과목이름"))
							.type(rs.getString("type")).grade(rs.getString("grade")).build();
					gradeList.add(grade);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradeList;
	}

	@Override
	public List<Grade> getSemesterByType(int studentId, Grade grade) {
		List<Grade> gradeList = new ArrayList<Grade>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SEMESTER_BY_TYPE_SQL)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, grade.getSubYear());
			pstmt.setInt(3, grade.getSemester());
			pstmt.setString(4, grade.getType());
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					grade = Grade.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
							.subjectId(rs.getInt("subject_id")).subjectName(rs.getString("과목이름"))
							.type(rs.getString("type")).grade(rs.getString("grade")).build();
					gradeList.add(grade);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gradeList;
	}

	@Override
	public Grade getTotalGrade(int studentId) {
		Grade grade = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_TOTAL_GRADE)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					grade = Grade.builder().subYear(rs.getInt("sub_year")).semester(rs.getInt("semester"))
							.sumGrades(rs.getDouble("sum_grades")).myGrades(rs.getDouble("my_grades"))
							.average(rs.getDouble("average")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

	@Override
	public Grade getSemester(int studentId) {
		Grade grade = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_TOTAL_GRADE)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					grade = Grade.builder().semester(rs.getInt("semester")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

	@Override
	public Grade getSubYear(int studentId) {
		Grade grade = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_TOTAL_GRADE)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					grade = Grade.builder().subYear(rs.getInt("sub_year")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

}
