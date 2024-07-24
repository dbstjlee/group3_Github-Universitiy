package com.tenco.group3.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.BreakAppRepositoryImpl;
import com.tenco.group3.repository.interfaces.BreakAppRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/break/*")
public class BreakController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BreakAppRepository breakAppRepository;
	private LocalDate currentDate = LocalDate.now();

	@Override
	public void init() throws ServletException {
		breakAppRepository = new BreakAppRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		switch (action) {
		case "/application":
			showApplication(request, response, session);
			break;
		case "/list":
			showBreakList(request, response, session);
			break;
		case "/detail":
			showBreakListDetail(request, response, session);
		default:
			break;
		}
	}

	/**
	 * 휴학 신청 화면 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showApplication(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/break/application.jsp").forward(request, response);
	}

	/**
	 * 휴학 신청 내역 상세 화면 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showBreakListDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		int breakId = Integer.parseInt(request.getParameter("breakId"));
//		System.out.println("breakId : " + breakId);
		BreakApp breakApp = breakAppRepository.getBreakAppDetail(breakId);
		if (breakApp == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
//		System.out.println(breakApp.toString());
		request.setAttribute("breakApp", breakApp);
		request.getRequestDispatcher("/WEB-INF/views/break/detail.jsp").forward(request, response);
	}

	/**
	 * 휴학 신청 내역 목록 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showBreakList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException {
		// TODO - 학생 ID값 추가(세션에서 받음)
		User user = (User) session.getAttribute("principal");
		List<BreakApp> breakList = breakAppRepository.getBreakAppList(user.getId());
//		System.out.println(breakList.toString());
		request.setAttribute("breakList", breakList);
		request.getRequestDispatcher("/WEB-INF/views/break/list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession();
		if (session == null) {
			// TODO - 로그인 화면으로 이동
			return;
		}
		switch (action) {
		case "/application":
			handlerApplication(request, response, session);
			break;
		default:
			break;
		}
	}

	/**
	 * 휴학 신청
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	private void handlerApplication(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		User user = (User) session.getAttribute("principal");
		Student student = breakAppRepository.getStudentInfo(user.getId());
		String breakType = request.getParameter("breakType");
		int breakYear = Integer.parseInt(request.getParameter("breakYear"));
		int semester = Integer.parseInt(request.getParameter("semester"));
		int currentYear = currentDate.getYear();
		// TODO - 샘플데이터 값 학생정보에서 받아오기.
		System.out.println("student : " + student);
		BreakApp breakApp = BreakApp.builder().studentId(user.getId()).studentGrade(student.getGrade()).fromYear(currentYear).fromSemester(student.getSemester())
				.toYear(breakYear).toSemester(semester).type(breakType).build();
		System.out.println("brakApp : " + breakApp);
		breakAppRepository.addBreakApp(breakApp);
		response.sendRedirect(request.getContextPath() + "/break/list");
	}

}