package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.BreakAppRepositoryImpl;
import com.tenco.group3.repository.interfaces.BreakAppRepository;
import com.tenco.group3.util.AlertUtil;
import com.tenco.group3.util.SemesterUtil;

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
		int isBreakDay = (int) getServletContext().getAttribute("breakApp");
		int thisYear = (int) getServletContext().getAttribute("year");
		int thisSemester = (int) getServletContext().getAttribute("semester");
		User user = (User) session.getAttribute("principal");
		if (isBreakDay == ScheduleState.TRUE) {
			if (breakAppRepository.isSubmitBreakApp(user.getId())) {
				AlertUtil.backAlert(response, "이미 휴학 신청을 하였습니다.");
				return;
			}
			Student student = breakAppRepository.getStudentInfo(user.getId());
			request.setAttribute("student", student);
			request.setAttribute("thisYear", thisYear);
			request.setAttribute("thisSemester", thisSemester);
			request.getRequestDispatcher("/WEB-INF/views/break/application.jsp").forward(request, response);
		} else {
			AlertUtil.backAlert(response, "휴학 신청 기간이 아닙니다.");
		}
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
		BreakApp breakApp = breakAppRepository.getBreakAppDetail(breakId);
		if (breakApp == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
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
		User user = (User) session.getAttribute("principal");
		List<BreakApp> breakList = breakAppRepository.getBreakAppList(user.getId());
		request.setAttribute("breakList", breakList);
		request.getRequestDispatcher("/WEB-INF/views/break/list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession();
		switch (action) {
		case "/application":
			handlerApplication(request, response, session);
			break;
		case "/cancle":
			handlerCancleBreak(request, response, session);
		default:
			break;
		}
	}

	/**
	 * 휴학 신청 취소
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	private void handlerCancleBreak(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		User user = (User) session.getAttribute("principal");
		breakAppRepository.cancleBreakApp(user.getId());
		AlertUtil.openerHrefAlertClosing(response, "취소되었습니다.", "/break/list");
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
		int semester = SemesterUtil.getCurrentSemester();
		int currentYear = SemesterUtil.getCurrentYear();
		BreakApp breakApp = BreakApp.builder().studentId(user.getId()).studentGrade(student.getGrade())
				.fromYear(currentYear).fromSemester(semester).toYear(breakYear).toSemester(semester).type(breakType)
				.build();
		breakAppRepository.addBreakApp(breakApp);
		response.sendRedirect(request.getContextPath() + "/break/list");
	}

}