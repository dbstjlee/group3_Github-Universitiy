package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.tenco.group3.model.College;
import com.tenco.group3.repository.interfaces.CollegeRepository;
import com.tenco.group3.util.DBUtil;

public class CollegeRepositoryImpl implements CollegeRepository {

	private static final String INSERT_COLLEGE = " INSERT INTO college_tb name VALUES ? ";
	private static final String DELETE_COLLEGE = " DELETE FROM college_tb WHERE id = ? ";
	
	@Override
	public int addCollege(College college) {
		int resultrowCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(INSERT_COLLEGE)){
				pstmt.setString(1, college.getName());
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return resultrowCount;
	}

	@Override
	public int deleteById(int id) {
		int resultrowCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(DELETE_COLLEGE)){
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
		
		
		return resultrowCount;
	}

}
