package com.tenco.group3.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.repository.interfaces.StuSubRepository;
import com.tenco.group3.util.DBUtil;
import com.tenco.group3.util.SemesterUtil;

public class StuSubRepositoryImpl implements StuSubRepository {

	private static final String SELECT_RANKED_STUDENT = 
			 " WITH avg_grades AS ( "
			+ "    SELECT ss.student_id, AVG(g.grade_value * ss.complete_grade) AS avg_grade "
			+ "    FROM stu_sub_tb ss "
			+ "    JOIN grade_tb g ON ss.grade = g.grade "
			+ "    JOIN subject_tb s ON ss.subject_id = s.id "
			+ "    WHERE s.sub_year = ? AND s.semester ? "
			+ "    GROUP BY ss.student_id "
			+ " ), "
			+ " ranked_grades AS ( "
			+ "    SELECT tg.student_id, s.dept_id, s.grade"
			+ " , RANK() OVER (PARTITION BY s.dept_id, s.grade ORDER BY avg_grade DESC) AS ranking "
			+ "    FROM avg_grades tg "
			+ "    JOIN student_tb s ON tg.student_id = s.id "
			+ " ) "
			+ " SELECT student_id, dept_id, grade, ranking "
			+ " FROM ranked_grades "
			+ " WHERE ranking <= 5 ";

	@Override
	public List<RankedStudent> selectRankedStudent() {
		List<RankedStudent> studentList = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); //
				PreparedStatement pstmt = conn.prepareStatement(SELECT_RANKED_STUDENT)) {
			pstmt.setInt(1, SemesterUtil.getCurrentYear());
			pstmt.setInt(2, SemesterUtil.getCurrentSemester());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				studentList.add(RankedStudent.builder()
						.studentId(rs.getInt("student_id")).deptId(rs.getInt("dept_id"))
						.grade(rs.getInt("grade")).ranking(rs.getInt("ranking"))
						.build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

}
