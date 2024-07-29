package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.ProfessorRepositoryImpl;
import com.tenco.group3.repository.SubjectRepositoryImpl;
import com.tenco.group3.repository.interfaces.ProfessorRepository;
import com.tenco.group3.repository.interfaces.SubjectRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/professor/*")
public class ProfessorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorRepository professorRepository;
	private SubjectRepository subjectRepository;

	@Override
	public void init() throws ServletException {
		professorRepository = new ProfessorRepositoryImpl();
		subjectRepository = new SubjectRepositoryImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "");
			return;
		}

		switch (action) {
		case "/subject":
			subjectById(request, response);

			break;
		case "/syllabusUpdate":

			break;
		case "/veiwSyllabusUpdate":
			veiwSyllabusUpdate(request, response);
			break;
		case "/mySubject":
			viewMyAllSubject(request, response, session);
			break;
		default:

			break;
		}

	}

	private void subjectById(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IOException, ServletException {
		// TODO 유효성 검사
		int semester = Integer.parseInt(request.getParameter("semester"));
		int year = Integer.parseInt(request.getParameter("year"));
		int professorId = Integer.parseInt(request.getParameter("professorId"));

		List<Subject> subjectlist = subjectRepository.getSubjectBySemester(professorId, year, semester);
		request.setAttribute("subjectlist", subjectlist);
		request.getRequestDispatcher("/WEB-INF/views/professor/professorSubjectList.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();
		switch (action) {
		case "/update":
			HandleSyllabusUpdate(request, response);
			break;
		case "/mySubjectBySemester":

			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

	}

	private void veiwSyllabusUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
			Syllabus syllabus = subjectRepository.getSyllabusById(subjectId);

			request.setAttribute("syllabus", syllabus);
			request.getRequestDispatcher("/WEB-INF/views/professor/updateSyllabus.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근");
			return;
		}
	}

	private void syllabusUpdated(HttpServletRequest request, HttpServletResponse response) {
		String overview = request.getParameter("overview");
		String objective = request.getParameter("objective");
		String textbook = request.getParameter("textbook");
		String subjectId = request.getParameter("subjectId");

		Syllabus syllabus = Syllabus.builder().overview(overview).objective(objective).textbook(textbook).build();
		professorRepository.updateSyllabus(syllabus);
		try {
			request.getRequestDispatcher("/WEB-INF/views/syllabus/updateSyllabus.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @교수과목전체조회
	 * 
	 */

	private void viewMyAllSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("principal");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login"); // 사용자 정보가 없으면 로그인 페이지로 리다이렉트
			return;
		}

		List<Subject> subjectList = professorRepository.veiwProfessorsubjectByProfessorId(user.getId());

		try {
			request.setAttribute("subjectList", subjectList);
			request.getRequestDispatcher("/WEB-INF/views/professor/professorSubjectList.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근");
		}
	}

	private void HandleSyllabusUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		User principal = (User) request.getSession().getAttribute("principal");

		String overview = request.getParameter("overview");
		String objective = request.getParameter("objective");
		String textbook = request.getParameter("textbook");
		String program = request.getParameter("program");
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		Syllabus syllabus = Syllabus.builder().overview(overview).objective(objective).textbook(textbook)
				.program(program).subjectId(subjectId).build();
		professorRepository.updateSyllabus(syllabus);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('수정완료');");
		out.println("window.close();");
		out.println("</script>");
		out.close();

	}

}
