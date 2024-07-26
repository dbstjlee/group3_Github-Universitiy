package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Student;
import com.tenco.group3.repository.interfaces.StuStatRepository;
import com.tenco.group3.util.DBUtil;

public class StuStatRepositoryImpl implements StuStatRepository {

	private static final String UPDATE_DATE = " UPDATE stu_stat_tb SET to_date = CURDATE() ";
	private static final String INSERT_STATUS_BY_ID = " INSERT INTO stu_stat_tb (student_id, status, from_date) VALUES ";
	private static final String SELECT_CURRENT_GRADE = " SELECT student_id, grade, semester FROM stu_stat_tb ss JOIN student_tb s ON ss.student_id = s.id WHERE ss.to_date = '9999-01-01' AND ss.status IN ('입학','복학') ";

	@Override
	public int updateStatusById(List<Integer> idList, String status) {
		int rowCount = 0;
		if (idList.size() == 0) {
			return rowCount;
		}
		StringBuffer updateQuery = new StringBuffer();
		StringBuffer insertQuery = new StringBuffer();
		updateQuery.append(UPDATE_DATE);
		updateQuery.append("  WHERE student_id IN (");
		insertQuery.append(INSERT_STATUS_BY_ID);
		for (int i = 0; i < idList.size(); i++) {
			updateQuery.append("?");
			insertQuery.append(" (?,?,CURDATE())");
			if (i < idList.size() - 1) {
				updateQuery.append(",");
				insertQuery.append(",");
			} else {
				updateQuery.append(")");
			}
		}
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(updateQuery.toString())) {
				int count = 1;
				for (Integer integer : idList) {
					pstmt.setInt(count++, integer);
				}
				rowCount = pstmt.executeUpdate();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return rowCount;
			}
			try (PreparedStatement pstmt = conn.prepareStatement(insertQuery.toString())) {
				int count = 1;
				for (Integer integer : idList) {
					pstmt.setInt(count++, integer);
					pstmt.setString(count++, status);
				}
				rowCount = pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				return rowCount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	@Override
	public List<Student> getCurrentGrade() {
		List<Student> studentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_CURRENT_GRADE)) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				studentList.add(Student.builder()
					.id(rs.getInt("student_id"))
					.grade(rs.getInt("grade"))
					.semester(rs.getInt("semester"))
					.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}
}
