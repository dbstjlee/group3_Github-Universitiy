package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.Sugang;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.SugangRepositoryImpl;
import com.tenco.group3.repository.interfaces.SugangRepository;
import com.tenco.group3.util.AlertUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sugang/*")
public class SugangController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SugangRepository sugangRepository;

	@Override
	public void init() throws ServletException {
		sugangRepository = new SugangRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getPathInfo();
		switch (action) {
		case "/subjectList":
			showSubjectList(request, response, session);
			break;
		case "/subjectList/search":
			showSearchSubject(request, response, session);
			break;
		case "/pre":
			showPreliminaryList(request, response, session);
			break;
		case "/pre/search":
			showSearchPreliminary(request, response, session);
			break;
		case "/preAppList":
			showPreliminaryAppList(request, response, session);
			break;
		default:
			break;
		}
	}

	/**
	 * 예비 수강 신청 내역
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showPreliminaryAppList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("principal");

		List<Sugang> sugangList = sugangRepository.getApplicatedSubjectList(user.getId());
		int totalGrade = sugangRepository.getSubjectGrade(user.getId());
		System.out.println(sugangList);
		request.setAttribute("sugangList", sugangList);
		request.setAttribute("totalGrade", totalGrade);
		request.getRequestDispatcher("/WEB-INF/views/sugang/preAppList.jsp").forward(request, response);
	}

	/**
	 * 예비 수강 신청 검색
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showSearchPreliminary(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		int deptId;
		try {
			deptId = Integer.parseInt(request.getParameter("deptId"));
		} catch (NumberFormatException e) {
			deptId = -1;
			e.printStackTrace();
		}
		String name = request.getParameter("name");
		if (name == null || name.trim().isEmpty()) {
			name = null;
		}
		Sugang sugang = Sugang.builder().subjectType(type).deptId(deptId).subjectName(name).build();
		List<Sugang> sugangList = sugangRepository.getSubjectBySearch(sugang);
		int totalCount = sugangRepository.getAllSubjectCount();
		request.setAttribute("sugangList", sugangList);
		request.setAttribute("totalCount", totalCount);
		request.getRequestDispatcher("/WEB-INF/views/sugang/preSearch.jsp").forward(request, response);
	}

	/**
	 * 예비 수강 신청 페이지 이동
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showPreliminaryList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		int page = 1; // 기본 페이지 번호
		int pageSize = 20; // 한 페이지당 보여질 게시글의 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			// 유효하지 않은 번호를 입력 받은 경우
			page = 1;
			e.printStackTrace();
		}
		int offset = (page - 1) * pageSize;
		int totalCount = sugangRepository.getAllSubjectCount();

		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		List<Sugang> sugangList = sugangRepository.getAllSubject(pageSize, offset);
		request.setAttribute("sugangList", sugangList);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
//		System.out.println(sugangList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/pre.jsp").forward(request, response);
	}

	/**
	 * 강의 검색
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showSearchSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		int deptId;
		try {
			deptId = Integer.parseInt(request.getParameter("deptId"));
		} catch (NumberFormatException e) {
			deptId = -1;
			e.printStackTrace();
		}
		String name = request.getParameter("name");
		if (name == null || name.trim().isEmpty()) {
			name = null;
		}
		System.out.println("type : " + type);
		System.out.println("deptId : " + deptId);
		System.out.println("name : " + name);
		Sugang sugang = Sugang.builder().subjectType(type).deptId(deptId).subjectName(name).build();
		List<Sugang> sugangList = sugangRepository.getSubjectBySearch(sugang);
		int totalCount = sugangRepository.getAllSubjectCount();
		request.setAttribute("sugangList", sugangList);
		request.setAttribute("totalCount", totalCount);
		request.getRequestDispatcher("/WEB-INF/views/sugang/subjectList.jsp").forward(request, response);
	}

	/**
	 * 강의 리스트 출력
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showSubjectList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		int page = 1; // 기본 페이지 번호
		int pageSize = 20; // 한 페이지당 보여질 게시글의 수
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			// 유효하지 않은 번호를 입력 받은 경우
			page = 1;
			e.printStackTrace();
		}
		int offset = (page - 1) * pageSize;
		int totalCount = sugangRepository.getAllSubjectCount();

		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		List<Sugang> sugangList = sugangRepository.getAllSubject(pageSize, offset);
		request.setAttribute("sugangList", sugangList);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("currentPage", page);
//		System.out.println(sugangList);
		request.getRequestDispatcher("/WEB-INF/views/sugang/subjectList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getPathInfo();
		switch (action) {
		case "/pre":
			handlerPreliminaryList(request, response, session);
			break;
		default:
			break;
		}
	}

	/**
	 * 예비 수강 신청
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	private void handlerPreliminaryList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		User user = (User) session.getAttribute("principal");
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		int rowCount = sugangRepository.addEnrolment(user.getId(), subjectId);
		if (rowCount != 0) {
			response.sendRedirect(request.getContextPath() + "/sugang/pre");
		} else {
			AlertUtil.errorAlert(response, "정원 초과된 강의입니다.");
		}
	}

}
