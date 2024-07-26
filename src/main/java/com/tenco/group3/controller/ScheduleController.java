package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.tenco.group3.model.Schedule;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.ScheduleRepositoryImpl;
import com.tenco.group3.repository.interfaces.ScheduleRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/schedule/*")
public class ScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ScheduleRepository scheduleRepository;

	@Override
	public void init() throws ServletException {
		scheduleRepository = new ScheduleRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action:" + action);
		switch (action) {
		case "/list":
			showScheduleList(request, response);
			break;
		case "/create":
			handleScheduleList(request, response);
			break;
		case "/delete":
			handleScheduleDelete(request, response);
			break;
		case "/add":
			request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleAdd.jsp").forward(request, response);
			break;
		case "/update":
			showScheduleUpdate(request, response);
			break;
		case "/detail":
			showScheduleDetail(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 학사 일정 삭제 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleScheduleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

		int scheduleId = Integer.parseInt(request.getParameter("id"));
		scheduleRepository.deleteSchedule(scheduleId);
		response.sendRedirect(request.getContextPath() + "/schedule/create");
	}

	/**
	 * 학사 일정 수정 폼
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showScheduleUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int ScheduleId = Integer.parseInt(request.getParameter("id"));
		System.out.println("ScheduleId:" + ScheduleId); // TODO - 삭제 예정
		Schedule schedule = scheduleRepository.getScheduleById(ScheduleId); // TODO - 다시
		request.setAttribute("schedule", schedule);
		request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleUpdate.jsp").forward(request, response);
	}

	/**
	 * 학사 일정 상세 보기
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showScheduleDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User principal = (User) request.getSession().getAttribute("principal");
		int ScheduleId = Integer.parseInt(request.getParameter("id"));

		if (principal.getUserRole().equals("staff")) {
			Schedule schedule = scheduleRepository.getScheduleById(ScheduleId);
			request.setAttribute("schedule", schedule);
			request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleDetail.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
		}
	}

	/**
	 * id 별로 학사 일정 전체 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleScheduleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Schedule> scheduleListById = scheduleRepository.getAllScheduleListById();
		request.setAttribute("scheduleListById", scheduleListById);
		request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleList.jsp").forward(request, response);

	}

	/**
	 * 월별 학사 일정 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showScheduleList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Schedule> scheduleList = scheduleRepository.getAllScheduleList();
		request.setAttribute("scheduleList", scheduleList);
		request.getRequestDispatcher("/WEB-INF/views/schedule/schedule.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action:" + action);
		switch (action) {
		case "/add":
			handleScheduleAdd(request, response);
			break;
		case "/update":
			handleScheduleUpdate(request, response);
			break;
		}
	}

	/**
	 * 학사 일정 수정 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleScheduleUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int scheduleId = Integer.parseInt(request.getParameter("id"));
		System.out.println("scheduleId : " + scheduleId); // TODO - 삭제
		String information = request.getParameter("information");
		String startDayStr = request.getParameter("startDay");
		String endDayStr = request.getParameter("endDay");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 형식에 맞게 설정

		java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDayStr);
		java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDayStr);

		// 여기서 startDay와 endDay를 Schedule 객체에 설정하고 저장
		Schedule schedule = new Schedule();
		schedule.setStartDay(sqlStartDate);
		schedule.setEndDay(sqlEndDate);

		Schedule scheduleList = Schedule.builder().id(scheduleId).startDay(sqlStartDate).endDay(sqlEndDate)
				.information(information).build();
		scheduleRepository.updateSchedule(scheduleList);
		response.sendRedirect(request.getContextPath() + "/schedule/create");
//		request.setAttribute("scheduleList", scheduleList);
//		request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleUpdate.jsp").forward(request, response);

	}

	/**
	 * 학사 일정 추가 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleScheduleAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User principal = (User) request.getSession().getAttribute("principal");
		int staffId = principal.getId();

		String information = request.getParameter("information");
		String startDayStr = request.getParameter("startDay");
		String endDayStr = request.getParameter("endDay");

		java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDayStr);
		java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDayStr);

		// 여기서 startDay와 endDay를 Schedule 객체에 설정하고 저장
		Schedule schedule = new Schedule();
		schedule.setStartDay(sqlStartDate);
		schedule.setEndDay(sqlEndDate);

		Schedule scheduleListById = Schedule.builder().staffId(staffId).startDay(sqlStartDate).endDay(sqlEndDate)
				.information(information).build();
		scheduleRepository.addSchedule(scheduleListById);
		System.out.println("scheduleListById:" + scheduleListById); // TODO - 삭제
		response.sendRedirect(request.getContextPath() + "/schedule/create");
	}
}
