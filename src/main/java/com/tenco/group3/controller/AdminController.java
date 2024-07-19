package com.tenco.group3.controller;

import java.io.IOException;

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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getPathInfo();
		
		switch (action) {
		case "college":
			request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp");
			break;
		case "department":
			
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
	}

}
