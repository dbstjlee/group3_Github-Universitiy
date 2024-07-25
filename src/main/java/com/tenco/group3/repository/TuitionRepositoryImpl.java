package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.Tuition;
import com.tenco.group3.repository.interfaces.TuitionRepository;
import com.tenco.group3.util.DBUtil;

public class TuitionRepositoryImpl implements TuitionRepository {

	private static final String GET_TUITION_BY_STUDENTID = " SELECT " + "	st.id AS studentId, "
			+ "    st.name AS studentName, " + " de.name AS deptName, " + " co.name AS collgeName, "
			+ "    ct.amount AS collTution, " + "    tu.sch_type AS scholarType, " + "	tu.tui_amount AS scholar, "
			+ "    (tu.tui_amount - tu.sch_amount) AS totaltution, tu.status as status " + " FROM "
			+ "    student_tb AS st " + " JOIN " + " department_tb AS de ON st.dept_id = de.id " + " JOIN "
			+ "    college_tb AS co ON de.college_id = co.id " + " JOIN "
			+ "    coll_tuit_tb AS ct ON co.id = ct.college_id " + " JOIN "
			+ "	 tuition_tb as tu ON tu.student_id = st.id " + " WHERE " + " st.id = ? ";

	private static final String GET_SUMMARY_TUTION_BY_STUDNETID = " SELECT *, (tui_amount - sch_amount) AS total_amount FROM tuition_tb WHERE student_id = ? ";

	@Override
	public Tuition getTuitionByStudentId(int studentId) {
		Tuition tuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_TUITION_BY_STUDENTID)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tuition = Tuition.builder().studentId(rs.getInt("student_id"))
							.studentName(rs.getString("student_name")).deptName(rs.getString("dept_name"))
							.collgeName(rs.getString("collage_name")).collAmount(rs.getInt("collage_amount"))
							.scholarType(rs.getInt("sch_type")).scholarAmount(rs.getInt("sch_amount"))
							.totalAmount(rs.getInt("total_amount")).status(rs.getInt("status")).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tuition;
	}

	@Override
	public Tuition getSummaryTuitionByStudentId(int studentId) {
		Tuition tuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SUMMARY_TUTION_BY_STUDNETID)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tuition = Tuition.builder().year(rs.getInt("tui_year")).semester(rs.getInt("semester"))
							.scholarType(rs.getInt("sch_type")).collAmount(rs.getInt("tui_amount"))
							.scholarAmount(rs.getInt("sch_amount")).status(rs.getInt("status"))
							.totalAmount(rs.getInt("total_amount")).build();
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
