package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.interfaces.SubjectRepository;
import com.tenco.group3.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository {
	private final String SELECT_ALL_SUBJECT = "SELECT sub.*, dept.name AS dept_name, coll.name AS coll_name, prof.name AS professor_name"
			+ "FROM subject_tb AS sub"
			+ "JOIN department_tb AS dept"
			+ "ON sub.dept_id = dept.id"
			+ "JOIN college_tb AS coll"
			+ "ON dept.college_id = coll.id"
			+ "JOIN professor_tb AS prof"
			+ "ON sub.professor_id = prof.id";
	private final String GET_SUBJECT_BY_SEMESTER = "SELECT * FROM subject_tb WHERE  PROFESSOR_ID= ? AND AND sub_year = ? AND  semester = ?";
	private final String SELECT_SUBJECT_BY_ID = "SELECT * FROM subject_tb WHERE id = AND AND sub_year = ? AND  semester = ? AND name = ? ";
	private final String SELECT_ID_BY_LESS_NUM_STUDENT="SELECT id FROM subject_tb WHERE capacity >= num_of_student ";
	@Override
	public List<Subject> getSubjectAll() {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUBJECT)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(Subject.builder()
						.subYear(rs.getInt("sub_year"))
						.semester(rs.getInt("semester"))
						.collName(rs.getString("coll_name"))
						.deptName(rs.getString("dept_name"))
						.id(rs.getInt("id"))
						.type(rs.getString("type"))
						.name(rs.getString("name"))
						.professorName(rs.getString("professor_name"))
						.grades(rs.getInt("grade"))
						.numOfStudent(rs.getInt("num_of_student"))
						.capacity(rs.getInt("capacity"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public List<Subject> getSubjectByType(String type) {
		
		
		return null;
	}

	@Override
	public List<Subject> getSubjectBySemester(int professorId, int subYear, int semester) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SUBJECT_BY_SEMESTER)) {
			pstmt.setInt(1, professorId);
			pstmt.setInt(2, subYear);
			pstmt.setInt(3, semester);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(
						Subject.builder()
						.id(rs.getInt("id"))
						.deptId(rs.getInt("deptId"))
						.name(rs.getString("name"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;

	}

	@Override
	public List<Subject> getStudentBySubject(int id, String type) {
		
		
		
		return null;
	}

	@Override
	public List<Subject> getSubjectById(int id, int year, int semester, String name) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SUBJECT_BY_ID)) {
			pstmt.setInt(1, id);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			pstmt.setString(4, name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.professorId(rs.getInt("professorId"))
						.roomId(rs.getString("roomId"))
						.deptId(rs.getInt("deptId"))
						.type(rs.getString("type"))
						.subYear(rs.getInt("subYear"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public List<Subject> selectIdByLessNumOfStudent() {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ID_BY_LESS_NUM_STUDENT)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.professorId(rs.getInt("professorId"))
						.roomId(rs.getString("roomId"))
						.deptId(rs.getInt("deptId"))
						.type(rs.getString("type"))
						.subYear(rs.getInt("subYear"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

}
