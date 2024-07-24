package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	private static final String NUM_OF_STUDENT = " SELECT capacity FROM subject_tb WHERE id = ? ";

	private static final String CAPACITY = " SELECT num_of_student FROM subject_tb WHERE id = ? ";

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

	// 학과별 검색
	private static final String GET_SUBJECT_BY_DEPARTMENT = " SELECT co.name as '단과대학', de.name as '개설학과', su.id, su.type, su.name as '강의명', pr.name as '강사명', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity"
			+ " FROM subject_tb as su n" + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " where de.name = ? " + " order by id asc " + " limit ? offset ? ";
	// 전공, 교양별 검색
	private static final String GET_SUBJECT_BY_TYPE = " SELECT co.name as '단과대학', de.name as '개설학과', su.id, su.type, su.name as '강의명', pr.name as '강사명', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity"
			+ " FROM subject_tb as su n" + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " where su.type = ? " + " order by id asc " + " limit ? offset ? ";
	// 강의명 검색
	private static final String GET_SUBJECT_BY_NAME = " SELECT co.name as '단과대학', de.name as '개설학과', su.id, su.type, su.name as '강의명', pr.name as '강사명', su.grades, su.sub_day, su.start_time, su.end_time,su.room_id, su.num_of_student, su.capacity"
			+ " FROM subject_tb as su n" + " JOIN professor_tb as pr on pr.id = su.professor_id "
			+ " JOIN department_tb as de on de.id = su.dept_id " + " JOIN college_tb as co on co.id = de.college_id "
			+ " where su.name = ? " + " order by id asc " + " limit ? offset ? ";

	@Override
	public List<Sugang> getAllSubject(int page, int offset) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)) {
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
	public int addEnrolment(int studentId, Sugang sugang) {
		int rowCount = 0;
		int nomOfStudent = 0;
		int capacity = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmtNumOFStudnet = conn.prepareStatement(NUM_OF_STUDENT);
				PreparedStatement pstmtCapacity = conn.prepareStatement(CAPACITY)) {
			pstmtNumOFStudnet.setInt(1, sugang.getSubjectId());
			pstmtCapacity.setInt(1, sugang.getSubjectId());
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
				pstmtAddEnorolment.setInt(2, sugang.getSubjectId());
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
	public List<Sugang> getSubjectByDeptName(Sugang sugang, int page, int offset) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)) {
			pstmt.setString(1, sugang.getDepartName());
			pstmt.setInt(2, page);
			pstmt.setInt(3, offset);
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
	public List<Sugang> getSubjectByType(Sugang sugang, int page, int offset) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)) {
			pstmt.setString(1, sugang.getSubjectType());
			pstmt.setInt(2, page);
			pstmt.setInt(3, offset);
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
	public List<Sugang> getSubjectBySubjectName(Sugang sugang, int page, int offset) {
		List<Sugang> sugangList = new ArrayList<Sugang>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)) {
			pstmt.setString(1, sugang.getSubjectName());
			pstmt.setInt(2, page);
			pstmt.setInt(3, offset);
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

}
