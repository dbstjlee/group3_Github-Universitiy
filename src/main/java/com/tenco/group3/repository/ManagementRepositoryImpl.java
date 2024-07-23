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

	private static final String SELECT_ALL_STUDENTS = " SELECT * FROM student_tb limit ? offset ? ";
	private static final String SELECT_ALL_PROFESSORS = " SELECT * FROM professor_tb limit ? offset ? ";
	private static final String COUNT_ALL_STUDENTS = " SELECT COUNT(*) AS count FROM student_tb ";
	private static final String COUNT_ALL_PROFESSORS = " SELECT COUNT(*) AS count FROM professor_tb ";
	private static final String INSERT_STUDENT_SQL = " INSERT INTO student_tb (name, birth_date, gender, address, tel, email, dept_id, entrance_date) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_STUDENT_ID_LAST = " SELECT id FROM student_tb ORDER BY id DESC LIMIT 1 ";
	private static final String INSERT_PROFESSOR_SQL = " INSERT INTO professor_tb (name, birth_date, gender, address, tel, email, dept_id) "
			+ " VALUES (?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_PROFESSOR_ID_LAST = " SELECT id FROM professor_tb ORDER BY id DESC LIMIT 1 ";
	private static final String INSERT_STAFF_SQL = " INSERT INTO staff_tb (name, birth_date, gender, address, tel, email) "
			+ " VALUES (?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_STAFF_ID_LAST = " SELECT id FROM staff_tb ORDER BY id DESC LIMIT 1 ";
	private static final String INSERT_USER_SQL = " INSERT INTO user_tb (id, password, user_role) VALUES (?, ?, ";
	// TODO 나중에 암호화해서 자동으로 받을예정
	private static final String SAMPLE_PASSWORD = "123123";
	private static final String UPDATE_SCHEDULE = " UPDATE schedule_state_tb SET ";
	private static final String CHECK_BREAK_APP_DONE = " SELECT * FROM break_app_tb WHERE status = '처리중' ";

	@Override
	public List<Student> getAllStudents(int limit, int offset) {
		List<Student> studentList = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_STUDENTS)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				studentList.add(Student.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).grade(rs.getInt("grade")).semester(rs.getInt("semester"))
						.entranceDate(rs.getDate("entrance_date")).graduationDate(rs.getDate("graduation_date"))
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

		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_PROFESSORS)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				professorList.add(Professor.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.birthDate(rs.getDate("birth_date")).gender(rs.getString("gender"))
						.address(rs.getString("address")).tel(rs.getString("tel")).email(rs.getString("email"))
						.deptId(rs.getInt("dept_id")).hireDate(rs.getDate("hire_date")).build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return professorList;
	}

	@Override
	public int getTotalStudentCount() {
		int totalCounts = 0;
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_STUDENTS)) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				totalCounts = rs.getInt("count");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCounts;
	}

	@Override
	public int getTotalProfessorCount() {
		int totalCounts = 0;
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_PROFESSORS)) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				totalCounts = rs.getInt("count");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCounts;
	}

	@Override
	public boolean createStudent(Student student) {
		boolean success = false;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			// 학생 테이블에 새로운 학생 등록
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_STUDENT_SQL)) {
				pstmt.setString(1, student.getName());
				pstmt.setDate(2, student.getBirthDate());
				pstmt.setString(3, student.getGender());
				pstmt.setString(4, student.getAddress());
				pstmt.setString(5, student.getTel());
				pstmt.setString(6, student.getEmail());
				pstmt.setInt(7, student.getDeptId());
				pstmt.setDate(8, student.getEntranceDate());
				pstmt.executeUpdate();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
			// 새로운 학생의 학번 받아오기
			int id = 0;
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STUDENT_ID_LAST)) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					id = rs.getInt("id");
				}
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
			// 유저 테이블에 새로운 학생을 유저로 등록
			String query = INSERT_USER_SQL + "'student') ";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, id);
				pstmt.setString(2, SAMPLE_PASSWORD);
				pstmt.executeUpdate();
				conn.commit();
				success = true;
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean createProfessor(Professor professor) {
		boolean success = false;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			// 교수 테이블에 새로운 교수 등록
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_PROFESSOR_SQL)) {
				pstmt.setString(1, professor.getName());
				pstmt.setDate(2, professor.getBirthDate());
				pstmt.setString(3, professor.getGender());
				pstmt.setString(4, professor.getAddress());
				pstmt.setString(5, professor.getTel());
				pstmt.setString(6, professor.getEmail());
				pstmt.setInt(7, professor.getDeptId());
				pstmt.executeUpdate();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
			// 새로운 교수의 사번 받아오기
			int id = 0;
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PROFESSOR_ID_LAST)) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					id = rs.getInt("id");
				}
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
			// 유저 테이블에 새로운 교수를 유저로 등록
			String query = INSERT_USER_SQL + "'professor') ";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, id);
				pstmt.setString(2, SAMPLE_PASSWORD);
				pstmt.executeUpdate();
				conn.commit();
				success = true;
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean createStaff(Staff staff) {
		boolean success = false;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			// 직원 테이블에 새로운 직원 등록
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_STAFF_SQL)) {
				pstmt.setString(1, staff.getName());
				pstmt.setDate(2, staff.getBirthDate());
				pstmt.setString(3, staff.getGender());
				pstmt.setString(4, staff.getAddress());
				pstmt.setString(5, staff.getTel());
				pstmt.setString(6, staff.getEmail());
				pstmt.executeUpdate();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
			// 새로운 직원의 사번 받아오기
			int id = 0;
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_STAFF_ID_LAST)) {
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					id = rs.getInt("id");
				}
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
			// 유저 테이블에 새로운 직원를 유저로 등록
			String query = INSERT_USER_SQL + "'staff') ";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, id);
				pstmt.setString(2, SAMPLE_PASSWORD);
				pstmt.executeUpdate();
				conn.commit();
				success = true;
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return success;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public void updateSchedule(String columName, boolean state) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			String query = UPDATE_SCHEDULE + columName + " = ? ";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setBoolean(1, state);
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
	public int getScheduleStat(String columName) {
		int state = -1;
		String query = " SELECT " + columName + " FROM schedule_state_tb LIMIT 1";
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				state = rs.getInt(columName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return state;
	}

	@Override
	public boolean checkBreakAppDone() {
		boolean done = false;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(CHECK_BREAK_APP_DONE)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				// 조회된 행이 하나라도 있다면 false, 없다면 true
				done = !rs.next();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

}
