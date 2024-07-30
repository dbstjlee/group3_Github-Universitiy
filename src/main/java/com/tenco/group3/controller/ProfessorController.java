package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.StuSubDetail;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.ProfessorRepositoryImpl;
import com.tenco.group3.repository.StuSubDetailRepositoryImpl;
import com.tenco.group3.repository.StuSubRepositoryImpl;
import com.tenco.group3.repository.SubjectRepositoryImpl;
import com.tenco.group3.repository.interfaces.ProfessorRepository;
import com.tenco.group3.repository.interfaces.StuSubDetailRepository;
import com.tenco.group3.repository.interfaces.StuSubRepository;
import com.tenco.group3.repository.interfaces.SubjectRepository;
import com.tenco.group3.util.AlertUtil;

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
	private StuSubDetailRepository subDetailRepository;
	private StuSubRepository stuSubRepository;

	@Override
	public void init() throws ServletException {
		professorRepository = new ProfessorRepositoryImpl();
		subjectRepository = new SubjectRepositoryImpl();
		subDetailRepository = new StuSubDetailRepositoryImpl();
		stuSubRepository = new StuSubRepositoryImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);

		switch (action) {
		case "/subject":
			subjectById(request, response);
			break;
		case "/veiwSyllabusUpdate":
			veiwSyllabusUpdate(request, response);
			break;
		case "/mySubject":
			viewMyAllSubject(request, response, session);
			break;
		case "/subject/student":
			showUpdateStudentDetail(request, response);
			break;
		default:

			break;
		}

	}

	private void showUpdateStudentDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StuSubDetail student = subDetailRepository.getDetailById(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/views/professor/updateStudentDetail.jsp").forward(request, response);
	}

	private void subjectById(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
		int subjectId = Integer.parseInt(request.getParameter("id"));
		String subjectName = request.getParameter("name");
		List<StuSubDetail> studentList = subDetailRepository.getAllDetailBySubjectId(subjectId);
		request.setAttribute("studentList", studentList);
		request.setAttribute("subjectName", subjectName);
		request.getRequestDispatcher("/WEB-INF/views/professor/subjectStudentList.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		switch (action) {
		case "/update":
			handleSyllabusUpdate(request, response);
			break;
		case "/mySubjectBySemester":
			searchPorfessorSubjectBySeemester(request, response, session);
			break;
		case "/student":
			handleStudentGrade(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

	}

	private void handleStudentGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int absent = Integer.parseInt(request.getParameter("absent"));
			int lateness = Integer.parseInt(request.getParameter("lateness"));
			int homework = Integer.parseInt(request.getParameter("homework"));
			int midExam = Integer.parseInt(request.getParameter("midExam"));
			int finalExam = Integer.parseInt(request.getParameter("finalExam"));
			int convertedMark = Integer.parseInt(request.getParameter("convertedMark"));
			int id = Integer.parseInt(request.getParameter("id"));
			String grade = request.getParameter("grade");
			StuSubDetail stuSubDetail = StuSubDetail.builder()
					.id(id)
					.absent(absent)
					.lateness(lateness)
					.homework(homework)
					.midExam(midExam)
					.finalExam(finalExam)
					.convertedMark(convertedMark)
					.build();
			subDetailRepository.updateDetailById(stuSubDetail);
			stuSubRepository.updateGrade(grade, id);
			Subject subject = subDetailRepository.getSubjectByDetailId(id);
			int subjectId = subject.getId();
			String subjectName = URLEncoder.encode(subject.getName(), StandardCharsets.UTF_8.toString());
			response.setCharacterEncoding("UTF-8");
			response.sendRedirect("/professor/subject?id=" + subjectId + "&name=" + subjectName);
		} catch (Exception e) {
			AlertUtil.backAlert(response, "잘못기입하셨습니다.");
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

		List<Subject> subjectList = professorRepository.veiwProfessorsubjectByProfessorId(user.getId());
		List<Integer> yearList = new ArrayList<>();
		for (Subject subject : subjectList) {
			int subYear = subject.getSubYear();
			if (!yearList.contains(subYear)) {
				yearList.add(subYear);
			}
		}
		try {
			request.setAttribute("yearList", yearList);
			request.setAttribute("subjectList", subjectList);
			request.getRequestDispatcher("/WEB-INF/views/professor/professorSubjectList.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "잘못된 접근");
		}
	}

	/**
	 * @학기별조회
	 */
	private void searchPorfessorSubjectBySeemester(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws SecurityException, IOException {
		User user = (User) session.getAttribute("principal");

		int year = Integer.parseInt(request.getParameter("subYear"));
		int semester = Integer.parseInt(request.getParameter("subSemester"));
		List<Subject> subjectlist = professorRepository.veiwProfessorsubjectBySemesterAndYear(user.getId(), year, semester);
		// 학년도 를 띄우려고 받아옴
		List<Subject> subjectList = professorRepository.veiwProfessorsubjectByProfessorId(user.getId());
		List<Integer> yearList = new ArrayList<>();
		for (Subject subject : subjectList) {
			int subYear = subject.getSubYear();
			if (!yearList.contains(subYear)) {
				yearList.add(subYear);
			}
		}
		request.setAttribute("subjectList", subjectlist);
		request.setAttribute("yearList", yearList);

		try {
			request.getRequestDispatcher("/WEB-INF/views/professor/professorSubjectList.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	private void handleSyllabusUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		User principal = (User) request.getSession().getAttribute("principal");
		String overview = request.getParameter("overview");
		String objective = request.getParameter("objective");
		String textbook = request.getParameter("textbook");
		String program = request.getParameter("program");
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		Syllabus syllabus = Syllabus.builder()
			.overview(overview)
			.objective(objective)
			.textbook(textbook)
			.program(program)
			.subjectId(subjectId)
			.build();
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
