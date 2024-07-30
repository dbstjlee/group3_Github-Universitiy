package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Sugang;
import com.tenco.group3.repository.interfaces.SugangRepository;
import com.tenco.group3.util.DBUtil;

public class SugangRepositoryImpl implements SugangRepository {

	// 수강 신청
	private static final String ADD_ENROLMENT_SQL = " INSERT INTO stu_sub_tb (student_id, subject_id, complete_grade) VALUES (?, ?, ?) ";

	// 예비 수강 신청
	private static final String ADD_PRE_ENROLMENT_SQL = " INSERT INTO pre_stu_sub_tb (student_id, subject_id) VALUES (?, ?) ";

	// 수강 신청시 인원수 증가 쿼리
	private static final String UPDATE_ADD_ENROLMENT_SQL = " UPDATE subject_tb SET num_of_student = num_of_student + 1 WHERE id = ? ";

	// 수강 신청시 인원수 감소 쿼리
	private static final String UPDATE_CANCLE_ENROLMENT_SQL = " UPDATE subject_tb SET num_of_student = num_of_student - 1 WHERE id = ? ";

	// 정원 - 신청 인원 값 출력하는 쿼리
	private static final String RESULT_STUDENT_COUNT = " SELECT id, (capacity - num_of_student) AS sub FROM subject_tb ";

	// 신청 인원 구하는 쿼리
	private static final String NUM_OF_STUDENT = " SELECT num_of_student FROM subject_tb WHERE id = ? ";

	private static final String CAPACITY = " SELECT capacity FROM subject_tb WHERE id = ? ";

	// 강의 시간표 조회
	private static final String GET_ALL_SUBJECT_LIST = " SELECT co.name as '단과대학', de.name as '개설학과', su.id, su.type, su.name as '강의명', pr.name as '강사명', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity "
			+ " FROM subject_tb as su " + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id "
			+ " JOIN college_tb as co on co.id = de.college_id WHERE sub_year = ? AND semester = ? "
			+ " order by id asc " + " limit ? offset ? ";

	// 예비 수강 신청 내역
	private static final String GET_PRE_APPLICATION_SUBJECT = " SELECT pre.student_id, su.id, su.name AS '강의명', "
			+ " pr.name AS '담당교수', de.name AS '개설학과', co.name AS '단과대학', "
			+ " su.grades, su.type, su.sub_day, su.start_time, su.end_time, su.room_id, su.num_of_student, su.capacity, "
			+ " CASE WHEN pre.student_id IS NOT NULL THEN 1 ELSE 0 END AS confirm "
			+ " FROM (select student_id, subject_id from pre_stu_sub_tb where student_id = ?) AS pre "
			+ " right JOIN subject_tb AS su ON pre.subject_id = su.id "
			+ " inner JOIN department_tb AS de ON su.dept_id = de.id "
			+ " inner JOIN college_tb AS co ON co.id = de.college_id "
			+ " inner JOIN professor_tb AS pr ON su.professor_id = pr.id WHERE sub_year = ? AND semester = ? "
			+ " ORDER BY su.id ASC limit ? offset ? ";

	// 수강 신청 내역
	private static final String GET_APPLICATION_SUBJECT = " SELECT sst.student_id, su.id, su.name AS '강의명', "
			+ " pr.name AS '담당교수', de.name AS '개설학과', co.name AS '단과대학', "
			+ " su.grades, su.type, su.sub_day, su.start_time, su.end_time, su.room_id, su.num_of_student, su.capacity, "
			+ " CASE WHEN sst.student_id IS NOT NULL THEN 1 ELSE 0 END AS confirm, "
			+ " (capacity - num_of_student)AS result "
			+ " FROM (select student_id, subject_id from stu_sub_tb where student_id = ?) AS sst "
			+ " right JOIN subject_tb AS su ON sst.subject_id = su.id "
			+ " inner JOIN department_tb AS de ON su.dept_id = de.id "
			+ " inner JOIN college_tb AS co ON co.id = de.college_id "
			+ " inner JOIN professor_tb AS pr ON su.professor_id = pr.id WHERE sub_year = ? AND semester = ? "
			+ " ORDER BY su.id ASC limit ? offset ? ";

	// 강의 검색
	private static final String GET_SUBJECT_BY_SEARCH = " SELECT " + "    co.name as '단과대학', "
			+ "    de.name as '개설학과', " + "    su.id, " + "    su.type, " + "    su.name as '강의명', "
			+ "    pr.name as '강사명', " + "    su.grades, " + "    su.sub_day, " + "    su.start_time, "
			+ "    su.end_time, " + "    su.room_id, " + "    su.num_of_student, " + "    su.capacity, "
			+ "    su.dept_id " + " FROM subject_tb as su " + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " WHERE " + "    (? = '전체' OR su.type = ?) " + "    AND (? = -1 OR su.dept_id = ?) "
			+ "    AND (? IS NULL OR su.name = ?) AND sub_year = ? AND semester = ? "
			+ " ORDER BY su.id ASC limit ? offset ? ";

	// 예비 수강 신청 검색
	private static final String GET_PRE_SUBJECT_BY_SEARCH = " SELECT " + "    pre.student_id, " + "    su.id, "
			+ "    su.name AS '강의명', " + "    pr.name AS '담당교수', " + "    de.name AS '개설학과', "
			+ "    co.name AS '단과대학', " + "    su.grades, " + "    su.type, " + "    su.sub_day, "
			+ "    su.start_time, " + "    su.end_time, " + "    su.room_id, " + "    su.num_of_student, "
			+ "    su.capacity, " + "    CASE " + "        WHEN pre.student_id IS NOT NULL THEN 1 " + "        ELSE 0 "
			+ "    END AS confirm, " + "    (capacity - num_of_student) AS result " + "FROM "
			+ "    (select student_id, subject_id from pre_stu_sub_tb where student_id = 2023000001) AS pre "
			+ "        right JOIN " + "    subject_tb AS su ON pre.subject_id = su.id " + "        inner JOIN "
			+ "    department_tb AS de ON su.dept_id = de.id " + "        inner JOIN "
			+ "    college_tb AS co ON co.id = de.college_id " + "        inner JOIN "
			+ "    professor_tb AS pr ON su.professor_id = pr.id "
			+ "WHERE (? = '전체' OR su.type = ?) AND (? = -1 OR su.dept_id = ?) AND (? IS NULL OR su.name = ?) AND sub_year = ? AND semester = ? "
			+ "ORDER BY su.id ASC limit ? offset ? ";

	// 수강 신청 검색
	private static final String GET_APP_SUBJECT_BY_SEARCH = " SELECT  " + "    sst.student_id, " + "    su.id, "
			+ "    su.name AS '강의명', " + "    pr.name AS '담당교수', " + "    de.name AS '개설학과', "
			+ "    co.name AS '단과대학', " + "    su.grades, " + "    su.type, " + "    su.sub_day, "
			+ "    su.start_time, " + "    su.end_time, " + "    su.room_id, " + "    su.num_of_student, "
			+ "    su.capacity, " + "    CASE " + "        WHEN sst.student_id IS NOT NULL THEN 1 " + "        ELSE 0 "
			+ "    END AS confirm, " + "    (su.capacity - su.num_of_student) AS result " + "FROM "
			+ "    (SELECT student_id, subject_id FROM stu_sub_tb WHERE student_id = 2023000001) AS sst "
			+ "        RIGHT JOIN " + "    subject_tb AS su ON sst.subject_id = su.id " + "        INNER JOIN "
			+ "    department_tb AS de ON su.dept_id = de.id " + "        INNER JOIN "
			+ "    college_tb AS co ON co.id = de.college_id " + "        INNER JOIN "
			+ "    professor_tb AS pr ON su.professor_id = pr.id " + "WHERE (? = '전체' OR su.type = ?)  "
			+ "    AND (? = -1 OR su.dept_id = ?)  "
			+ "    AND (? IS NULL OR su.name = ?) AND sub_year = ? AND semester = ? "
			+ "ORDER BY su.id ASC limit ? offset ? ";

	// 전체 카운트
	private static final String COUNT_ALL_SUBJECT = " SELECT count(*) as count FROM subject_tb WHERE sub_year = ? AND semester = ? ";

	// 검색 결과 카운트
	private static final String COUNT_SEARCH_SUBJECT = " select count(*) as count from subject_tb WHERE (? = '전체' OR type = ?) AND (? = -1 OR dept_id = ?) AND (? IS NULL OR name = ?) AND sub_year = ? AND semester = ? ";

	// 수강 신청 내역
	private static final String GET_APPLICATED_SUBJECT_LIST = " select sst.student_id, sst.subject_id, su.name as '강의명', pr.name as '담당교수', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity "
			+ " from stu_sub_tb as sst " + " left join subject_tb as su on sst.subject_id = su.id "
			+ " left Join professor_tb as pr on su.professor_id = pr.id "
			+ " where sst.student_id = ? AND sub_year = ? AND semester = ? ";

	// 예비 수강 신청 내역
	private static final String GET_PRE_APPLICATED_SUBJECT_LIST = " select pre.student_id, pre.subject_id, su.name as '강의명', pr.name as '담당교수', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity  "
			+ " from pre_stu_sub_tb as pre  " + " left join subject_tb as su on pre.subject_id = su.id  "
			+ " left Join professor_tb as pr on su.professor_id = pr.id "
			+ " where pre.student_id = ? AND sub_year = ? AND semester = ? ";

	// 총 학점
	private static final String TOTAL_SUBJECT_GRADES = " select sum(su.grades) as '총합' " + " from stu_sub_tb as sst "
			+ " left join subject_tb as su on sst.subject_id = su.id "
			+ " where sst.student_id = ? AND sub_year = ? AND semester = ? ";

	// 예비 총 학점
	private static final String TOTAL_PRE_SUBJECT_GRADES = " SELECT sum(grades) as '총합' "
			+ "FROM pre_stu_sub_tb as pre " + "left join subject_tb as su on pre.subject_id = su.id "
			+ "where pre.student_id = ? AND sub_year = ? AND semester = ? ";

	// 수강 신청 여부
	private static final String CONFIRM_SUBJECT_SQL = " select * from stu_sub_tb where student_id = ? AND sub_year = ? AND semester = ? ";

	// 수강 취소
	private static final String DELETE_CONFIRM_SUBJECT_SQL = " DELETE FROM stu_sub_tb WHERE subject_id = ? ";

	// 예비 수강 취소
	private static final String DELETE_PRE_CONFIRM_SUBJECT_SQL = " DELETE FROM pre_stu_sub_tb WHERE subject_id = ? ";

	// 신청 초기화
	private static final String PRE_SUBJECT_RESET = " delete from pre_stu_sub_tb where subject_id = ? ";

	private static final String APP_SUBJECT_RESET = " delete from stu_sub_tb where subject_id = ? ";

	private static final String NOM_STUDENT_RESET = " UPDATE subject_tb SET num_of_student = 0 WHERE id = ? ";

	// 예비 수강신청 -> 수강 신청 초기화된 리스트
	private static final String PRE_TO_APP_SUBJECT = " SELECT " + "    pre.student_id, " + "    su.id, "
			+ "    su.name AS '강의명', " + "    pr.name AS '담당교수', " + "    de.name AS '개설학과', "
			+ "    co.name AS '단과대학', " + "    su.grades, " + "    su.type, " + "    su.sub_day, "
			+ "    su.start_time, " + "    su.end_time, " + "    su.room_id, " + "    su.num_of_student, "
			+ "    su.capacity, " + "    CASE " + "        WHEN pre.student_id IS NOT NULL THEN 1 ELSE 0 "
			+ "    END AS confirm," + "    (su.capacity - su.num_of_student) AS result " + " FROM "
			+ "    (SELECT student_id, subject_id FROM pre_stu_sub_tb WHERE student_id = ?) AS pre "
			+ " RIGHT JOIN subject_tb AS su ON pre.subject_id = su.id "
			+ " INNER JOIN department_tb AS de ON su.dept_id = de.id "
			+ " INNER JOIN college_tb AS co ON co.id = de.college_id "
			+ " INNER JOIN professor_tb AS pr ON su.professor_id = pr.id "
			+ " WHERE pre.student_id = ? AND sub_year = ? AND semester = ? " + " ORDER BY su.id ASC ";

	// 수강 학점 총합
	private static final String GET_TOTAL_GRADES = " select sum(complete_grade) as totalGrade from stu_sub_tb where student_id = ? ";

	// 예비 수강 신청 학점 총합
	private static final String GET_PRE_TOTAL_GRADES = " SELECT sum(grades) as totalGrade FROM pre_stu_sub_tb as pre left join subject_tb as su on pre.subject_id = su.id where pre.student_id = ? AND sub_year = ? AND semester = ? ";

	// 예비 수강 신청 -> 수강 신청시 처리
	private static final String SUBMIT_PRE_TO_ENRILMENT = " DELETE FROM pre_stu_sub_tb WHERE student_id = ? AND subject_id = ? ";

	// 휴학 여부
	private static final String IS_BREAK_APP = " SELECT status FROM stu_stat_tb WHERE student_id = ? ORDER BY id DESC LIMIT 1 ";

	@Override
	public List<Sugang> getAllSubject(int limit, int offset, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)) {
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			pstmt.setInt(3, limit);
			pstmt.setInt(4, offset);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().collegeName(rs.getString("단과대학")).departName(rs.getString("개설학과"))
							.subjectId(rs.getInt("id")).subjectType(rs.getString("type"))
							.subjectName(rs.getString("강의명")).professorName(rs.getString("강사명"))
							.grades(rs.getInt("grades")).subjectDay(rs.getString("sub_day"))
							.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time"))
							.roomId(rs.getString("room_id")).numOfStudent(rs.getInt("num_of_student"))
							.capacity(rs.getInt("capacity")).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public List<Sugang> getPreApplicationSubject(int studentId, int limit, int offset, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_PRE_APPLICATION_SUBJECT)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			pstmt.setInt(4, limit);
			pstmt.setInt(5, offset);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().collegeName(rs.getString("단과대학")).departName(rs.getString("개설학과"))
							.subjectId(rs.getInt("id")).subjectType(rs.getString("type"))
							.subjectName(rs.getString("강의명")).professorName(rs.getString("담당교수"))
							.grades(rs.getInt("grades")).subjectDay(rs.getString("sub_day"))
							.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time"))
							.roomId(rs.getString("room_id")).numOfStudent(rs.getInt("num_of_student"))
							.capacity(rs.getInt("capacity")).hasConfirmed(rs.getInt("confirm") == 1).build();
					sugangList.add(sugang);
				}
			} catch (SQLException e) {
				e.printStackTrace(); // SQLException 처리
			}
		} catch (SQLException e) {
			e.printStackTrace(); // SQLException 처리
		}
		return sugangList;
	}

	@Override
	public int addPreEnrolment(int studentId, int subjectId) {
		int rowCount = 0;

		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmtAddPreSub = conn.prepareStatement(ADD_PRE_ENROLMENT_SQL);
					PreparedStatement pstmtAddCount = conn.prepareStatement(UPDATE_ADD_ENROLMENT_SQL)) {

				pstmtAddPreSub.setInt(1, studentId);
				pstmtAddPreSub.setInt(2, subjectId);
				pstmtAddCount.setInt(1, subjectId);

				rowCount = pstmtAddPreSub.executeUpdate();
				pstmtAddCount.executeUpdate();

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
	public List<Sugang> getSubjectBySearch(Sugang sugang, int pageSize, int offset, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SUBJECT_BY_SEARCH)) {
			pstmt.setString(1, sugang.getSubjectType());
			pstmt.setString(2, sugang.getSubjectType());
			pstmt.setInt(3, sugang.getDeptId());
			pstmt.setInt(4, sugang.getDeptId());
			pstmt.setString(5, sugang.getSubjectName());
			pstmt.setString(6, sugang.getSubjectName());
			pstmt.setInt(7, year);
			pstmt.setInt(8, semester);
			pstmt.setInt(9, pageSize);
			pstmt.setInt(10, offset);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					sugang = Sugang.builder().collegeName(rs.getString("단과대학")).departName(rs.getString("개설학과"))
							.subjectId(rs.getInt("id")).subjectType(rs.getString("type"))
							.subjectName(rs.getString("강의명")).professorName(rs.getString("강사명"))
							.grades(rs.getInt("grades")).subjectDay(rs.getString("sub_day"))
							.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time"))
							.roomId(rs.getString("room_id")).numOfStudent(rs.getInt("num_of_student"))
							.capacity(rs.getInt("capacity")).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public int getAllSubjectCount(int year, int semester) {
		int subjectCount = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_SUBJECT)) {
			pstmt.setInt(1, year);
			pstmt.setInt(2, semester);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				subjectCount = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectCount;
	}

	@Override
	public List<Sugang> getApplicatedSubjectList(int studentId, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_APPLICATED_SUBJECT_LIST)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().subjectId(rs.getInt("subject_id")).subjectName(rs.getString("강의명"))
							.professorName(rs.getString("담당교수")).grades(rs.getInt("grades"))
							.subjectDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
							.endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
							.numOfStudent(rs.getInt("num_of_student")).capacity(rs.getInt("capacity")).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public List<Sugang> getPreApplicatedSubjectList(int studentId, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_PRE_APPLICATED_SUBJECT_LIST)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().subjectId(rs.getInt("subject_id")).subjectName(rs.getString("강의명"))
							.professorName(rs.getString("담당교수")).grades(rs.getInt("grades"))
							.subjectDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
							.endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
							.numOfStudent(rs.getInt("num_of_student")).capacity(rs.getInt("capacity")).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public int getSubjectGrade(int studentId, int year, int semester) {
		int totalGrade = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(TOTAL_SUBJECT_GRADES)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				totalGrade = rs.getInt("총합");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalGrade;
	}

	@Override
	public boolean getConfirmSubject(int studentId, int year, int semester) {
		boolean sugang = false;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(CONFIRM_SUBJECT_SQL)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) { // 결과 집합에 데이터가 있는지 확인합니다.
					sugang = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sugang;
	}

	@Override
	public int deletePreConfirmSubject(int subjectId) {
		int rowCount = 0;
		int numOfStudent = 0;

		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			// NUM_OF_STUDENT 실행
			try (PreparedStatement pstmtNumOfStudent = conn.prepareStatement(NUM_OF_STUDENT)) {
				pstmtNumOfStudent.setInt(1, subjectId);
				try (ResultSet rsNum = pstmtNumOfStudent.executeQuery()) {
					if (rsNum.next()) {
						numOfStudent = rsNum.getInt("num_of_student");
					}
				}
			}
			// 수강 인원이 0 이상일 경우 UPDATE_CANCLE_ENROLMENT_SQL 실행
			if (numOfStudent > 0) {
				try (PreparedStatement pstmtUpdate = conn.prepareStatement(UPDATE_CANCLE_ENROLMENT_SQL)) {
					pstmtUpdate.setInt(1, subjectId);
					rowCount += pstmtUpdate.executeUpdate();
				}
			} else {
				rowCount = 0;
			}
			// DELETE_PRE_CONFIRM_SUBJECT_SQL 실행
			try (PreparedStatement pstmtDeletePre = conn.prepareStatement(DELETE_PRE_CONFIRM_SUBJECT_SQL)) {
				pstmtDeletePre.setInt(1, subjectId);
				rowCount += pstmtDeletePre.executeUpdate();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try (Connection conn = DBUtil.getConnection()) {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return rowCount;
	}

	@Override
	public int getSearchSubjectCount(Sugang sugang, int year, int semester) {
		int count = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_SEARCH_SUBJECT)) {
			pstmt.setString(1, sugang.getSubjectType());
			pstmt.setString(2, sugang.getSubjectType());
			pstmt.setInt(3, sugang.getDeptId());
			pstmt.setInt(4, sugang.getDeptId());
			pstmt.setString(5, sugang.getSubjectName());
			pstmt.setString(6, sugang.getSubjectName());
			pstmt.setInt(7, year);
			pstmt.setInt(8, semester);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt("count");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public int deleteConfirmSubject(int subjectId) {
		int rowCount = 0;
		int numOfStudent = 0;

		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			// NUM_OF_STUDENT 실행
			try (PreparedStatement pstmtNumOfStudent = conn.prepareStatement(NUM_OF_STUDENT)) {
				pstmtNumOfStudent.setInt(1, subjectId);
				try (ResultSet rsNum = pstmtNumOfStudent.executeQuery()) {
					if (rsNum.next()) {
						numOfStudent = rsNum.getInt("num_of_student");
					}
				}
			}
			// 수강 인원이 0 이상일 경우 UPDATE_CANCLE_ENROLMENT_SQL 실행
			if (numOfStudent > 0) {
				try (PreparedStatement pstmtUpdate = conn.prepareStatement(UPDATE_CANCLE_ENROLMENT_SQL)) {
					pstmtUpdate.setInt(1, subjectId);
					rowCount += pstmtUpdate.executeUpdate();
				}
			} else {
				rowCount = 0;
			}
			// DELETE_CONFIRM_SUBJECT_SQL 실행
			try (PreparedStatement pstmtDeletePre = conn.prepareStatement(DELETE_CONFIRM_SUBJECT_SQL)) {
				pstmtDeletePre.setInt(1, subjectId);
				rowCount += pstmtDeletePre.executeUpdate();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try (Connection conn = DBUtil.getConnection()) {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return rowCount;
	}

	@Override
	public List<Sugang> getApplicationSubject(int studentId, int limit, int offset, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_APPLICATION_SUBJECT)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, limit);
			pstmt.setInt(3, offset);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().collegeName(rs.getString("단과대학")).departName(rs.getString("개설학과"))
							.subjectId(rs.getInt("id")).subjectType(rs.getString("type"))
							.subjectName(rs.getString("강의명")).professorName(rs.getString("담당교수"))
							.grades(rs.getInt("grades")).subjectDay(rs.getString("sub_day"))
							.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time"))
							.roomId(rs.getString("room_id")).numOfStudent(rs.getInt("num_of_student"))
							.capacity(rs.getInt("capacity")).hasConfirmed(rs.getInt("confirm") == 1)
							.result(rs.getInt("result")).build();
					sugangList.add(sugang);
				}
			} catch (SQLException e) {
				e.printStackTrace(); // SQLException 처리
			}
		} catch (SQLException e) {
			e.printStackTrace(); // SQLException 처리
		}
		return sugangList;
	}

	@Override
	public List<Sugang> resultStudentCount() {
		List<Sugang> sugangList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(RESULT_STUDENT_COUNT)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int subValue = rs.getInt("sub");
					if (subValue < 0) {
						Sugang sugang = Sugang.builder().subjectId(rs.getInt("id")).sub(subValue).build();
						sugangList.add(sugang);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public List<Sugang> getPreSubjectBySearch(Sugang sugang, int limit, int offset, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_PRE_SUBJECT_BY_SEARCH)) {
			pstmt.setString(1, sugang.getSubjectType());
			pstmt.setString(2, sugang.getSubjectType());
			pstmt.setInt(3, sugang.getDeptId());
			pstmt.setInt(4, sugang.getDeptId());
			pstmt.setString(5, sugang.getSubjectName());
			pstmt.setString(6, sugang.getSubjectName());
			pstmt.setInt(7, year);
			pstmt.setInt(8, semester);
			pstmt.setInt(9, limit);
			pstmt.setInt(10, offset);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					sugang = Sugang.builder().collegeName(rs.getString("단과대학")).departName(rs.getString("개설학과"))
							.subjectId(rs.getInt("id")).subjectType(rs.getString("type"))
							.subjectName(rs.getString("강의명")).professorName(rs.getString("담당교수"))
							.grades(rs.getInt("grades")).subjectDay(rs.getString("sub_day"))
							.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time"))
							.roomId(rs.getString("room_id")).numOfStudent(rs.getInt("num_of_student"))
							.capacity(rs.getInt("capacity")).hasConfirmed(rs.getInt("confirm") == 1).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public List<Sugang> getAppSubjectBySearch(Sugang sugang, int limit, int offset, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_APP_SUBJECT_BY_SEARCH)) {
			pstmt.setString(1, sugang.getSubjectType());
			pstmt.setString(2, sugang.getSubjectType());
			pstmt.setInt(3, sugang.getDeptId());
			pstmt.setInt(4, sugang.getDeptId());
			pstmt.setString(5, sugang.getSubjectName());
			pstmt.setString(6, sugang.getSubjectName());
			pstmt.setInt(7, year);
			pstmt.setInt(8, semester);
			pstmt.setInt(9, limit);
			pstmt.setInt(10, offset);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					sugang = Sugang.builder().collegeName(rs.getString("단과대학")).departName(rs.getString("개설학과"))
							.subjectId(rs.getInt("id")).subjectType(rs.getString("type"))
							.subjectName(rs.getString("강의명")).professorName(rs.getString("담당교수"))
							.grades(rs.getInt("grades")).subjectDay(rs.getString("sub_day"))
							.startTime(rs.getInt("start_time")).endTime(rs.getInt("end_time"))
							.roomId(rs.getString("room_id")).numOfStudent(rs.getInt("num_of_student"))
							.capacity(rs.getInt("capacity")).hasConfirmed(rs.getInt("confirm") == 1)
							.result(rs.getInt("result")).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	// 수강 신청
	@Override
	public int addEnrolment(int studentId, int subjectId, int grade) {
		int rowCount = 0;
		int nomOfStudent = 0;
		int capacity = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmtNumOFStudnet = conn.prepareStatement(NUM_OF_STUDENT);
				PreparedStatement pstmtCapacity = conn.prepareStatement(CAPACITY)) {
			pstmtNumOFStudnet.setInt(1, subjectId);
			pstmtCapacity.setInt(1, subjectId);
			try (ResultSet rsNum = pstmtNumOFStudnet.executeQuery(); ResultSet rsCap = pstmtCapacity.executeQuery()) {
				if (rsNum.next()) {
					nomOfStudent = rsNum.getInt("num_of_student");
				}
				if (rsCap.next()) {
					capacity = rsCap.getInt("capacity");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmtAddSub = conn.prepareStatement(ADD_ENROLMENT_SQL);
					PreparedStatement pstmtAddCount = conn.prepareStatement(UPDATE_ADD_ENROLMENT_SQL)) {
				pstmtAddSub.setInt(1, studentId);
				pstmtAddSub.setInt(2, subjectId);
				pstmtAddSub.setInt(3, grade);
				pstmtAddCount.setInt(1, subjectId);
				if ((capacity - nomOfStudent) > 0) {
					rowCount = pstmtAddSub.executeUpdate();
					pstmtAddCount.executeUpdate();
					conn.commit();
				}
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
	public void resetStudentCount(int subjectId) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmtPre = conn.prepareStatement(PRE_SUBJECT_RESET);
					PreparedStatement pstmtApp = conn.prepareStatement(APP_SUBJECT_RESET);
					PreparedStatement pstmtStu = conn.prepareStatement(NOM_STUDENT_RESET)) {
				pstmtPre.setInt(1, subjectId);
				pstmtApp.setInt(1, subjectId);
				pstmtStu.setInt(1, subjectId);

				pstmtPre.executeUpdate();
				pstmtApp.executeUpdate();
				pstmtStu.executeUpdate();

				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Sugang> getResetPreSubject(int studentId, int year, int semester) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(PRE_TO_APP_SUBJECT)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, studentId);
			pstmt.setInt(3, year);
			pstmt.setInt(4, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().subjectId(rs.getInt("id")).subjectName(rs.getString("강의명"))
							.professorName(rs.getString("담당교수")).grades(rs.getInt("grades"))
							.subjectDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
							.endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
							.numOfStudent(rs.getInt("num_of_student")).capacity(rs.getInt("capacity"))
							.hasConfirmed(false).build();
					sugangList.add(sugang);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sugangList;
	}

	@Override
	public int isTotalGradeWithinLimit(int studentId) {
		int totalGrade = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_TOTAL_GRADES)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					totalGrade = rs.getInt("totalGrade");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalGrade;
	}

	@Override
	public int isPreTotalGradeWithinLimit(int studentId, int year, int semester) {
		int totalGrade = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_PRE_TOTAL_GRADES)) {
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					totalGrade = rs.getInt("totalGrade");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalGrade;
	}

	@Override
	public int getPreSubjectGrade(int studentId, int year, int semester) {
		int totalGrade = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(TOTAL_PRE_SUBJECT_GRADES)) {
			pstmt.setInt(1, studentId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				totalGrade = rs.getInt("총합");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalGrade;
	}

	@Override
	public int submitPreToEnrolment(int studentId, int subjectId) {
		int rowCount = 0;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(SUBMIT_PRE_TO_ENRILMENT)) {
				pstmt.setInt(1, studentId);
				pstmt.setInt(2, subjectId);

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
	public boolean isBreakedApp(int studentId) {
		boolean isBreaked = false;
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(IS_BREAK_APP)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					if (rs.getString("status").equals("휴학")) {
						isBreaked = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isBreaked;
	}

	public boolean isWeeding(int studentId) {
		boolean isBreaked = false;
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(IS_BREAK_APP)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					if (rs.getString("status").equals("제적")) {
						isBreaked = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isBreaked;
	}

}
