package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Room;

public interface RoomRepository {

	List<Room> getAllRoom(); // 강의실 전체 조회
	int addRoom(Room room); // 강의실 등록
	int deleteRoom(int id); // 강의실 삭제
	Room getRoomById(int id);
	
}
