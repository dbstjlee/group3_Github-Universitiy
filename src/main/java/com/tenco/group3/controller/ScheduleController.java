package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tenco.group3.model.Notice;
import com.tenco.group3.model.Schedule;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.ScheduleRepositoryImpl;
import com.tenco.group3.repository.interfaces.ScheduleRepository;

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
	 * 학사 일정 수정 폼
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void showScheduleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ScheduleId = Integer.parseInt(request.getParameter("id"));

		Schedule schedule = scheduleRepository.getScheduleById(ScheduleId); // TODO - 다시
		request.setAttribute("schedule", schedule);
		request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleDetail.jsp").forward(request, response);

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
		int ScheduleId = Integer.parseInt(request.getParameter("id"));
		System.out.println("ScheduleId:" + ScheduleId);
		Schedule schedule = scheduleRepository.getScheduleById(ScheduleId);
		request.setAttribute("schedule", schedule);
		request.getRequestDispatcher("/WEB-INF/views/schedule/scheduleDetail.jsp").forward(request, response);

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
		case "/create":
			handleScheduleListCreate(request, response); // TODO
			break;
		case "/update":
			handleScheduleUpdate(request, response); // TODO
			break;
		}
	}

	/**
	 * 학사 일정 수정 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleScheduleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int scheduleId = Integer.parseInt(request.getParameter("id"));
		System.out.println("scheduleId : " + scheduleId); // TODO - 삭제
		String information = request.getParameter("information");
		String startDayStr = request.getParameter("startDay");
		String endDayStr = request.getParameter("endDay");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 형식에 맞게 설정

		try {
			Date startDay = formatter.parse(startDayStr);
			Date endDay = formatter.parse(endDayStr);

			// 여기서 startDay와 endDay를 Schedule 객체에 설정하고 저장
			Schedule schedule = new Schedule();
			schedule.setStartDay(startDay);
			schedule.setEndDay(endDay);

			Schedule scheduleList = Schedule.builder()
					.id(scheduleId)
					.startDay(startDay)
					.endDay(endDay)
					.information(information)
					.build();
			scheduleRepository.updateSchedule(scheduleList);
			request.setAttribute("scheduleList", scheduleList);
			response.sendRedirect(request.getContextPath() + "/schedule/update");
		} catch (ParseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
		}

	}

	private void handleScheduleListCreate(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}
}
