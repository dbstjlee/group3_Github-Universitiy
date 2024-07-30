package com.tenco.group3.controller;

import java.io.IOException;

import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.model.Tuition;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.TuitionRepositoryImpl;
import com.tenco.group3.repository.interfaces.TuitionRepository;
import com.tenco.group3.util.AlertUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/tuition/*")
public class TuitionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TuitionRepository tuitionRepository;

	@Override
	public void init() throws ServletException {
		tuitionRepository = new TuitionRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		switch (action) {
		case "/payment":
			showPaymentTuition(request, response, session);
			break;
		case "/check":
			showListTuition(request, response, session);
			break;
		default:
			break;
		}
	}

	/**
	 * 등록금 내역 조회 페이지 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showListTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("principal");
		Tuition checkTuition = tuitionRepository.getSummaryTuitionByStudentId(user.getId());

//		System.out.println(tuition.toString());
		request.setAttribute("checkTuition", checkTuition);
		request.getRequestDispatcher("/WEB-INF/views/tuition/check.jsp").forward(request, response);
	}

	/**
	 * 등록금 납부 고지서 화면 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showPaymentTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("principal");
		Tuition paymentTuition = tuitionRepository.getTuitionByStudentId(user.getId());
		int isTuition = (int) getServletContext().getAttribute("tuition");
		
		if(isTuition == ScheduleState.TRUE) {
//		System.out.println(tuition.toString());
			request.setAttribute("paymentTuition", paymentTuition);
			request.getRequestDispatcher("/WEB-INF/views/tuition/payment.jsp").forward(request, response);
		} else {
			AlertUtil.backAlert(response, "등록금 납부 기간이 아닙니다.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		switch (action) {
		case "/payment":
			handlerSubmitTuition(request, response, session);
			break;
		default:
			break;
		}
	}

	/**
	 * 등록금 납부 기능
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handlerSubmitTuition(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("principal");
		tuitionRepository.submitTuition(user.getId());
		response.sendRedirect(request.getContextPath() + "/tuition/payment");
	}
}
