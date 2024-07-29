package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;
import com.tenco.group3.model.College;
import com.tenco.group3.model.Department;
import com.tenco.group3.repository.interfaces.DepartmentRepository;
import com.tenco.group3.util.DBUtil;

public class DepartmentRepositoryImpl implements DepartmentRepository{

	private static final String SELECT_ALL_DEPARTMENT = " select * from department_tb ";
	private static final String ADD_DEPARTMENT = " insert into department_tb (name, college_id) values (?, ?) ";
	private static final String UPDATE_DEPARTMENT = " update department_tb set name = ? where id = ? ";
	private static final String DELETE_DEPARTMENT = " delete from department_tb where id = ? ";
	private static final String SELECT_DEPARTMENT_BY_ID = " SELECT * FROM department_tb WHERE id = ? ";
	private static final String SELECT_ALL_COLLEGES = " select * from college_tb order by id ASC ";
	// 학과 전체 조회 
	@Override
	public List<Department> getAllDepartment() {
		List<Department> departments = new ArrayList<>();
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_DEPARTMENT)){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				departments.add(Department.builder()
										.id(rs.getInt("id"))
										.name(rs.getString("name"))
										.collegeId(rs.getInt("college_id"))
										.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departments;
	}
	
	// 학과 등록
	@Override
	public int addDepartment(Department department) {
		int resultCount = 0;
		try (Connection conn = DBUtil.getConnection()){
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(ADD_DEPARTMENT)){
				pstmt.setString(1, department.getName());
				pstmt.setInt(2, department.getCollegeId());
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
	
	// 학과 수정
	@Override
	public int updateDepartment(Department department) {
		int resultCount = 0;
	    try (Connection conn = DBUtil.getConnection()) {
	        conn.setAutoCommit(false);
	        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_DEPARTMENT)) {
	            pstmt.setString(1, department.getName());
	            pstmt.setInt(2, department.getId());
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
	
	// 학과 삭제
	@Override
	public int deleteDepartment(int id) {
		int resultCount = 0;
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(DELETE_DEPARTMENT)) {
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
	public Department getDepartmentById(int id) {
		Department department = null;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_DEPARTMENT_BY_ID)){
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					department = Department.builder()
									.id(rs.getInt("id"))
									.name(rs.getString("name"))
									.collegeId(rs.getInt("college_id"))
									.build();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public List<College> getAllColleges() {
		List<College> colleges = new ArrayList<>();

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_COLLEGES)) {
			ResultSet rs = pstmt.executeQuery(); 
			while (rs.next()) {
				colleges.add(College.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colleges;
	}



	


	
}
 
	
	

