package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.College;
import com.tenco.group3.model.Room;
import com.tenco.group3.repository.interfaces.RoomRepository;
import com.tenco.group3.util.DBUtil;

public class RoomRepositoryImpl implements RoomRepository{

	private static final String SELECT_ALL_ROOM = " select * from room_tb ";
	private static final String ADD_ROOM = " INSERT INTO room_tb (id, college_id) VALUES (?, ?) ";
	private static final String DELETE_ROOM = " DELETE FROM room_tb WHERE id = ? ";
	private static final String SELECT_ROOM_BY_ID = " SELECT * FROM room_tb WHERE id = ? ";
	
	// 강의실 전체조회
	@Override
	public List<Room> getAllRoom() {
		List<Room> rooms = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_ROOM)) {
			ResultSet rs = pstmt.executeQuery(); 
			while (rs.next()) {
				rooms.add(Room.builder().id(rs.getString("id")).collegeId(rs.getInt("college_id")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rooms;
	}

	
	// 강의실 등록
	@Override
	public int addRoom(Room room) {
			int resultCount = 0;
			try (Connection conn = DBUtil.getConnection()) {
				conn.setAutoCommit(false);
				try (PreparedStatement pstmt = conn.prepareStatement(ADD_ROOM)) {
					pstmt.setString(1, room.getId());
					pstmt.setInt(2, room.getCollegeId());
					resultCount = pstmt.executeUpdate();
					conn.commit();
				} catch (Exception e) {
					conn.rollback();
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultCount;
		}

	
	// 강의실 삭제
	@Override
	public int deleteRoom(int id) {
		  int resultCount = 0;
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(DELETE_ROOM)) {
	            conn.setAutoCommit(false);
	            pstmt.setInt(1, id);
	            resultCount = pstmt.executeUpdate();

	            if (resultCount > 0) {
	                conn.commit();
	            } else {
	                conn.rollback();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            try (Connection conn = DBUtil.getConnection()) {
	                conn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return resultCount;
	    }

	@Override
	public Room getRoomById(int id) {
		Room room = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ROOM_BY_ID)){
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					room = Room.builder()
							.id(rs.getString("id"))
							.collegeId(rs.getInt("college_id"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return room;
	}

}
