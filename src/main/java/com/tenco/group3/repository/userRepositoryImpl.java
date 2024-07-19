package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.User;
import com.tenco.group3.repository.interfaces.UserRepository;
import com.tenco.group3.util.DBUtil;

public class userRepositoryImpl implements UserRepository{

	private static final String SELECT_USER_BY_ID = " SELECT u.*, "
			+ "        CASE WHEN sd.name IS NOT NULL THEN sd.name "
			+ "        WHEN sf.name IS NOT NULL THEN sf.name "
			+ "        WHEN p.name IS NOT NULL THEN p.name "
			+ "        END AS name "
			+ "        FROM user_tb AS u "
			+ "        LEFT JOIN "
			+ "        student_tb AS sd "
			+ "        ON u.id = sd.id "
			+ "        LEFT JOIN staff_tb AS sf "
			+ "        ON u.id = sf.id "
			+ "        LEFT JOIN professor_tb AS p "
			+ "        ON u.id = p.id "
			+ "        where u.id = ? ; ";
	
	@Override
	public User getUserById(int id) {
		User user = null;
		try (Connection conn = DBUtil.getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_ID)){
				pstmt.setInt(1, id);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					user = User.builder()
							.id(rs.getInt("id"))
							.password(rs.getString("password"))
							.userRole(rs.getString("user_role"))
							.username(rs.getString("name"))
							.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
		return user;
	}

}
