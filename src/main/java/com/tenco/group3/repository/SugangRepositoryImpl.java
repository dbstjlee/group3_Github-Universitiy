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
	private static final String ADD_ENROLMENT_SQL = " INSERT INTO pre_stu_sub_tb (student_id, subject_id) "
			+ "VALUES (?,?) ";

	private static final String UPDATE_ADD_ENROLMENT_SQL = " UPDATE subject_tb SET num_of_student = num_of_student + 1 WHERE id = ? ";

	private static final String UPDATE_CANCLE_ENROLMENT_SQL = " UPDATE subject_tb SET num_of_student = num_of_student - 1 WHERE id = ? ";

	private static final String CAPACITY = " SELECT capacity FROM subject_tb WHERE id = ? ";

	private static final String NUM_OF_STUDENT = " SELECT num_of_student FROM subject_tb WHERE id = ? ";

	// 강의 시간표 조회
	private static final String GET_ALL_SUBJECT_LIST = " SELECT co.name as '단과대학', de.name as '개설학과', su.id, su.type, su.name as '강의명', pr.name as '강사명', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity "
			+ " FROM subject_tb as su " + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " order by id asc " + " limit ? offset ? ";

	// 예비 수강 신청
	private static final String GET_APPLICATION_SUBJECT = " SELECT su.id, su.type, su.name as '강의명', pr.name as '강사명', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity "
			+ " FROM subject_tb as su " + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " JOIN pre_stu_sub_tb as ps on ps.subject_id = su.id " + " WHERE ps.student_id = ? "
			+ " order by id asc ";

	// 강의 검색
	private static final String GET_SUBJECT_BY_SEARCH = " SELECT " + "    co.name as '단과대학', "
			+ "    de.name as '개설학과', " + "    su.id, " + "    su.type, " + "    su.name as '강의명', "
			+ "    pr.name as '강사명', " + "    su.grades, " + "    su.sub_day, " + "    su.start_time, "
			+ "    su.end_time, " + "    su.room_id, " + "    su.num_of_student, " + "    su.capacity, "
			+ "    su.dept_id " + " FROM subject_tb as su " + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " WHERE " + "    (? = '전체' OR su.type = ?) " + "    AND (? = -1 OR su.dept_id = ?) "
			+ "    AND (? IS NULL OR su.name = ?) " + " ORDER BY su.id ASC ";

	private static final String COUNT_ALL_BOARDS = " SELECT count(*) as count FROM subject_tb ";

	// 수강 신청 내역
	private static final String GET_APPLICATED_SUBJECT_LIST = " select ps.student_id, ps.subject_id, su.name as '강의명', pr.name as '담당교수', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity "
			+ " from pre_stu_sub_tb as ps " + " left join subject_tb as su on ps.subject_id = su.id "
			+ " left Join professor_tb as pr on su.professor_id = pr.id " + " where ps.student_id = ? ";

	// 총 학점
	private static final String TOTAL_SUBJECT_GRADES = " select sum(su.grades) as '총합' "
			+ " from pre_stu_sub_tb as ps "
			+ " left join subject_tb as su on ps.subject_id = su.id "
			+ " where ps.student_id = ? ";
	
	
	@Override
	public List<Sugang> getAllSubject(int limit, int offset) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);
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
	public List<Sugang> getApplicationSubject(int studentId) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_APPLICATION_SUBJECT)) {
			pstmt.setInt(1, studentId);
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
	public int addEnrolment(int studentId, int subjectId) {
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
			try (PreparedStatement pstmtAddEnorolment = conn.prepareStatement(ADD_ENROLMENT_SQL);
					PreparedStatement pstmtAddCount = conn.prepareStatement(UPDATE_ADD_ENROLMENT_SQL)) {
				pstmtAddEnorolment.setInt(1, studentId);
				pstmtAddEnorolment.setInt(2, subjectId);
				pstmtAddCount.setInt(1, subjectId);
				if ((capacity - nomOfStudent) > 0) {
					rowCount = pstmtAddEnorolment.executeUpdate();
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
	public List<Sugang> getSubjectBySearch(Sugang sugang) {
		List<Sugang> sugangList = new ArrayList<Sugang>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SUBJECT_BY_SEARCH)) {
			pstmt.setString(1, sugang.getSubjectType());
			pstmt.setString(2, sugang.getSubjectType());
			pstmt.setInt(3, sugang.getDeptId());
			pstmt.setInt(4, sugang.getDeptId());
			pstmt.setString(5, sugang.getSubjectName());
			pstmt.setString(6, sugang.getSubjectName());

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
	public int cancelEnrolment(Sugang sugang) {
		int rowCount = 0;
		int nomOfStudent = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmtNumOFStudnet = conn.prepareStatement(NUM_OF_STUDENT);) {
			pstmtNumOFStudnet.setInt(1, sugang.getSubjectId());
			try (ResultSet rsNum = pstmtNumOFStudnet.executeQuery();) {
				if (rsNum.next()) {
					nomOfStudent = rsNum.getInt("num_of_student");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (nomOfStudent > 0) {
			try (Connection conn = DBUtil.getConnection()) {
				conn.setAutoCommit(false);
				try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_CANCLE_ENROLMENT_SQL)) {
					pstmt.setInt(1, sugang.getSubjectId());
					rowCount = pstmt.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	@Override
	public int getAllSubjectCount() {
		int subjectCount = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_BOARDS)) {
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
	public List<Sugang> getApplicatedSubjectList(int studentId) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_APPLICATION_SUBJECT)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Sugang sugang = Sugang.builder().subjectId(rs.getInt("id")).subjectName(rs.getString("강의명"))
							.professorName(rs.getString("강사명")).grades(rs.getInt("grades"))
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
	public int getSubjectGrade(int studentId) {
		int totalGrade = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(TOTAL_SUBJECT_GRADES)) {
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

}
