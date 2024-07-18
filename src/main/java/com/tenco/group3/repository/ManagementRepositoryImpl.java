package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.repository.interfaces.ManagementRepository;
import com.tenco.group3.util.DBUtil;

public class ManagementRepositoryImpl implements ManagementRepository {

	private final String SELECT_ALL_STUDENTS = " SELECT * FROM student_tb limit ? offset ? ";
	private final String SELECT_ALL_PROFESSORS = " SELECT * FROM professor_tb limit ? offset ? ";

	@Override
	public List<Student> getAllStudents(int limit, int offset) {
		List<Student> studentList = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection();//
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_STUDENTS)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				studentList.add(Student.builder()
					.id(rs.getInt("id"))
					.name(rs.getString("name"))
					.birthDate(rs.getDate("birthDate"))
					.gender(rs.getString("gender"))
					.address(rs.getString("address"))
					.tel(rs.getString("tel"))
					.email(rs.getString("email"))
					.deptId(rs.getInt("deptId"))
					.grade(rs.getInt("grade"))
					.semester(rs.getInt("semester"))
					.entranceDate(rs.getDate("entranceDate"))
					.graduationDate(rs.getDate("graduationDate"))
					.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return studentList;
	}

	@Override
	public List<Professor> getAllProfessors(int limit, int offset) {
		List<Professor> professorList = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection();//
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_PROFESSORS)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				professorList.add(Professor.builder()
					.id(rs.getInt("id"))
					.name(rs.getString("name"))
					.birthDate(rs.getDate("birthDate"))
					.gender(rs.getString("gender"))
					.address(rs.getString("address"))
					.tel(rs.getString("tel"))
					.email(rs.getString("email"))
					.deptId(rs.getInt("deptId"))
					.hireDate(rs.getDate("hireDate"))
					.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return professorList;
	}

	@Override
	public int getTotalStudentCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalProfessorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createStudent(Student student) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createProfessor(Professor professor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createStaff(Staff staff) {
		// TODO Auto-generated method stub
		return 0;
	}

}
