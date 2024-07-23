package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.BreakApp;

public interface BreakAppRepository {

	// 휴학 신청
	void addBreakApp(BreakApp breakApp);
	// 휴학 내역 조회
	List<BreakApp> getBreakAppList(int studentId);
	// 휴학 내역 상세 조회
	BreakApp getBreakAppDetail(int breakId);
}
