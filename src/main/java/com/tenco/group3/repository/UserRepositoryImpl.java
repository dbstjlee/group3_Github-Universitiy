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

public class UserRepositoryImpl implements UserRepository{

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
	private static final String  SELECT_STUDENT_BY_NAME_AND_EMAIL = " SELECT id, name "
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
	
	
	// 학생 정보 조회
	private static final String SELECT_STUDENT_INFO_BY_ID = " select s.id, s.name, s.birth_date, "
			+ " s.gender, s.address, s.tel, s.email, s.grade, s.semester, s.entrance_date, s.graduation_date, "
			+ " d.name as deptname, c.name as collname "
			+ " from student_tb as s join department_tb as d on s.dept_id = d.id "
			+ " join college_tb as c on c.id = d.college_id WHERE s.id = ? ; ";
	
	
	// 교수 정보 조회
	private static final String SELECT_PROFESSOR_INFO_BY_ID = " select p.id, p.name, p.birth_date, p.gender, "
			+ " p.address, p.tel, p.email, d.name as deptname, c.name as collname "
			+ " from professor_tb as p join department_tb as d on p.dept_id = d.id "
			+ " join college_tb as c on c.id = d.college_id where p.id = ? ; ";
	
	
	// 직원 정보 조회
	private static final String SELECT_STAFF_INFO_BY_ID = " select * from staff_tb WHERE = ? 	; ";
	
	
	
	
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
	public User getStudentByNameAndEmail(String username, String email) {
		User student = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_BY_NAME_AND_EMAIL)){
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					student = User.builder()
							.username(rs.getString("name"))
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
	public User getProfessorByNameAndEmail(String username, String email) {
		User professor = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_BY_FINDID)){
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					professor = User.builder()
							.username(rs.getString("name"))
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
	public User getStaffByNameAndEmail(String username, String email) {
		User staff = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_BY_FINDID)){
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					staff = User.builder()
							.username(rs.getString("name"))
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

	@Override
	public Student getStudentInfo(int id) {
		Student studentInfo = null;
		 try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_INFO_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					studentInfo = Student.builder()
								.id(rs.getInt("id"))
								.name(rs.getString("name"))
								.birthDate(rs.getDate("birth_date"))
								.gender(rs.getString("gender"))
								.address(rs.getString("address"))
								.tel(rs.getString("tel"))
								.email(rs.getString("email"))
								.grade(rs.getInt("grade"))
								.semester(rs.getInt("semester"))
								.entranceDate(rs.getDate("entrance_date"))
								.graduationDate(rs.getDate("graduation_date"))
								.deptname(rs.getString("deptname"))
								.collname(rs.getString("collname"))
								.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentInfo;
	}

	@Override
	public Professor getProfessorInfo(int id) {
		Professor professor = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_INFO_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					professor = Professor.builder()
								.id(rs.getInt("id"))
								.name(rs.getString("name"))
								.birthDate(rs.getDate("birth_date"))
								.gender(rs.getString("gender"))
								.address(rs.getString("address"))
								.tel(rs.getString("tel"))
								.email(rs.getString("email"))
								.deptname(rs.getString("deptname"))
								.collname(rs.getString("collname"))
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
	public Staff getStaffInfo(int id) {
		Staff staff = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_INFO_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					staff = Staff.builder()
							.id(rs.getInt("id"))
							.name(rs.getString("name"))
							.birthDate(rs.getDate("birth_date"))
							.gender(rs.getString("gender"))
							.address(rs.getString("address"))
							.tel(rs.getString("tel"))
							.email(rs.getString("email"))
							.hireDate(rs.getDate("hire_date"))
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
