package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.CollegeTuition;
import com.tenco.group3.repository.interfaces.CollTuitRepository;
import com.tenco.group3.util.DBUtil;

public class CollTuitRepositoryImpl implements CollTuitRepository{

	private static final String GET_ALL_COLLTUIT = " SELECT c.id AS college_id, c.name AS college_name, t.amount AS tuition_amount "
			+ " FROM  college_tb c JOIN coll_tuit_tb t ON c.id = t.college_id ORDER BY c.id ASC ";
	private static final String ADD_COLLEGE_TUITION = " INSERT INTO coll_tuit_tb (college_id, amount) VALUES (?, ?) ";
	private static final String UPDATE_COLLEGE_TUITION = " update coll_tuit_tb set amount = ? where college_id = ? ";
	private static final String DELETE_COLLEGE_TUITION = " delete from coll_tuit_tb where college_id = ? ";
	private static final String SELECT_COLLTUIT_BY_ID = " select * from coll_tuit_tb where college_id ";
	
	@Override
	public List<CollegeTuition> getAllColTuit() {
	      List<CollegeTuition> collegeTuitions = new ArrayList<>();

	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(GET_ALL_COLLTUIT);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                CollegeTuition collegeTuition = CollegeTuition.builder()
	                        .college_id(rs.getInt("college_id"))
	                        .college_name(rs.getString("college_name"))
	                        .amount(rs.getInt("tuition_amount"))
	                        .build();
	                collegeTuitions.add(collegeTuition);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return collegeTuitions;
	    }

	@Override
	public int addCollegeTuition(CollegeTuition collegeTuition) {
		int result = 0;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(ADD_COLLEGE_TUITION)) {

            pstmt.setInt(1, collegeTuition.getCollege_id());
            pstmt.setInt(2, collegeTuition.getAmount());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
	
	@Override
	public int deleteCollegeTuition(int id) {
		int resultCount = 0;
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(DELETE_COLLEGE_TUITION)) {
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
	public CollegeTuition getCollegeTutionById(int id) {
		CollegeTuition collegeTuition = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_COLLTUIT_BY_ID)){
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					collegeTuition = CollegeTuition.builder()
										.college_id(rs.getInt("college_id"))
										.college_name(rs.getString("college_name"))
										.amount(rs.getInt("amount"))
										.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return collegeTuition;
	}


	/**
	 * 단과대학별 등록금 수정
	 */
	@Override
	public int updateCollegeTuition(CollegeTuition collegeTuition) {
	    int resultCount = 0;
	    try (Connection conn = DBUtil.getConnection()) {
	        conn.setAutoCommit(false);
	        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_COLLEGE_TUITION)) {
	            pstmt.setInt(1, collegeTuition.getAmount());
	            pstmt.setInt(2, collegeTuition.getCollege_id());
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


}
