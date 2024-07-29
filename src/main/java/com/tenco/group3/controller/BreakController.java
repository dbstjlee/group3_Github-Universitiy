package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
		int isBreakDay = (int) getServletContext().getAttribute("breakApp");
		int thisYear = (int) getServletContext().getAttribute("year");
		int thisSemester = (int) getServletContext().getAttribute("semester");
		User user = (User) session.getAttribute("principal");
		if(isBreakDay == ScheduleState.TRUE) {
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
		case "/cancle":
			handlerCancleBreak(request, response, session);
		default:
			break;
		}
	}
	
	/**
	 * 휴학 신청 취소 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException 
	 */
	private void handlerCancleBreak(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("principal");
		breakAppRepository.cancleBreakApp(user.getId());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script> alert('취소되었습니다.'); window.close(); </script>");
		return;
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
		// TODO - 샘플데이터 값 학생정보에서 받아오기.
		BreakApp breakApp = BreakApp.builder().studentId(user.getId()).studentGrade(student.getGrade()).fromYear(currentYear).fromSemester(semester)
				.toYear(breakYear).toSemester(semester).type(breakType).build();
		System.out.println("brakApp : " + breakApp);
		breakAppRepository.addBreakApp(breakApp);
		response.sendRedirect(request.getContextPath() + "/break/list");
	}

}