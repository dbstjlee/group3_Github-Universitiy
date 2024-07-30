package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.StuSubDetail;
import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.interfaces.StuSubDetailRepository;
import com.tenco.group3.util.DBUtil;

public class StuSubDetailRepositoryImpl implements StuSubDetailRepository {

	private static final String SELECT_ALL_DETAIL_BY_SUB_ID = " SELECT ssd.*, d.name AS dept_name, s.name AS student_name FROM stu_sub_detail_tb ssd "
			+ " JOIN student_tb s ON ssd.student_id = s.id " + " JOIN department_tb d ON s.dept_id = d.id " + " WHERE subject_id = ? ";
	private static final String SELECT_DETAIL_BY_ID = " SELECT ssd.*, d.name AS dept_name, s.name AS student_name FROM stu_sub_detail_tb ssd "
			+ " JOIN student_tb s ON ssd.student_id = s.id " + " JOIN department_tb d ON s.dept_id = d.id " + " WHERE ssd.id = ? ";
	private static final String UPDATE_DETAIL_BY_ID = " UPDATE stu_sub_detail_tb SET absent = ? , lateness = ?, homework = ?, mid_exam = ?, final_exam = ?, converted_mark = ? WHERE id = ? ";
	private static final String SELECT_SUBJECT_BY_ID = " SELECT ssd.subject_id, s.name FROM stu_sub_detail_tb ssd JOIN subject_tb s ON ssd.subject_id = s.id WHERE ssd.id = ? "; 

	@Override
	public List<StuSubDetail> getAllDetailBySubjectId(int subjectId) {
		List<StuSubDetail> stuSubDetailList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_DETAIL_BY_SUB_ID)) {
			pstmt.setInt(1, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				stuSubDetailList.add(StuSubDetail.builder()
					.id(rs.getInt("id"))
					.studentId(rs.getInt("student_id"))
					.subjectId(rs.getInt("subject_id"))
					.absent(rs.getInt("absent"))
					.lateness(rs.getInt("lateness"))
					.homework(rs.getInt("homework"))
					.midExam(rs.getInt("mid_exam"))
					.finalExam(rs.getInt("final_exam"))
					.convertedMark(rs.getInt("converted_mark"))
					.deptName(rs.getString("dept_name"))
					.studentName(rs.getString("student_name"))
					.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stuSubDetailList;
	}

	@Override
	public StuSubDetail getDetailById(int id) {
		StuSubDetail stuSubDetail = null;
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SELECT_DETAIL_BY_ID)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				stuSubDetail = StuSubDetail.builder()
					.id(rs.getInt("id"))
					.studentId(rs.getInt("student_id"))
					.subjectId(rs.getInt("subject_id"))
					.absent(rs.getInt("absent"))
					.lateness(rs.getInt("lateness"))
					.homework(rs.getInt("homework"))
					.midExam(rs.getInt("mid_exam"))
					.finalExam(rs.getInt("final_exam"))
					.convertedMark(rs.getInt("converted_mark"))
					.deptName(rs.getString("dept_name"))
					.studentName(rs.getString("student_name"))
					.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stuSubDetail;
	}

	@Override
	public void updateDetailById(StuSubDetail stuSubDetail) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_DETAIL_BY_ID)) {
				pstmt.setInt(1, stuSubDetail.getAbsent());
				pstmt.setInt(2, stuSubDetail.getLateness());
				pstmt.setInt(3, stuSubDetail.getHomework());
				pstmt.setInt(4, stuSubDetail.getMidExam());
				pstmt.setInt(5, stuSubDetail.getFinalExam());
				pstmt.setInt(6, stuSubDetail.getConvertedMark());
				pstmt.setInt(7, stuSubDetail.getId());
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
	public Subject getSubjectByDetailId(int id) {
		Subject subject = null;
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(SELECT_SUBJECT_BY_ID)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				subject = Subject.builder()
					.id(rs.getInt("subject_id"))
					.name(rs.getString("name"))
					.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}

}
