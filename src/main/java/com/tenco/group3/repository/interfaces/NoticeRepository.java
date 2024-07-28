package com.tenco.group3.repository.interfaces;

import java.util.List;

import com.tenco.group3.model.Notice;

public interface NoticeRepository {

	void addNotice(Notice notice); // 공지사항 추가
	void updateNotice(Notice notice); // 공지사항 수정
	void deleteNotice(int id); // 공지사항 삭제
	Notice getNoticeById(int id); // id별 공지사항 조회
	List<Notice> getAllNotice(int limit, int offset); // 모든 공지사항 조회
	int getTotalNoticeCount(); // 공지사항 개수
	List<Notice> searchTitle(String title); // 제목 검색
	List<Notice> searchTitleAndContent(String titleAndcontent); // 제목 + 내용 검색
	void incrementViewCount(int noticeId); // 조회수 상승
}
