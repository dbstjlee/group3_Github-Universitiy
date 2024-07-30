package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.Student;

public interface BreakAppRepository {

	// 휴학 신청
	void addBreakApp(BreakApp breakApp);
	// 휴학 내역 조회
	List<BreakApp> getBreakAppList(int studentId);
	// 휴학 내역 상세 조회
	BreakApp getBreakAppDetail(int breakId);
	// 학생 정보
	Student getStudentInfo(int studentId);
	// 승인 상태인 휴학 내역 모두 확인
	List<BreakApp> getBreakAppByApproval();
	
	int cancleBreakApp(int studentId);
	// 처리중인 휴학 신청 내역 모두 조회
	List<BreakApp> getAllBreakAppInProgress();
	// 휴학 상태 변경
	void updateBreakAppStatus(int breakId, String status);
	
	boolean isSubmitBreakApp(int studentId);
}
