package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.SubjectRepositoryImpl;
import com.tenco.group3.repository.interfaces.SubjectRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectRepository subjectRepository;
	
	public SubjectController() {
		super();
	}
	
	@Override
	public void init() throws ServletException {
		subjectRepository = new SubjectRepositoryImpl();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("principal") == null) {
			
			switch(action) {
			
			case "/allsubject ":
				showAllSubject(request, response);
				break;
			case"/subjectById":
				
				subjectById(request, response);
				break;
			}
			
			
		}
	
	
	}
	private void showAllSubject(HttpServletRequest request, HttpServletResponse response) throws SecurityException,IOException {
		
		
	}
	
	
	/**
	 * 교수 과목 조회 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */

	private void subjectById(HttpServletRequest request, HttpServletResponse response) throws SecurityException,IOException, ServletException {
		// TODO 유효성 검사
		String name =request.getParameter("name");
		int semester = Integer.parseInt(request.getParameter("semester"));
		int year  = Integer.parseInt(request.getParameter("year"));
		int  id  = Integer.parseInt(request.getParameter("id"));
		
		List<Subject> subjectlist = subjectRepository.getSubjectById(id, year, semester, name);
		request.setAttribute("subjectlist", subjectlist);
		request.getRequestDispatcher("/WEB-INF/views/subject/subject.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}
	
}
