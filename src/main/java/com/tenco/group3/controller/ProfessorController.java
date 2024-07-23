package com.tenco.group3.controller;

import java.io.IOException;

import com.tenco.group3.repository.ProfessorRepositoryImpl;
import com.tenco.group3.repository.interfaces.ProfessorRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/professor/*")
public class ProfessorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfessorRepository professorRepository;
	
	@Override
	public void init() throws ServletException {
		professorRepository = new ProfessorRepositoryImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("pricipal") == null ) {
			response.sendRedirect(request.getContextPath() + "");
		}
		
		switch (action) {
		case "/subject":
			
			break;
		case "":
			
			break;
		default:
			
			break;
		}
		
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
	}
	
}
