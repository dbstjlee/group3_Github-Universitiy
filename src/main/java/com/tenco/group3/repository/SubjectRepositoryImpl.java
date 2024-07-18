package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.interfaces.SubjectRepository;
import com.tenco.group3.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository {

	private final String GET_SUBJECT_BY_SEMESTER = "SELECT * FROM subject WHERE and PROFESSOR_ID= ?  semester = ?";

	@Override
	public List<Subject> getSubjectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<Subject> getSubjectBySemester(int professorId, int semester) {
		List<Subject> subjectList = new ArrayList();
		try (Connection conn = DBUtil.getConnetion();
				PreparedStatement pstmt = conn.prepareStatement(GET_SUBJECT_BY_SEMESTER)){
			pstmt.setInt(1, professorId);
			pstmt.setInt(2, semester);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.deptId(rs.getInt("deptId"))
						.name(rs.getString("name"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build()
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
		
	} 
	


	@Override
	public List<Subject> getStudentBySubject(int id, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
