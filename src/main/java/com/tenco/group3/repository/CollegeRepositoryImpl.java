package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.College;
import com.tenco.group3.repository.interfaces.CollegeRepository;
import com.tenco.group3.util.DBUtil;

public class CollegeRepositoryImpl implements CollegeRepository {

	private static final String SELECT_ALL_COLLEGE = " select * from college_tb order by id ASC ";
	private static final String ADD_COLLEGE = " INSERT INTO college_tb (name) VALUES (?) ";
	private static final String DELETE_COLLEGE = " DELETE FROM college_tb WHERE id = ? ";
	private static final String SELECT_COLLEGE_BY_ID = " SELECT * FROM college_tb WHERE id = ? ";
	
	// 단과대학 전체 조회
	@Override
	public List<College> getAllCollege() {
		List<College> colleges = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_COLLEGE)) {
			ResultSet rs = pstmt.executeQuery(); 
			while (rs.next()) {
				colleges.add(College.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colleges;
	}

	// 단과대학 추가(등록)
	@Override
	public int addCollege(College college) {
		int resultCount = 0;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(ADD_COLLEGE)) {
				pstmt.setString(1, college.getName());
				resultCount = pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultCount;
	}

	// 단과대학 삭제
	@Override
	public int deleteCollege(int id) {
		int resultCount = 0;
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(DELETE_COLLEGE)) {
		        conn.setAutoCommit(false); 
		        pstmt.setInt(1, id); 
		        resultCount = pstmt.executeUpdate(); 
		        
		        if (resultCount > 0) {
		            conn.commit(); 
		        } else {
		            conn.rollback(); 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); 
		        try (Connection conn = DBUtil.getConnection()) {
		            conn.rollback();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		return resultCount;
	}

	@Override
	public College getCollegeById(int id) {
		College college = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_COLLEGE_BY_ID)){
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					college = College.builder()
							.id(rs.getInt("id"))
							.name(rs.getString("name"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return college;
	}

}
