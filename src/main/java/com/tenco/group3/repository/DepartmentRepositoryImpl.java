package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tenco.group3.model.Department;
import com.tenco.group3.repository.interfaces.DepartmentRepository;
import com.tenco.group3.util.DBUtil;

public class DepartmentRepositoryImpl implements DepartmentRepository{

	
	@Override
	public int addDeaprtment(Department department) {
		
		return 0;
	}

	@Override
	public Department selectedById(int id) {
		Department department = null;
		String query = " SELECT FROM * department_tb WHERE id = ? ";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)){
				pstmt.setInt(1, id);
				try (ResultSet rs = pstmt.executeQuery()){
					if (rs.next()) {
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
	public int updateDepartment(Department department) {
		String query = " UPDATE departments SET name = ? WHERE id = ? ";
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setString(1, department.getName());
				pstmt.setInt(2, department.getId());
				pstmt.executeQuery();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
