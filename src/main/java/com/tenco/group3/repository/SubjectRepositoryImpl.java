package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;
import com.tenco.group3.repository.interfaces.SubjectRepository;
import com.tenco.group3.util.DBUtil;

public class SubjectRepositoryImpl implements SubjectRepository {
	private final String SELECT_ALL_SUBJECT = " SELECT sub.*, dept.name AS dept_name, coll.name AS coll_name, prof.name AS professor_name "
			+ " FROM subject_tb AS sub "
			+ " JOIN department_tb AS dept "
			+ " ON sub.dept_id = dept.id "
			+ " JOIN college_tb AS coll "
			+ " ON dept.college_id = coll.id "
			+ " JOIN professor_tb AS prof "
			+ " ON sub.professor_id = prof.id "
			+ " order by id asc  limit ? offset ? ";
	private final String GET_SUBJECT_BY_SEMESTER = "SELECT * FROM subject_tb WHERE  PROFESSOR_ID= ? AND AND sub_year = ? AND  semester = ?";
	private final String SELECT_SUBJECT_BY_ID = "SELECT * FROM subject_tb WHERE id = AND AND sub_year = ? AND  semester = ? AND name = ? ";
	private final String SELECT_ID_BY_LESS_NUM_STUDENT="SELECT id FROM subject_tb WHERE capacity >= num_of_student ";
	private static final String COUNT_ALL_BOARDS = " SELECT count(*) as count FROM subject_tb ";
	private static final String SEARCH_SUBJECT = " select sub.*, dept.name AS dept_name, coll.name AS coll_name, prof.name AS professor_name "
			+ " FROM subject_tb AS sub "
			+ " JOIN department_tb AS dept "
			+ " ON sub.dept_id = dept.id "
			+ " JOIN college_tb AS coll "
			+ " ON dept.college_id = coll.id "
			+ " JOIN professor_tb AS prof "
			+ " ON sub.professor_id = prof.id "
			+ " where sub.sub_year  = ? and sub.semester =  ? and sub.name like ?  and (dept.id = ? OR ? = -1)  "
			+ " ORDER BY id asc "
			+ " limit ? offset ? ";
	private static final String COUNT_SEARCH_SUBJECT = " select COUNT(*) as count "
			+ " FROM subject_tb AS sub "
			+ " JOIN department_tb AS dept "
			+ " ON sub.dept_id = dept.id "
			+ " JOIN college_tb AS coll "
			+ " ON dept.college_id = coll.id "
			+ " JOIN professor_tb AS prof "
			+ " ON sub.professor_id = prof.id "
			+ " where sub.sub_year  = ? and sub.semester =  ? and sub.name like ?  and (dept.id = ? OR ? = -1)  ";
	
	private static final String SELECT_SYLLABUS_BY_ID = " SELECT sy.subject_id, s.name, s.sub_year, s.semester, s.grades, s.type, s.sub_day, s.start_time, s.end_time, s.room_id, c.name college_name, p.name as professor_name, d.name as dept_name, p.tel, p.email, sy.overview, sy.objective, sy.textbook, sy.program FROM subject_tb s JOIN professor_tb p ON s.professor_id = p.id JOIN department_tb d ON p.dept_id = d.id JOIN syllabus_tb sy ON s.id = sy.subject_id JOIN room_tb r ON s.room_id = r.id JOIN college_tb c ON r.college_id = c.id WHERE subject_id = ? ";

	
	@Override
	public List<Subject> getSubjectAll(int limit, int offset) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SUBJECT)) {
			pstmt.setInt(1, limit);
			pstmt.setInt(2, offset);			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
			
				Subject subject = Subject.builder()
						.subYear(rs.getInt("sub_year"))
						.semester(rs.getInt("semester"))
						.collName(rs.getString("coll_name"))
						.deptName(rs.getString("dept_name"))
						.id(rs.getInt("id"))
						.type(rs.getString("type"))
						.name(rs.getString("name"))
						.professorName(rs.getString("professor_name"))
						.grades(rs.getInt("grades"))
						.numOfStudent(rs.getInt("num_of_student"))
						.capacity(rs.getInt("capacity"))
						.build(); 
				subjectList.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	
	
	@Override
	public List<Subject> searchSubject(int subYear,int semester,String name,int deptId,int limit, int offset) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SEARCH_SUBJECT)) {
		
		
			if (name == null) {
				name = "";
			}
			
			
			pstmt.setInt(1, subYear);
			pstmt.setInt(2,semester);
			pstmt.setString(3, "%" + name + "%");
			pstmt.setInt(4,  deptId);
			pstmt.setInt(5, deptId);
			pstmt.setInt(6, limit);
			pstmt.setInt(7, offset);			
			ResultSet rs = pstmt.executeQuery();
			System.out.println("나는 섭");
			while (rs.next()) {
				Subject subject = Subject.builder()
						.subYear(rs.getInt("sub_year"))
						.semester(rs.getInt("semester"))
						.collName(rs.getString("coll_name"))
						.deptName(rs.getString("dept_name"))
						.id(rs.getInt("id"))
						.type(rs.getString("type"))
						.name(rs.getString("name"))
						.professorName(rs.getString("professor_name"))
						.grades(rs.getInt("grades"))
						.numOfStudent(rs.getInt("num_of_student"))
						.capacity(rs.getInt("capacity"))
						.build(); 
				subjectList.add(subject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}
	
	@Override
	public List<Subject> getSubjectByType(String type) {
		
		
		return null;
	}

	@Override
	public List<Subject> getSubjectBySemester(int professorId, int subYear, int semester) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(GET_SUBJECT_BY_SEMESTER)) {
			pstmt.setInt(1, professorId);
			pstmt.setInt(2, subYear);
			pstmt.setInt(3, semester);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(
						Subject.builder()
						.id(rs.getInt("id"))
						.deptId(rs.getInt("deptId"))
						.name(rs.getString("name"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;

	}

	@Override
	public List<Subject> getStudentBySubject(int id, String type) {
		
		
		
		return null;
	}

	@Override
	public List<Subject> getSubjectById(int id, int year, int semester, String name) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_SUBJECT_BY_ID)) {
			pstmt.setInt(1, id);
			pstmt.setInt(2, year);
			pstmt.setInt(3, semester);
			pstmt.setString(4, name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.professorId(rs.getInt("professorId"))
						.roomId(rs.getString("roomId"))
						.deptId(rs.getInt("deptId"))
						.type(rs.getString("type"))
						.subYear(rs.getInt("subYear"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public List<Subject> selectIdByLessNumOfStudent() {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_ID_BY_LESS_NUM_STUDENT)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.professorId(rs.getInt("professorId"))
						.roomId(rs.getString("roomId"))
						.deptId(rs.getInt("deptId"))
						.type(rs.getString("type"))
						.subYear(rs.getInt("subYear"))
						.startTime(rs.getInt("startTime"))
						.endTime(rs.getInt("endTime"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	public int getAllSubjectCount() {
		int subjectCount = 0;
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_ALL_BOARDS)) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				subjectCount = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subjectCount;
	}



	@Override
	public int getSearchSubjectCount(int year, int semester, String name, int deptId) {
		 int count = 0;
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(COUNT_SEARCH_SUBJECT)) {
	        	if (name == null) {
					name = "";
				}
	        	pstmt.setInt(1, year);
	        	pstmt.setInt(2, semester);
	        	pstmt.setString(3, "%" + name + "%");
	        	pstmt.setInt(4, deptId);
	        	pstmt.setInt(5, deptId);
	                try (ResultSet rs = pstmt.executeQuery()) {
	                    if (rs.next()) {
	                        count = rs.getInt("count");
	                    }
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	            return count;
		
	}

	
	
	@Override
    public Syllabus getSyllabusById(int subjectId) {
        Syllabus syllabus = null;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SELECT_SYLLABUS_BY_ID)) {
            pstmt.setInt(1, subjectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                syllabus = Syllabus.builder().subjectId(subjectId).name(rs.getString("name"))
                        .subYear(rs.getInt("sub_year")).semester(rs.getInt("semester")).grades(rs.getInt("grades"))
                        .type(rs.getString("type")).subDay(rs.getString("sub_day")).startTime(rs.getInt("start_time"))
                        .endTime(rs.getInt("end_time")).roomId(rs.getString("room_id"))
                        .collName(rs.getString("college_name")).professorName(rs.getString("professor_name"))
                        .deptName(rs.getString("dept_name")).tel(rs.getString("tel")).email(rs.getString("email"))
                        .overview(rs.getString("overview")).objective(rs.getString("objective"))
                        .textbook(rs.getString("textbook")).program(rs.getString("program")).build();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return syllabus;


    }




	

	

}
