package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Syllabus;
import com.tenco.group3.repository.interfaces.ProfessorRepository;
import com.tenco.group3.util.DBUtil;

public class ProfessorRepositoryImpl implements ProfessorRepository {
	private final String ADD_PROFESSOR = "INSERT INTO professor_tb (name, birth_date, gender, address, tel, dept_id, email) VALUES (?,?,?,?,?,?,?)";
	private final String UPDATE_SYLLABUS = " UPDATE syllabus_tb SET  overview = ? ,objective = ?,textbook = ? WHERE subject_id = ? ";
	private final String VEIW_SYLLABUS = " SELECT * FROM syllabus_tb WHERE  subject_id = ? ";
	
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
	public int updateProfessorPassword(Professor professor, String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProfessor(Professor professor, String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Professor> getProfessorList() {
		return null;
	}

	@Override
	public Professor getProfessorById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Professor getProfessorByDepartmentId(int deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Professor getProfessorAmount() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	@Override
	public void updateSyllabus(Syllabus syllabus) {
		try (Connection conn = DBUtil.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_SYLLABUS)) {
				pstmt.setString(1, syllabus.getOverview());
				pstmt.setString(2, syllabus.getObjective());
				pstmt.setString(3, syllabus.getTextbook());
				pstmt.setInt(4, syllabus.getSubjectId());
				pstmt.executeUpdate();
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

	
}
