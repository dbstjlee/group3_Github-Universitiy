package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.repository.BreakAppRepositoryImpl;
import com.tenco.group3.repository.interfaces.BreakAppRepository;

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
		HttpSession session = request.getSession();
		if (session == null) {
			// TODO - 로그인 화면으로 이동
			return;
		}
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
//		int studentId = Integer.parseInt(request.getParameter("id"));
		BreakApp breakApp = breakAppRepository.getBreakAppDetail(0);
		if (breakApp == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
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
//		int studentId = Integer.parseInt(request.getParameter("id"));
//		List<BreakApp> breakList = breakAppRepository.getBreakAppList(studentId);
//		if (breakList == null) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
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
		case "/list":
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
	 */
	private void handlerApplication(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

	}

}
