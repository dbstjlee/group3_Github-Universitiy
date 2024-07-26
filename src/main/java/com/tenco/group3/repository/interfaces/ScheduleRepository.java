package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Schedule;

public interface ScheduleRepository {

	List<Schedule> getAllScheduleList();
	List<Schedule> getAllScheduleListById();
	List<Schedule> getAllSchedule(int limit);
	Schedule getScheduleById(int id);
	void updateSchedule(Schedule schedule);
	void deleteSchedule(int id);
	void addSchedule(Schedule schedule);
}
