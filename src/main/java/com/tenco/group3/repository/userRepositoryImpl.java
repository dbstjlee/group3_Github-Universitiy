package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.interfaces.UserRepository;
import com.tenco.group3.util.DBUtil;

public class userRepositoryImpl implements UserRepository{

	// ID로 로그인
	private static final String SELECT_USER_BY_ID = " SELECT u.*, "
			+ "        CASE WHEN sd.name IS NOT NULL THEN sd.name "
			+ "        WHEN sf.name IS NOT NULL THEN sf.name "
			+ "        WHEN p.name IS NOT NULL THEN p.name "
			+ "        END AS name "
			+ "        FROM user_tb AS u "
			+ "        LEFT JOIN "
			+ "        student_tb AS sd "
			+ "        ON u.id = sd.id "
			+ "        LEFT JOIN staff_tb AS sf "
			+ "        ON u.id = sf.id "
			+ "        LEFT JOIN professor_tb AS p "
			+ "        ON u.id = p.id "
			+ "        where u.id = ? ; ";
	
	// 이름, 이메일로 ID 찾기
	// 학생
	private static final String  SELECT_STUDENT_BY_FINDID = " SELECT id, name "
			+ "	FROM student_tb "
			+ "	WHERE name = ? "
			+ "	AND email = ? ";
	
	// 교수
	private static final String  SELECT_PROFESSOR_BY_FINDID = " SELECT id, name "
			+ "	FROM professor_tb "
			+ "	WHERE name = ? "
			+ "	AND email = ? ";
	
	// 직원
	private static final String  SELECT_STAFF_BY_FINDID = " SELECT id, name "
			+ "	FROM staff_tb "
			+ "	WHERE name = ? "
			+ "	AND email = ? ";
	
	
	
	
	@Override
	public User getUserById(int id) {
		User user = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					user = User.builder()
							.id(rs.getInt("id"))
							.password(rs.getString("password"))
							.userRole(rs.getString("user_role"))
							.username(rs.getString("name"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
		return user;
	}

	@Override
	public Student getStudentByNameAndEmail(String username, String email) {
		Student student = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_BY_FINDID)){
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					student = Student.builder()
							.name(rs.getString("name"))
							.id(rs.getInt("id"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public Professor getProfessorByNameAndEmail(String username, String email) {
		Professor professor = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_BY_FINDID)){
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					professor = Professor.builder()
							.name(rs.getString("name"))
							.id(rs.getInt("id"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	@Override
	public Staff getStaffByNameAndEmail(String username, String email) {
		Staff staff = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_BY_FINDID)){
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					staff = Staff.builder()
							.name(rs.getString("name"))
							.id(rs.getInt("id"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staff;
	}
}
