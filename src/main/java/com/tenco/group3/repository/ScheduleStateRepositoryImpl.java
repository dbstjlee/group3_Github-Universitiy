package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.repository.interfaces.ScheduleStateRepository;
import com.tenco.group3.util.DBUtil;

public class ScheduleStateRepositoryImpl implements ScheduleStateRepository {

	private static final String SELECT_SCHEDULE_STATE = " SELECT * FROM schedule_state_tb ORDER BY year, semester DESC LIMIT 1 ";
	private static final String SELECT_ALL_YEARS = " SELECT year FROM schedule_state_tb ";
	private static final String UPDATE_SCHEDULE = " UPDATE schedule_state_tb SET ";
	
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
	public List<Integer> getAllYears() {
		List<Integer> yearList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_YEARS)) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				yearList.add(rs.getInt("year"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearList;
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
	
}
