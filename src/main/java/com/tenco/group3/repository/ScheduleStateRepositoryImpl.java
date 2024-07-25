package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.repository.interfaces.ScheduleStateRepository;
import com.tenco.group3.util.DBUtil;

public class ScheduleStateRepositoryImpl implements ScheduleStateRepository {
	
	private static final String SELECT_SCHEDULE_STATE = " SELECT * FROM schedule_state_tb "; 
	
	@Override
	public ScheduleState getAllScheduleStates() {
		ScheduleState scheduleState = null;
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SCHEDULE_STATE)) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				scheduleState = ScheduleState.builder()
						.breakApp(rs.getBoolean("break_app"))
						.sugang(rs.getBoolean("sugang"))
						.tuition(rs.getBoolean("tuition"))
						.year(rs.getInt("year"))
						.semester(rs.getInt("semester"))
						.build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleState;
	}
	
}
