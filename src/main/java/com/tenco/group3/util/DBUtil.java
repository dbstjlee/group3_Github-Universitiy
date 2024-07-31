package com.tenco.group3.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {

	private static DataSource datesource;
	
	static {
		try {
			InitialContext ctx = new InitialContext();
			datesource = (DataSource) ctx.lookup("java:comp/env/jdbc/githubUniversity");
		} catch (NamingException e) {
			System.out.println("DBUtil 초기화 실패");
			e.printStackTrace();	
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return datesource.getConnection();
	}
	
}
