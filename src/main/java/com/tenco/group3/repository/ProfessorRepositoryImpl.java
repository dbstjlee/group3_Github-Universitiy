package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;
import com.tenco.group3.repository.interfaces.ProfessorRepository;
import com.tenco.group3.util.DBUtil;

public class ProfessorRepositoryImpl implements ProfessorRepository {
	private final String ADD_PROFESSOR = "INSERT INTO professor_tb (name, birth_date, gender, address, tel, dept_id, email) VALUES (?,?,?,?,?,?,?)";
	private final String UPDATE_SYLLABUS = "  update syllabus_tb set  overview = ? , objective= ? , textbook=  ? ,program= ?  where subject_id = ?  ";
	private final String VEIW_SYLLABUS = " SELECT * FROM syllabus_tb WHERE  subject_id = ? ";
	private final String VEIW_PROFESSOR_SUBJECT = " SELECT * FROM subject_tb WHERE professor_id = ? AND sub_year = ? AND semester = ? ";
	private final String VEIW_PROFESSOR_ALL_SUBJECT = " SELECT * FROM subject_tb WHERE professor_id = ? ";
	
	
	
	@Override
	public int addProfessor(Professor professor) {
		// 교수 추가 하기
		int resultCount = 0;
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(ADD_PROFESSOR)) {
				pstmt.setInt(1, professor.getId());
				pstmt.setString(2, professor.getName());
				pstmt.setDate(3, professor.getBirthDate());
				pstmt.setString(4, professor.getGender());
				pstmt.setString(5, professor.getAddress());
				pstmt.setString(6, professor.getTel());
				pstmt.setString(7, professor.getEmail());
				pstmt.setInt(8, professor.getDeptId());
				pstmt.setDate(9, professor.getHireDate());
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

	
	
	

	@Override
	public void updateSyllabus(Syllabus syllabus) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_SYLLABUS)) {
				pstmt.setString(1, syllabus.getOverview());
				pstmt.setString(2, syllabus.getObjective());
				pstmt.setString(3, syllabus.getTextbook());
				pstmt.setString(4, syllabus.getProgram());
				pstmt.setInt(5, syllabus.getSubjectId());
				pstmt.executeUpdate();
				System.out.println("여기까진 들간다:" + pstmt);
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Syllabus veiwSyllabus(int subjectId) {
		Syllabus syllabus = null;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(VEIW_SYLLABUS)) {
            pstmt.setInt(1, subjectId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                syllabus  = Syllabus.builder()
                		.subjectId(subjectId)
        				.overview(rs.getString("overview"))
        				.objective(rs.getString("objective"))
        				.textbook(rs.getString("textbook"))
        				.build();
                       
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return syllabus;
	}

	
	/**
	 * @교수 /내 강의 조회
	 * 
	 * */
	@Override
	public List<Subject> veiwProfessorsubjectBySemesterAndYear(int professorId, int subYear, int semester) {
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(VEIW_PROFESSOR_SUBJECT)) {
			pstmt.setInt(1, professorId);
			pstmt.setInt(2, subYear);
			pstmt.setInt(3, semester);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.professorId(rs.getInt("professor_id"))
						.roomId(rs.getString("room_id"))
						.deptId(rs.getInt("dept_id"))
						.type(rs.getString("type"))
						.subYear(rs.getInt("sub_year"))
						.semester(rs.getInt("semester"))
						.startTime(rs.getInt("start_time"))
						.endTime(rs.getInt("end_time"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	
	
	
	}

	@Override
	public List<Subject> veiwProfessorsubjectByProfessorId(int professorId) {
		
		List<Subject> subjectList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(VEIW_PROFESSOR_ALL_SUBJECT)) {
			pstmt.setInt(1, professorId);
			ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
				subjectList.add(Subject.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.professorId(rs.getInt("professor_id"))
						.roomId(rs.getString("room_id"))
						.deptId(rs.getInt("dept_id"))
						.type(rs.getString("type"))
						.subYear(rs.getInt("sub_year"))
						.startTime(rs.getInt("start_time"))
						.semester(rs.getInt("semester"))
						.endTime(rs.getInt("end_time"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectList;
	}

	
}
