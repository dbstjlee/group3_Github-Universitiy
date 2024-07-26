package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.repository.interfaces.ScheduleStateRepository;
import com.tenco.group3.util.DBUtil;
import com.tenco.group3.util.SemesterUtil;

public class ScheduleStateRepositoryImpl implements ScheduleStateRepository {

	private static final String SELECT_SCHEDULE_STATE = " SELECT * FROM schedule_state_tb ORDER BY year, semester DESC LIMIT 1 ";
	private static final String UPDATE_SCHEDULE = " UPDATE schedule_state_tb SET ";
	private static final String INSERT_SCHEDULE = " INSERT INTO schedule_state_tb (year, semester) VALUES (?, ?) ";
	
	@Override
	public ScheduleState getAllScheduleStates() {
		ScheduleState scheduleState = null;
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SCHEDULE_STATE)) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				scheduleState = ScheduleState.builder()
					.breakApp(rs.getInt("break_app"))
					.sugang(rs.getInt("sugang"))
					.tuition(rs.getInt("tuition"))
					.year(rs.getInt("year"))
					.semester(rs.getInt("semester"))
					.build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleState;
	}

	@Override
	public void updateSchedule(String columName, int state) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			String query = UPDATE_SCHEDULE + columName + " = ? ";
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, state);
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
	public void addSchedule() {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_SCHEDULE)) {
				pstmt.setInt(1, SemesterUtil.getAfterYear());
				pstmt.setInt(2, SemesterUtil.getAfterSemester());
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
