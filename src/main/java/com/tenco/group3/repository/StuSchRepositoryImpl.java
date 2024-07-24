package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.repository.interfaces.StuSchRepository;
import com.tenco.group3.util.DBUtil;
import com.tenco.group3.util.SemesterUtil;

public class StuSchRepositoryImpl implements StuSchRepository {

	private static final String INSERT_STUSCH = " INSERT INTO stu_sch_tb (student_id, sch_year, semester, sch_type) VALUES ";

	@Override
	public void insertStuSch(List<RankedStudent> rankedStudentList) {
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append(INSERT_STUSCH);
		for (int i = 0; i < rankedStudentList.size(); i++) {
			insertQuery.append(" (?,?,?,?)");
			if (i < rankedStudentList.size() - 1) {
				insertQuery.append(",");
			}
		}
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(insertQuery.toString())) {
				int count = 1;
				for (RankedStudent rankedStudent : rankedStudentList) {
					int schType = rankedStudent.getRanking();
					if (schType > 1) {
						schType = 2;
					}
					pstmt.setInt(count++, rankedStudent.getStudentId());
					pstmt.setInt(count++, SemesterUtil.getCurrentYear());
					pstmt.setInt(count++, SemesterUtil.getCurrentSemester());
					pstmt.setInt(count++, schType);
				}
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

}
