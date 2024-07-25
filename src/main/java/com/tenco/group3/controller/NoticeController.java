package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.Notice;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.NoticeRepositoryImpl;
import com.tenco.group3.repository.interfaces.NoticeRepository;

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
		default:
			break;
		}

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
		int list = 1; // 기본 페이지 번호
		int listSize = 6; // TODO - 페이지 당 공지사항 개수 확인

		try {
			String listStr = request.getParameter("list");

			if (listStr != null) {
				list = Integer.parseInt(listStr);
			}

		} catch (Exception e) {
			list = 1;
		}

		// TODO - offset 처리 확인
		int offset = (list - 1) * listSize;

		List<Notice> noticeList = noticeRepository.getAllNotice(listSize, offset);

		// 전체 공지사항 개수 조회
		int totalNotices = noticeRepository.getTotalNoticeCount();

		// 총 페이지 수 계산
		int totalLists = (int) Math.ceil(totalNotices / listSize);

		request.setAttribute("noticeList", noticeList);
		request.setAttribute("totalLists", totalLists);
		request.setAttribute("currentList", list);

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
			// Notice notice = noticeRepository.getNoticeById(noticeId); // TODO - 이게 꼭 필요한지
			// 확인
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
		// TODO - 다시 확인
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
		System.out.println("noticeId : " + noticeId); //TODO - 삭제
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
			response.sendRedirect(request.getContextPath() + "/notice/list"); // TODO - 확인
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
		}
	}

}
