package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Notice;

public interface NoticeRepository {

	void addNotice(Notice notice);
	void updateNotice(Notice notice);
	void deleteNotice(int id);
	Notice getNoticeById(int id);
	List<Notice> getAllNotice(int limit, int offset);
	int getTotalNoticeCount();
	
	// TODO - 검색 기능 2개
}
