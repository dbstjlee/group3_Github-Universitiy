package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Notice;
import com.tenco.group3.repository.interfaces.NoticeRepository;
import com.tenco.group3.util.DBUtil;

public class NoticeRepositoryImpl implements NoticeRepository {

	
	private static final String INSERT_NOTICE_SQL = " INSERT INTO notice_tb(category, title, content) "
			+ " values( ? , ? , ? ); ";
	private static final String UPDATE_NOTICE_SQL = " UPDATE notice_tb SET title = ? , content = ? "
			+ " WHERE id = ? ; ";
	private static final String DELETE_NOTICE_SQL = " DELETE FROM notice_tb WHERE id = ? ; ";
	
	private static final String SELECT_NOTICE_BY_ID = " SELECT * FROM notice_tb WHERE id = ? ; ";
	
	private static final String SELECT_ALL_NOTICES = " SELECT * FROM notice_tb ORDER BY id DESC limit ? offset ? ";
	
	private static final String COUNT_ALL_NOTICES = " SELECT count(*) AS count FROM notice_tb; ";
	
	
	// TODO - 검색
	
	
	/**
	 * 공지사항 등록
	 */
	@Override
	public void addNotice(Notice notice) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_NOTICE_SQL)){
				pstmt.setString(1, notice.getCategory());
				pstmt.setString(2, notice.getTitle());
				pstmt.setString(3, notice.getContent());
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
	 * 공지사항 수정
	 */
	@Override
	public void updateNotice(Notice notice) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_NOTICE_SQL)){
				pstmt.setString(1, notice.getTitle());
				pstmt.setString(2, notice.getContent());
				pstmt.setInt(3, notice.getId());
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
	 * 공지사항 삭제
	 */
	@Override
	public void deleteNotice(int id) {
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_NOTICE_SQL)){
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
	 * 공지사항 id 기준으로 조회
	 */
	@Override
	public Notice getNoticeById(int id) {
		Notice notice = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_NOTICE_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					notice = Notice.builder()
							.id(rs.getInt("id"))
							.category(rs.getString("category"))
							.title(rs.getString("title"))
							.content(rs.getString("content"))
							.createdTime(rs.getTimestamp("created_time"))
							.views(rs.getInt("views"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notice;
	}

	/**
	 * 모든 공지사항 조회
	 */
	@Override
	public List<Notice> getAllNotice(int limit, int offset) {
		List<Notice> noticeList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_NOTICES)){
				pstmt.setInt(1, limit);
				pstmt.setInt(2, offset);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					noticeList.add(
							Notice.builder()
							.id(rs.getInt("id"))
							.category(rs.getString("category"))
							.title(rs.getString("title"))
							.content(rs.getString("content"))
							.createdTime(rs.getTimestamp("created_time"))
							.views(rs.getInt("views"))
							.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noticeList;
	}

	/**
	 * 공지사항 개수
	 */
	@Override
	public int getTotalNoticeCount() {
		int totalNotices = 0;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_NOTICES)){
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					totalNotices = rs.getInt("count");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalNotices;
	}
}
