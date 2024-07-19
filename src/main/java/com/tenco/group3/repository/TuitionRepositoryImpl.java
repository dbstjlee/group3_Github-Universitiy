package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.Tuition;
import com.tenco.group3.repository.interfaces.TuitionRepository;
import com.tenco.group3.util.DBUtil;

public class TuitionRepositoryImpl implements TuitionRepository {

	private static final String GET_TUITION_BY_STUDENTID = "SELECT " + " st.id AS studentId,"
			+ " st.name AS studentName, " + " de.name AS deptName, " + " co.name AS collgeName, "
			+ " ct.amount AS collTution, " + " ss.sch_type AS scholarType, " + " sc.max_amount AS scholar, "
			+ " (ct.amount - sc.max_amount) AS totaltution " + " FROM student_tb AS st "
			+ " JOIN department_tb AS de ON st.dept_id = de.id " + " JOIN college_tb AS co ON de.college_id = co.id "
			+ " JOIN coll_tuit_tb AS ct ON co.id = ct.college_id " + " JOIN stu_sch_tb AS ss ON st.id = ss.student_id "
			+ " JOIN scholarship_tb AS sc ON sc.type = ss.sch_type " + " WHERE st.id = ?";

	@Override
	public Tuition getTuitionByStudentId(int studentId) {
		Tuition tuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_TUITION_BY_STUDENTID)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tuition = Tuition.builder().studentId(rs.getInt("studentId"))
							.studentName(rs.getString("studentName")).deptName(rs.getString("deptName"))
							.collgeName(rs.getString("collgeName")).collTution(rs.getInt("collTution"))
							.scholarType(rs.getString("scholarType"))
							.scholar(rs.getInt("scholar")).totalTuition(rs.getInt("totaltution")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuition;
	}

}
