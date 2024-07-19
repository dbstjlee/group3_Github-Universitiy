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

	@Override
	public List<Subject> getSubjectAll() {
		List<Subject> subjectList = new ArrayList<Subject>();
		String query = " SELECT su.*, pr.name, de.name " + " FROM subject_tb AS su "
				+ " INNER JOIN department_tb AS de ON su.dept_id = de.id "
				+ " INNER JOIN professor_tb AS pr ON su.professor_id = pr.id ";
		try (Connection conn = DBUtil.getConnetion();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()){
				while (rs.next()) {
					Subject subject = Subject.builder()
									.id(rs.getInt("id"))
									.name(rs.getString("name"))
									.roomId(rs.getString("room_id"))
									.type(rs.getString("type"))
									.semester(rs.getInt("semester"))
									.subDay(rs.getString("sub_day"))
									.startTime(rs.getInt("start_time"))
									.endTime(rs.getInt("end_time"))
									.grades(rs.getInt("grades"))
									.capacity(rs.getInt("capacity"))
									.numOfStudent(rs.getInt("numOfStudent"))
									.professorName(rs.getString("professorName"))
									.deptName("deptName")
									.build();
					subjectList.add(subject);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public List<Subject> getSubjectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByDeptId(int deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectByDeptIDAndType(int deptId, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject subjectEnrolment(int studentId, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Subject> getSubjectEnrolmented(int studentId, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

}
