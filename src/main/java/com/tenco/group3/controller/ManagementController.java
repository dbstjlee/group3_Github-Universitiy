package com.tenco.group3.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/management/*")
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagementController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "/main/login");
			return;
		}
		// TODO 관리자 아이디가 아니면 이전 페이지로 돌아가게함
		
		switch (action) {
		case "studentList":
			
			break;

		default:
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
