package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.repository.interfaces.BreakAppRepository;
import com.tenco.group3.util.DBUtil;

public class BreakAppRepositoryImpl implements BreakAppRepository {

	@Override
	public void addBreakApp(BreakApp breakApp) {
		String query = " INSERT INTO break_app_tb " + " (student_id, student_grade, from_year, from_semester, to_year, "
				+ " to_semester, type) " + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, breakApp.getStudentId());
				pstmt.setInt(2, breakApp.getStudentGrade());
				pstmt.setInt(3, breakApp.getFromYear());
				pstmt.setInt(4, breakApp.getFromSemester());
				pstmt.setInt(5, breakApp.getToYear());
				pstmt.setInt(6, breakApp.getToSemester());
				pstmt.setString(7, breakApp.getType());
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
	public List<BreakApp> getBreakAppList(int studentId) {
		String sql = " SELECT br.*, st.name AS studentName, de.name as departmentname " + " FROM break_app_tb AS br "
				+ " JOIN student_tb AS st ON br.student_id = st.id "
				+ " JOIN department_tb as de on st.dept_id = de.id  " + " WHERE st.id = ? ";
		List<BreakApp> breakAppList = new ArrayList<BreakApp>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, studentId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BreakApp app = BreakApp.builder().type(rs.getString("type")).id(rs.getInt("id"))
							.departmentName(rs.getString("departmentname")).studentId(rs.getInt("student_id"))
							.studentGrade(rs.getInt("student_grade")).studentName(rs.getString("studentName"))
							.fromYear(rs.getInt("from_year")).fromSemester(rs.getInt("from_semester"))
							.toYear(rs.getInt("to_year")).toSemester(rs.getInt("to_semester"))
							.appDate(rs.getString("app_date")).status(rs.getString("status"))
							.build();
					breakAppList.add(app);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakAppList;
	}

	@Override
	public BreakApp getBreakAppDetail(int breakId) {
		BreakApp breakApp = null;
		String sql = " SELECT br.*, st.name AS studentName, de.name as departmentname, st.address, st.tel, co.name as collegename "
				+ " FROM break_app_tb AS br " + " JOIN student_tb AS st ON br.student_id = st.id "
				+ " JOIN department_tb as de on st.dept_id = de.id JOIN college_tb as co ON co.id = de.college_id "
				+ "WHERE br.id = ? ";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, breakId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					breakApp = BreakApp.builder().type(rs.getString("type"))
							.departmentName(rs.getString("departmentname")).studentId(rs.getInt("student_id"))
							.studentGrade(rs.getInt("student_grade")).studentTel(rs.getString("tel"))
							.studentAdds(rs.getString("address")).studentName(rs.getString("studentName"))
							.fromYear(rs.getInt("from_year")).fromSemester(rs.getInt("from_semester"))
							.toYear(rs.getInt("to_year")).toSemester(rs.getInt("to_semester"))
							.appDate(rs.getString("app_date")).status(rs.getString("status"))
							.collegeName(rs.getString("collegename"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breakApp;
	}

}
