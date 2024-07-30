package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.interfaces.UserRepository;
import com.tenco.group3.util.DBUtil;

public class UserRepositoryImpl implements UserRepository {

	// ID로 로그인
	private static final String SELECT_USER_BY_ID = " SELECT u.*, "
			+ "        CASE WHEN sd.name IS NOT NULL THEN sd.name " + "        WHEN sf.name IS NOT NULL THEN sf.name "
			+ "        WHEN p.name IS NOT NULL THEN p.name " + "        END AS name " + "        FROM user_tb AS u "
			+ "        LEFT JOIN " + "        student_tb AS sd " + "        ON u.id = sd.id "
			+ "        LEFT JOIN staff_tb AS sf " + "        ON u.id = sf.id " + "        LEFT JOIN professor_tb AS p "
			+ "        ON u.id = p.id " + "        where u.id = ? ; ";

	// 이름, 이메일로 ID 찾기

	// 학생
	private static final String SELECT_STUDENT_BY_NAME_AND_EMAIL = " SELECT id, name " + "	FROM student_tb "
			+ "	WHERE name = ? " + "	AND email = ? ";
	// 교수
	private static final String SELECT_PROFESSOR_BY_FINDID = " SELECT id, name " + "	FROM professor_tb "
			+ "	WHERE name = ? " + "	AND email = ? ";

	// 직원
	private static final String SELECT_STAFF_BY_FINDID = " SELECT id, name " + "	FROM staff_tb " + "	WHERE name = ? "
			+ "	AND email = ? ";

	// 임시 비밀번호 발급
	// 학생
	private static final String SELECT_STUDENT_FOR_TEMPWD = " SELECT s.name, s.email, s.id, u.user_role, u.password "
			+ " FROM user_tb as u " 
			+ " JOIN student_tb as s on u.id = s.id "
			+ " WHERE s.name = ? AND s.email = ? AND s.id = ? ; ";
	
	// 교수 
	private static final String SELECT_PROFESSOR_FOR_TEMPWD = " SELECT p.name, p.email, p.id, u.user_role, u.password "
			+ " FROM user_tb as u "
			+ " JOIN professor_tb as p on u.id = p.id "
			+ " WHERE p.name = ? AND p.email = ? AND p.id = ? ; ";
	
	// 직원
	private static final String SELECT_STAFF_FOR_TEMPWD = " SELECT s.name, s.email, s.id, u.user_role, u.password "
			+ " FROM user_tb as u "
			+ " JOIN staff_tb as s on u.id = s.id "
			+ " WHERE s.name = ? AND s.email = ? AND s.id = ? ; ";
	
	

	// 정보 조회
	// 학생 정보 조회
	private static final String SELECT_STUDENT_INFO_BY_ID = " SELECT s.id, s.name, s.birth_date, "
			+ " s.gender, s.address, s.tel, s.email, s.grade, s.semester, s.entrance_date, s.graduation_date, "
			+ " d.name as deptname, c.name as collname "
			+ " FROM student_tb as s join department_tb as d on s.dept_id = d.id "
			+ " JOIN college_tb as c on c.id = d.college_id WHERE s.id = ? ; ";

	// 교수 정보 조회
	private static final String SELECT_PROFESSOR_INFO_BY_ID = " SELECT p.id, p.name, p.birth_date, p.gender, "
			+ " p.address, p.tel, p.email, d.name as deptname, c.name as collname "
			+ " FROM professor_tb as p join department_tb as d on p.dept_id = d.id "
			+ " JOIN college_tb as c on c.id = d.college_id WHERE p.id = ? ; ";

	// 직원 정보 조회
	private static final String SELECT_STAFF_INFO_BY_ID = " SELECT * FROM staff_tb WHERE id = ? ; ";

	// 정보 수정

	// 학생 정보 수정
	private static final String UPDATE_STUDENT_INFO_BY_ID = " UPDATE student_tb SET address =  ? , "
			+ " tel =  ? , email =  ?  WHERE id = ? ; ";

	// 교수 정보 수정
	private static final String UPDATE_PROFESSOR_INFO_BY_ID = " UPDATE professor_tb SET address =  ? , "
			+ " tel =  ? , email =  ?  WHERE id = ? ; ";

	// 직원 정보 수정
	private static final String UPDATE_STAFF_INFO_BY_ID = " UPDATE staff_tb SET address =  ? , "
			+ " tel =  ? , email =  ?  WHERE id = ? ; ";

	// 학생 프로필 정보 조회
	private static final String SELECT_STUDENT_INFO_MAIN = " SELECT st.id, st.name, st.email, st.grade, st.semester, "
			+ " stat.status, dept.name as deptname FROM student_tb as st "
			+ " JOIN department_tb as dept ON st.dept_id = dept.id "
			+ " JOIN stu_stat_tb as stat on st.id = stat.student_id "
			+ " WHERE stat.to_date = '9999-01-01' AND st.id = ? ; ";

	// 학생 학적 변동 조회
	private static final String SELECT_STUDENT_STAT = " SELECT student_id, status, from_date, description from stu_stat_tb where student_id = ? ; ";

	// 비밀번호 수정
	private static final String UPDATE_USER_PASSWORD = "  UPDATE user_tb SET password = ? WHERE id = ?; ";

	/**
	 * ID로 로그인
	 */
	@Override
	public User getUserById(int id) {
		User user = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_ID)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					user = User.builder().id(rs.getInt("id")).password(rs.getString("password"))
							.userRole(rs.getString("user_role")).username(rs.getString("name")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 이름, 이메일로 ID 찾기 학생
	 */
	@Override
	public User getStudentByNameAndEmail(String username, String email) {
		User student = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_BY_NAME_AND_EMAIL)) {
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
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

	/**
	 * 이름, 이메일로 ID 찾기 교수
	 */
	@Override
	public User getProfessorByNameAndEmail(String username, String email) {
		User professor = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_BY_FINDID)) {
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					professor = User.builder().username(rs.getString("name")).id(rs.getInt("id")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	/**
	 * 이름, 이메일로 ID 찾기 직원
	 */
	@Override
	public User getStaffByNameAndEmail(String username, String email) {
		User staff = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_BY_FINDID)) {
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					staff = User.builder().username(rs.getString("name")).id(rs.getInt("id")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staff;
	}

	/**
	 * 학생 정보 조회
	 */
	@Override
	public Student getStudentInfo(int id) {
		Student studentInfo = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_INFO_BY_ID)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					studentInfo = Student.builder().id(rs.getInt("id")).name(rs.getString("name"))
							.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
							.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
							.grade(rs.getInt("grade")).semester(rs.getInt("semester"))
							.entranceDate(rs.getDate("entrance_date")).graduationDate(rs.getDate("graduation_date"))
							.deptname(rs.getString("deptname")).collname(rs.getString("collname")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentInfo;
	}

	/**
	 * 교수 정보 조회
	 */
	@Override
	public Professor getProfessorInfo(int id) {
		Professor professor = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_INFO_BY_ID)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					professor = Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
							.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
							.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
							.deptname(rs.getString("deptname")).collname(rs.getString("collname")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	/**
	 * 직원 정보 조회
	 */
	@Override
	public Staff getStaffInfo(int id) {
		Staff staff = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_INFO_BY_ID)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					staff = Staff.builder().id(rs.getInt("id")).name(rs.getString("name"))
							.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
							.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
							.hireDate(rs.getDate("hire_date")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return staff;
	}

	/**
	 * 학생 정보 수정
	 */
	@Override
	public void getStudentInfoUpdate(Student student) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STUDENT_INFO_BY_ID)) {
				pstmt.setString(1, student.getAddress());
				pstmt.setString(2, student.getTel());
				pstmt.setString(3, student.getEmail());
				pstmt.setInt(4, student.getId());
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

	/**
	 * 교수 정보 수정
	 */
	@Override
	public void getProfessorInfoUpdate(Professor professor) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_PROFESSOR_INFO_BY_ID)) {
				pstmt.setString(1, professor.getAddress());
				pstmt.setString(2, professor.getTel());
				pstmt.setString(3, professor.getEmail());
				pstmt.setInt(4, professor.getId());
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

	/**
	 * 직원 정보 수정
	 */
	@Override
	public void getStaffInfoUpdate(Staff staff) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_STAFF_INFO_BY_ID)) {
				pstmt.setString(1, staff.getAddress());
				pstmt.setString(2, staff.getTel());
				pstmt.setString(3, staff.getEmail());
				pstmt.setInt(4, staff.getId());
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

	/**
	 * 학생 프로필 정보 조회
	 */
	@Override
	public Student getStudentInfoMain(int id) {
		Student student = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_INFO_MAIN)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					student = Student.builder().id(rs.getInt("id")).name(rs.getString("name"))
							.email(rs.getString("email")).deptname(rs.getString("deptname")).grade(rs.getInt("grade"))
							.semester(rs.getInt("semester")).status(rs.getString("status")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	/**
	 * 학생 학적 변동 조회
	 */
	@Override
	public List<Student> getStudentStat(int id) {
		List<Student> studentInfo = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_STAT)) {
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					studentInfo.add(
							Student.builder()
							.id(rs.getInt("student_id"))
							.status(rs.getString("status"))
							.fromDate(rs.getDate("from_date"))
							.description(rs.getString("description"))
							.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentInfo;
	}

	/**
	 * 사용자 비밀번호 수정
	 */
	@Override
	public void getUpdatePassword(User user) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_USER_PASSWORD)) {
				pstmt.setString(1, user.getPassword());
				pstmt.setInt(2, user.getId());
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

	/**
	 * 임시 비밀번호 발급
	 */

	// 학생
	@Override
	public User getStudentByNameAndEmailAndId(String username, String email, int id) {
		User user = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_FOR_TEMPWD)) {
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				pstmt.setInt(3, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					user = User.builder()
							.id(rs.getInt("id"))
							.username(rs.getString("name"))
							.email(rs.getString("email"))
							.userRole(rs.getString("user_role"))
							.password(rs.getString("password"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	// 교수 
	@Override
	public User getProfessorByNameAndEmailAndId(String username, String email, int id) {
		User user = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_FOR_TEMPWD)) {
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				pstmt.setInt(3, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					user = User.builder()
							.id(rs.getInt("id"))
							.username(rs.getString("name"))
							.email(rs.getString("email"))
							.userRole(rs.getString("user_role"))
							.password(rs.getString("password"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	// 직원
	@Override
	public User getStaffByNameAndEmailAndId(String username, String email, int id) {
		User user = null;
		try (Connection conn = DBUtil.getConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_FOR_TEMPWD)) {
				pstmt.setString(1, username);
				pstmt.setString(2, email);
				pstmt.setInt(3, id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					user = User.builder()
							.id(rs.getInt("id"))
							.username(rs.getString("name"))
							.email(rs.getString("email"))
							.userRole(rs.getString("user_role"))
							.password(rs.getString("password"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
