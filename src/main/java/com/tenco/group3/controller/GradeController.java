package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.Grade;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.EvaluationRepositoryImpl;
import com.tenco.group3.repository.GradeRepositoryImpl;
import com.tenco.group3.repository.interfaces.EvaluationRepository;
import com.tenco.group3.repository.interfaces.GradeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/grade/*")
public class GradeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GradeRepository gradeRepository;
	
	@Override
	public void init() throws ServletException {
		gradeRepository = new GradeRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getPathInfo();
		switch (action) {
		case "/thisSemester":
			showThisSemester(request, response, session);
			break;
		case "/semester":
			showSemester(request, response, session);
			break;
		case "/total":
			showTotal(request, response, session);
			break;
		default:
			break;
		}

	}

	/**
	 * 전체 누계 성적 페이지로 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showTotal(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		// TODO - 수강신청에서 학기, 연도 받기
		// 세션에서 학생 ID 받기
		User user = (User) session.getAttribute("principal");
//		gradeRepository.getTotalGrade(student.getId());
		Grade grade = gradeRepository.getTotalGrade(user.getId());
		request.setAttribute("grade", grade);
		request.getRequestDispatcher("/WEB-INF/views/grade/total.jsp").forward(request, response);

	}

	/**
	 * 학기별 성적 조회 페이지 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showSemester(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		// TODO - 수강신청에서 학기, 연도 받기
		User user = (User) session.getAttribute("principal");
		int year = 0;
		int semester = 0;
		String type = "all";
		if (request.getParameter("year") != null && request.getParameter("semester") != null
				&& request.getParameter("type") != null) {
			try {
				year = Integer.parseInt(request.getParameter("year"));
				semester = Integer.parseInt(request.getParameter("semester"));
				type = request.getParameter("type");
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 파라미터 값입니다.");
				return;
			}
		}
		Grade grade = Grade.builder().subYear(year).semester(semester).type(type).build();
		if (type.equals("교양") || type.equals("전공")) {
			// 교양 or 전공 타입 성적 조회
//			List<Grade> gradeList = gradeRepository.getSemesterByType(2023000012, grade);
			List<Grade> gradeList = gradeRepository.getSemesterByType(user.getId(), grade);
			request.setAttribute("gradeList", gradeList);
		} else {
			// 전체 성적 조회
//			List<Grade> gradeList = gradeRepository.getSemester(2023000012, grade);
			List<Grade> gradeList = gradeRepository.getSemester(user.getId(), grade);
			request.setAttribute("gradeList", gradeList);
		}
		request.getRequestDispatcher("/WEB-INF/views/grade/semester.jsp").forward(request, response);
	}

	/**
	 * 이번 학기 성적 조회 페이지로 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showThisSemester(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		// TODO - 수강신청에서 학기, 연도 받기
		User user = (User) session.getAttribute("principal");
		int year = gradeRepository.getCurrentYearBySubject(user.getId());
		int semester = gradeRepository.getCurrentSemesterBySubject(user.getId());
//		List<Grade> gradeList = gradeRepository.getThisSemester(user.getId(), 1, 2023);
//		Grade gradeTotalList = gradeRepository.getThisSemesterGrade(user.getId(), 1, 2023);
		List<Grade> gradeList = gradeRepository.getThisSemester(user.getId(), semester, year);
		Grade gradeTotalList = gradeRepository.getThisSemesterGrade(user.getId(), semester, year);
		request.setAttribute("gradeList", gradeList);
		request.setAttribute("thisGrade", gradeTotalList);
		request.getRequestDispatcher("/WEB-INF/views/grade/thisSemester.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
