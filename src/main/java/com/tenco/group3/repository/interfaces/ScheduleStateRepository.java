package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.ScheduleState;

public interface ScheduleStateRepository {

	// 학사일정 모두 조회
	ScheduleState getAllScheduleStates();

	// 존재하는 학사일정 년도 조회
	List<Integer> getAllYears();

	// 학사일정 스케줄 상태 변경
	void updateSchedule(String columName, int state);

}
