package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

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
		Schedule schedule = scheduleRepository.getScheduleById(ScheduleId);
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

		String information = request.getParameter("information");
		String startDayStr = request.getParameter("startDay");
		String endDayStr = request.getParameter("endDay");

		try {
			Date sqlStartDate = Date.valueOf(startDayStr);
			Date sqlEndDate = Date.valueOf(endDayStr);

			if (sqlEndDate.equals(sqlStartDate) || sqlEndDate.after(sqlStartDate)) {
				// 여기서 startDay와 endDay를 Schedule 객체에 설정하고 저장
				Schedule schedule = new Schedule();
				schedule.setStartDay(sqlStartDate);
				schedule.setEndDay(sqlEndDate);

				if (information == null || information.isEmpty()) {
					sendError(response, "수정할 내용을 입력해주세요.");
					return;
				}
				Schedule scheduleList = Schedule.builder()
						.id(scheduleId)
						.startDay(sqlStartDate)
						.endDay(sqlEndDate)
						.information(information)
						.build();
				scheduleRepository.updateSchedule(scheduleList);
			} else {
				sendError(response, "종료 날짜를 시작 날짜 이후로 선택해주세요.");
			}
		} catch (IllegalArgumentException e) {
			sendError(response, "날짜를 입력해주세요.");
		}
		response.sendRedirect(request.getContextPath() + "/schedule/create");
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

		try {
			Date sqlStartDate = Date.valueOf(startDayStr);
			Date sqlEndDate = Date.valueOf(endDayStr);

			if (sqlEndDate.equals(sqlStartDate) || sqlEndDate.after(sqlStartDate)) {
				Schedule schedule = new Schedule();
				schedule.setStartDay(sqlStartDate);
				schedule.setEndDay(sqlEndDate);

				if (information == null || information.isEmpty()) {
					sendError(response, "내용을 입력해주세요.");
					return;
				}

				Schedule scheduleListById = Schedule.builder().staffId(staffId).startDay(sqlStartDate)
						.endDay(sqlEndDate).information(information).build();
				scheduleRepository.addSchedule(scheduleListById);
				request.setAttribute("scheduleListById", scheduleListById);
			} else {
				sendError(response, "종료 날짜를 시작 날짜 이후로 선택해주세요.");
			}
		} catch (IllegalArgumentException e) {
			sendError(response, "날짜를 입력해주세요.");
		}
		response.sendRedirect(request.getContextPath() + "/schedule/create");
	}

	// 에러 메시지
	private void sendError(HttpServletResponse response, String message) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		out.println("<script> alert('" + message + "');");
		out.println("history.go(-1); </script>");
		out.close();
	}
}
