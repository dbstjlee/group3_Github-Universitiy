package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Subject;
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

	@Override
	public List<Subject> getAllSubject() {
		List<Subject> subjectList = new ArrayList<Subject>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUBJECT_LIST)){
			try (ResultSet rs = pstmt.executeQuery()){
				while (rs.next()) {
					
				}
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			
		}
		return subjectList;
	}

	@Override
	public List<Subject> getApplicationSubject(int studentId) {

		return null;
	}

	@Override
	public List<Subject> getApplicationSubject(int studentId, String state) {

		return null;
	}

	@Override
	public void addEnrolment(int studentId, Subject subject) {
//		subject.getId()

	}

	@Override
	public List<Subject> getSubjectByDeptName(Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByType(Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectBySubjectName(Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

}
