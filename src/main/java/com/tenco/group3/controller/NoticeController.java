package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Notice;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.NoticeRepositoryImpl;
import com.tenco.group3.repository.interfaces.NoticeRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private NoticeRepository noticeRepository;

	@Override
	public void init() throws ServletException {
		noticeRepository = new NoticeRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("principal") == null) {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
			return;
		}
		String action = request.getPathInfo();
		switch (action) {
		case "/delete":
			handleDeleteNotice(request, response);
			break;
		case "/read":
			showDetailViewNotice(request, response);
			break;
		case "/update":
			showEditNoticeForm(request, response);
			break;
		case "/list":
			handleListNotices(request, response);
			break;
		case "/create":
			showCreateNoticeForm(request, response);
			break;
		case "/search":
			handleSearchNotice(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

	}

	/**
	 * 공지사항 검색 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleSearchNotice(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String searchType = request.getParameter("type");
	    String keyword = request.getParameter("keyword");

		List<Notice> noticeList = new ArrayList<>();
	    
	    if (searchType.equals("title")) {
	        // 제목으로 검색
	        noticeList = noticeRepository.searchTitle(keyword);
	    } else if (searchType.equals("keyword")) {
	        // 제목 + 내용으로 검색
	        noticeList = noticeRepository.searchTitleAndContent(keyword);
	    }

	    request.setAttribute("noticeList", noticeList);
		request.getRequestDispatcher("/WEB-INF/views/notice/searchResult.jsp").forward(request, response);
	}

	/**
	 * 공지사항 등록 화면 이동
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showCreateNoticeForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User principal = (User) request.getSession().getAttribute("principal");
		if (principal.getUserRole().equals("staff")) {
			request.getRequestDispatcher("/WEB-INF/views/notice/create.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
		}
	}

	/**
	 * 공지사항 수정 화면 이동
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showEditNoticeForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		int noticeId = Integer.parseInt(request.getParameter("id"));
		Notice notice = noticeRepository.getNoticeById(noticeId);
		if (notice == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice/edit.jsp").forward(request, response);

	}

	/**
	 * 전체 공지사항 조회
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleListNotices(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1; // 기본 페이지 번호
		int pageSize = 6; // 페이지 당 공지사항 개수

		try {
			String pageStr = request.getParameter("page");

			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			page = 1;
		}

		int offset = (page - 1) * pageSize;

		List<Notice> noticeList = noticeRepository.getAllNotice(pageSize, offset);

		// 전체 공지사항 개수 조회
		int totalNotices = noticeRepository.getTotalNoticeCount();

		// 총 페이지 수 계산
		int totalpages = (int) Math.ceil((double) totalNotices / pageSize);

		request.setAttribute("noticeList", noticeList);
		request.setAttribute("totalPages", totalpages);
		request.setAttribute("currentPage", page);

		request.getRequestDispatcher("/WEB-INF/views/notice/notice.jsp").forward(request, response);
	}

	/**
	 * 공지사항 삭제 직원일 때만
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleDeleteNotice(HttpServletRequest request, HttpServletResponse response) throws IOException {

		User principal = (User) request.getSession().getAttribute("principal");
		int noticeId = Integer.parseInt(request.getParameter("id"));

		if (principal.getUserRole().equals("staff")) {
			noticeRepository.deleteNotice(noticeId);
			response.sendRedirect(request.getContextPath() + "/notice/list");
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
		}

	}

	/**
	 * 공지사항 상세 보기 전체
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showDetailViewNotice(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		int noticeId = Integer.parseInt(request.getParameter("id"));

		Notice notice = noticeRepository.getNoticeById(noticeId);
		if (notice == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 조회수 증가
		noticeRepository.incrementViewCount(noticeId);

		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice/read.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/update":
			handleEditNotice(request, response);
			break;
		case "/create":
			handleCreateNotice(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 공지사항 수정 기능 직원일 때만
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleEditNotice(HttpServletRequest request, HttpServletResponse response) throws IOException {

		User principal = (User) request.getSession().getAttribute("principal");
		int noticeId = Integer.parseInt(request.getParameter("id"));
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");

		if (principal.getUserRole().equals("staff")) {
			Notice notice = Notice.builder().id(noticeId).title(noticeTitle).content(noticeContent).build();
			noticeRepository.updateNotice(notice);
			response.sendRedirect(request.getContextPath() + "/notice/list");
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
		}
	}

	/**
	 * 공지사항 등록 기능 직원일 때만
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */

	private void handleCreateNotice(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		User principal = (User) request.getSession().getAttribute("principal");

		if (principal.getUserRole().equals("staff")) {
			Notice notice = Notice.builder().category(category).title(title).content(content).build();
			noticeRepository.addNotice(notice);
			response.sendRedirect(request.getContextPath() + "/notice/list");
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
		}
	}

}
