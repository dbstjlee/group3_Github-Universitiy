package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.College;
import com.tenco.group3.repository.CollegeRepositoryImpl;
import com.tenco.group3.repository.interfaces.CollegeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CollegeRepository collegeRepository;

	@Override
	public void init() throws ServletException {
		collegeRepository = new CollegeRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
//		HttpSession session = request.getSession(false);
//		if(session == null || session.getAttribute("principal") == null ) {
//			response.sendRedirect(request.getContextPath() + "");
//		}

		System.out.println("action : " + action);
		switch (action) {
		case "/college":
			collegeList(request, response);
			// request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp");
			break;
		case "delete":
			deleteCollege(request, response);
			break;
		default:
			collegeList(request, response);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void deleteCollege(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	/**
	 * 단과대학 조회
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void collegeList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("메서드 호출됨");
		List<College> colleges = collegeRepository.getAllCollege();
		request.setAttribute("colleges", colleges);
		request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

		System.out.println("action : " + action);
		switch (action) {
		case "/addCollege":
			handleAddCollege(request, response);
			break;

		default:
			break;
		}
	}

	/**
	 * 단과대학 등록 기능
	 * @param request
	 * @param response
	 */
	private void handleAddCollege(HttpServletRequest request, HttpServletResponse response) {
		int collegeId = Integer.parseInt(request.getParameter("collegeId"));
		String collegename= request.getParameter("collegeName");

		College college = College.builder().id(collegeId).name(collegename).build();
		collegeRepository.addCollege(college);
		
	}
}
