package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.ScheduleState;

public interface ScheduleStateRepository {

	// 학사일정 모두 조회
	ScheduleState getAllScheduleStates();

	// 학사일정 스케줄 상태 변경
	void updateSchedule(String columName, int state);
	
	// 다음 학기 학사 스케쥴 생성
	void addSchedule();
	
}
