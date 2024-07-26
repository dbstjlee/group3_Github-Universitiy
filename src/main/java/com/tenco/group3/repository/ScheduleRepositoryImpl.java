package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Schedule;
import com.tenco.group3.repository.interfaces.ScheduleRepository;
import com.tenco.group3.util.DBUtil;

public class ScheduleRepositoryImpl implements ScheduleRepository {

	
	private static final String SELECT_SCHEDULE= " SELECT start_day, end_day, information FROM schedule_tb; ";
	private static final String SELECT_SCHEDULE_ORDER_BY_ID = " SELECT id, start_day, end_day, information FROM schedule_tb; ";
	private static final String SELECT_SCHEDULE_BY_ID = " SELECT id, start_day, end_day, information FROM schedule_tb WHERE id = ? ; ";
	private static final String UPDATE_SCHEDULE_DETAIL = " UPDATE schedule_tb SET start_day = ? , end_day = ? , information = ? WHERE id = ? ; ";
	private static final String DELETE_SCHEDULE_DETAIL = " DELETE FROM schedule_tb WHERE id = ? ; ";
	private static final String ADD_SCHEDULE_DETAIL = " INSERT INTO schedule_tb(staff_id, start_day, end_day, information) values ( ? , ? , ? , ? ); ";
//	private static final String SELECT_ALL_INFO = " SELECT * FROM schedule_tb";
	
	/**
	 * 전체 학사일정 조회
	 */
	@Override
	public List<Schedule> getAllScheduleList() {
		List<Schedule> scheduleList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_SCHEDULE)){
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					scheduleList.add(
					 			Schedule.builder()
								.startDay(rs.getDate("start_day"))
								.endDay(rs.getDate("end_day"))
								.information(rs.getString("information"))
								.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleList;
	}

	/**
	 * id로 학사 일정 전체 조회
	 */
	@Override
	public List<Schedule> getAllScheduleListById() {
		List<Schedule> scheduleListById = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_SCHEDULE_ORDER_BY_ID)){
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					scheduleListById.add(
					 			Schedule.builder()
					 			.id(rs.getInt("id"))
								.startDay(rs.getDate("start_day"))
								.endDay(rs.getDate("end_day"))
								.information(rs.getString("information"))
								.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleListById;
	}

	/**
	 * id로 학사 일정 개별 조회
	 */
	@Override
	public Schedule getScheduleById(int id) {
		Schedule schedule = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_SCHEDULE_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					schedule = Schedule.builder()
								.id(rs.getInt("id"))
								.startDay(rs.getDate("start_day"))
								.endDay(rs.getDate("end_day"))
								.information(rs.getString("information"))
								.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedule;
	}

	/**
	 * 학사 일정 수정
	 */
	@Override
	public void updateSchedule(Schedule schedule) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_SCHEDULE_DETAIL)){
				pstmt.setDate(1, (Date) schedule.getStartDay()); // TODO - DATE 확인
				pstmt.setDate(2, (Date) schedule.getEndDay());
				pstmt.setString(3, schedule.getInformation());
				pstmt.setInt(4, schedule.getId());
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

	/**
	 * 학사 일정 삭제
	 */
	@Override
	public void deleteSchedule(int id) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_SCHEDULE_DETAIL)){
				pstmt.setInt(1, id);
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

	/**
	 * 학사 일정 추가
	 */
	@Override
	public void addSchedule(Schedule schedule) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(ADD_SCHEDULE_DETAIL)){
				pstmt.setInt(1, schedule.getStaffId());
				pstmt.setDate(2, (Date) schedule.getStartDay());
				pstmt.setDate(3, (Date) schedule.getEndDay());
				pstmt.setString(4, schedule.getInformation());
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
