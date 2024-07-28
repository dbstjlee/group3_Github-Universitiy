package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.Department;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Syllabus;
import com.tenco.group3.repository.DepartmentRepositoryImpl;
import com.tenco.group3.repository.SubjectRepositoryImpl;
import com.tenco.group3.repository.interfaces.DepartmentRepository;
import com.tenco.group3.repository.interfaces.SubjectRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/subject/*")
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectRepository subjectRepository;
	private DepartmentRepository departmentRepository;
	public SubjectController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		subjectRepository = new SubjectRepositoryImpl();
		departmentRepository =new DepartmentRepositoryImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println(action);
//		HttpSession session = request.getSession(false);
//		if (session != null || session.getAttribute("principal") != null) {

			switch (action) {

			case "/allSubject":
				showAllSubject(request, response);
				break;
			case "/subjectById":
				subjectById(request, response);
				break;
			case "/search":
				searchSubject(request, response);
				break;
			case "/syllabus":

				showSyllabus(request, response);

				break;
				
			}

		}

//	}

	private void showAllSubject(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IOException {
		
		int page = 1; 
		int pageSize = 20; 
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
			e.printStackTrace();
		}
		int offset = (page - 1) * pageSize;
		int totalCount = subjectRepository.getAllSubjectCount();	
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);
		
		List<Subject> subjectlist = subjectRepository.getSubjectAll(pageSize,offset);
		List<Department> departList = departmentRepository.getAllDepartment();
		request.setAttribute("subjectList", subjectlist);
		request.setAttribute("departList", departList);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		System.out.println("departList :" + departList);
		try {
			request.getRequestDispatcher("/WEB-INF/views/subject/subject.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void searchSubject(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IOException {
		int page = 1; 
		int pageSize = 20; 
		int year = Integer.parseInt(request.getParameter("subYear"));
		int semester = Integer.parseInt(request.getParameter("subSemester"));
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		String name = request.getParameter("subName");
		
		try {
			
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
			e.printStackTrace();
		}
		
		int offset = (page - 1) * pageSize;
		List<Subject> subjectlist = subjectRepository.searchSubject(year, semester, name,deptId, pageSize,offset);
		int totalCount = subjectRepository.getSearchSubjectCount(year, semester, name, deptId);
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);
		
		request.setAttribute("subjectList", subjectlist);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
		try {
			request.getRequestDispatcher("/WEB-INF/views/subject/subject.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 교수 과목 조회
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */

	
	
	
	
	private void subjectById(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IOException, ServletException {
		// TODO 유효성 검사
		String name = request.getParameter("name");
		int semester = Integer.parseInt(request.getParameter("semester"));
		int year = Integer.parseInt(request.getParameter("year"));
		int id = Integer.parseInt(request.getParameter("id"));

		List<Subject> subjectlist = subjectRepository.getSubjectById(id, year, semester, name);
		request.setAttribute("subjectlist", subjectlist);
		request.getRequestDispatcher("/WEB-INF/views/subject/subject.jsp").forward(request, response);
	}
	
	
	
	private void seeSyllabus(HttpServletRequest request, HttpServletResponse response)
			throws SecurityException, IOException, ServletException {
	
		request.getRequestDispatcher("/WEB-INF/views/syllabus/syllabus.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	private void showSyllabus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            Syllabus syllabus = subjectRepository.getSyllabusById(subjectId);
            request.setAttribute("syllabus", syllabus);
            request.getRequestDispatcher("/WEB-INF/views/subject/syllabus.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "잘못된 접근");
            return;
        }
    }

}
