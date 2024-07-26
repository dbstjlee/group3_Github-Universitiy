package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.CollegeTuition;
import com.tenco.group3.repository.interfaces.CollTuitRepository;
import com.tenco.group3.repository.interfaces.CollegeRepository;
import com.tenco.group3.util.DBUtil;

public class CollTuitRepositoryImpl implements CollTuitRepository{

	private static final String GET_ALL_COLLTUIT = " SELECT c.id AS college_id, c.name AS college_name, t.amount AS tuition_amount "
			+ " FROM  college_tb c JOIN coll_tuit_tb t ON c.id = t.college_id ORDER BY c.id ASC ";
	private static final String ADD_COLLEGE_TUITION = " INSERT INTO coll_tuit_tb (college_id, amount) VALUES (?, ?) ";
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CollegeTuition getCollegeTutionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


}
