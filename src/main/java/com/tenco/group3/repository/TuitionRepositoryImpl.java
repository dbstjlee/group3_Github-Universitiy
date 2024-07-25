package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.Tuition;
import com.tenco.group3.repository.interfaces.TuitionRepository;
import com.tenco.group3.util.DBUtil;
import com.tenco.group3.util.SemesterUtil;

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
	private static final String SELECT_TUITIONS = " SELECT s.id AS student_id, ct.amount AS tui_amount, IFNULL(sch.type,0) AS sch_type, IFNULL((ct.amount * sch.max_amount / 100),0) AS sch_amount "
			+ " FROM stu_sch_tb ssc "
			+ " JOIN scholarship_tb sch ON ssc.sch_type = sch.type "
			+ " RIGHT JOIN student_tb s ON s.id = ssc.student_id "
			+ " JOIN department_tb d ON s.dept_id = d.id "
			+ " JOIN coll_tuit_tb ct ON d.college_id = ct.college_id "
			+ " JOIN stu_stat_tb ss ON s.id = ss.student_id AND ss.to_date = '9999-01-01' "
			+ " WHERE ss.status IN ('입학','복학') ";
	private static final String ADD_ALL_TUITIONS = " INSERT INTO tuition_tb (student_id, tui_year, semester, tui_amount, sch_type, sch_amount) VALUES ";

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

	@Override
	public List<Tuition> getTuitions() {
		List<Tuition> tutionList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_TUITIONS)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Tuition tuition = Tuition.builder()
							.studentId(rs.getInt("student_id"))							
							.year(SemesterUtil.getCurrentYear()).semester(SemesterUtil.getCurrentSemester())
							.scholarType(rs.getInt("sch_type")).tuiAmount(rs.getInt("tui_amount"))
							.scholarAmount(rs.getInt("sch_amount")).build();
					tutionList.add(tuition);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tutionList;
	}

	@Override
	public int addAllTuitions(List<Tuition> tuitionList) {
		int rowCount = 0;
		StringBuffer insertQuery = new StringBuffer();
		insertQuery.append(ADD_ALL_TUITIONS);
		for (int i = 0; i < tuitionList.size(); i++) {
			insertQuery.append(" (?,?,?,?,?,?)");
			if (i < tuitionList.size() - 1) {
				insertQuery.append(",");
			}
		}
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(insertQuery.toString())) {
				int count = 1;
				for (Tuition tuition : tuitionList) {
					pstmt.setInt(count++, tuition.getStudentId());
					pstmt.setInt(count++, tuition.getYear());
					pstmt.setInt(count++, tuition.getSemester());
					pstmt.setInt(count++, tuition.getTuiAmount());
					pstmt.setInt(count++, tuition.getScholarType());
					pstmt.setInt(count++, tuition.getScholarAmount());
				}
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

}
