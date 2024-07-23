package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.repository.ManagementRepositoryImpl;
import com.tenco.group3.repository.interfaces.ManagementRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/management/*")
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ManagementRepository managementRepository;

	public ManagementController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		managementRepository = new ManagementRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		// TODO 현재 로그인 기능 없어서 생략함
//		HttpSession session = request.getSession(false);
//		if (session == null || session.getAttribute("principal") == null) {
//			response.sendRedirect(request.getContextPath() + "/user/logIn");
//			return;
//		}
		// TODO 관리자 아이디가 아니면 이전 페이지로 돌아가게함

		switch (action) {
		case "/studentList":
			showStudentList(request, response);
			break;
		case "/professorList":
			showProfessorList(request, response);
			break;
		case "/student":
			request.getRequestDispatcher("/WEB-INF/views/management/registStudentForm.jsp").forward(request, response);
			break;
		case "/professor":
			request.getRequestDispatcher("/WEB-INF/views/management/registProfessorForm.jsp").forward(request, response);
			break;
		case "/staff":
			request.getRequestDispatcher("/WEB-INF/views/management/registStaffForm.jsp").forward(request, response);
			break;
		case "/tuition":
			request.getRequestDispatcher("/WEB-INF/views/management/tuitionForm.jsp").forward(request, response);
			break;
		case "/bill":
			handleCreateTuition(request, response);
			break;
		case "/break":
			showBreakPage(request, response);
			break;
		case "/breakStart":
			handleBreakState(request, response, true);
			break;
		case "/breakEnd":
			handleBreakState(request, response, false);
			break;
		default:
			break;
		}
	}


	/**
	 * 학생 목록 조회 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showStudentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징 처리를 위한 변수 선언
		int page = 1; // 기본 페이지 번호
		int pageSize = 20; // 한 페이지당 보여질 게시글 수

		System.out.println("테스트용"); // TODO 삭제 예정
		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			// 유효하지 않은 번호를 마음대로 보낼 경우
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산 (offset 값 계산)

		List<Student> studentList = managementRepository.getAllStudents(pageSize, offset);

		// 전체 학생 수 조회
		int totalStudents = managementRepository.getTotalStudentCount();

		// 총 페이지 수 계산
		int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

		request.setAttribute("totalPages", totalPages);
		request.setAttribute("studentList", studentList);
		request.setAttribute("currentPage", page);

		request.getRequestDispatcher("/WEB-INF/views/management/studentList.jsp").forward(request, response);
	}

	private void showProfessorList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이징 처리를 위한 변수 선언
		int page = 1; // 기본 페이지 번호
		int pageSize = 20; // 한 페이지당 보여질 게시글 수

		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
		} catch (Exception e) {
			// 유효하지 않은 번호를 마음대로 보낼 경우
			page = 1;
		}
		int offset = (page - 1) * pageSize; // 시작 위치 계산 (offset 값 계산)

		List<Professor> professorList = managementRepository.getAllProfessors(pageSize, offset);

		// 전체 학생 수 조회
		int totalProfessors = managementRepository.getTotalProfessorCount();

		// 총 페이지 수 계산
		int totalPages = (int) Math.ceil((double) totalProfessors / pageSize);

		request.setAttribute("totalPages", totalPages);
		request.setAttribute("professorList", professorList);
		request.setAttribute("currentPage", page);

		request.getRequestDispatcher("/WEB-INF/views/management/professorList.jsp").forward(request, response);
	}
	
	/**
	 * 등록금 고지서 발송
	 * @param request
	 * @param response
	 */
	private void handleCreateTuition(HttpServletRequest request, HttpServletResponse response) {
		// TODO 등록금 고지 대상 확인
		// 1. 학적이 재학인 경우
		
	}
	
	/**
	 * 휴학 처리 페이지 호출 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void showBreakPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int isBreak = managementRepository.getScheduleStat("break"); // TODO 휴학 상태 받아와야됨
		if (isBreak == -1) {
			// TODO 오류 발생 오류 처리
			return;
		}
		request.setAttribute("isBreak", isBreak);
		request.getRequestDispatcher("/WEB-INF/views/management/break.jsp").forward(request, response);
	}
	
	/**
	 * 휴학 기간 상태 변경
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void handleBreakState(HttpServletRequest request, HttpServletResponse response, boolean state) throws ServletException, IOException {
		managementRepository.updateSchedule("break", state);
		showBreakPage(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		// TODO 현재 로그인 기능 없어서 생략함
//		HttpSession session = request.getSession(false);
//		if (session == null || session.getAttribute("principal") == null) {
//			response.sendRedirect(request.getContextPath() + "/user/login");
//			return;
//		}
		// TODO 관리자 아이디가 아니면 이전 페이지로 돌아가게함
		switch (action) {
		case "/student":
			handleCreateStudent(request, response);
			break;
		case "/professor":
			handleCreateProfessor(request, response);
			break;
		case "/staff":
			handleCreateStaff(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * 학사관리 학생 등록 학생 테이블, 유저 테이블 동시 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleCreateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 유효성 검사
		try {
			Student student = Student.builder()
				.name(request.getParameter("name"))
				.birthDate(Date.valueOf(request.getParameter("birthDate")))
				.gender(request.getParameter("gender"))
				.address(request.getParameter("address"))
				.tel(request.getParameter("tel"))
				.email(request.getParameter("email"))
				.deptId(Integer.parseInt(request.getParameter("deptId")))
				.entranceDate(Date.valueOf(request.getParameter("entranceDate")))
				.build();
			if (managementRepository.createStudent(student)) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter()
					.println(
							"<script> alert('등록 성공'); " + "window.location.href = '" + request.getContextPath() + "/management/student';  </script>");
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
			}
		} catch (Exception e) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
		}
	}

	private void handleCreateProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("들어옴");
		// TODO 유효성 검사
		try {
			Professor professor = Professor.builder()
				.name(request.getParameter("name"))
				.birthDate(Date.valueOf(request.getParameter("birthDate")))
				.gender(request.getParameter("gender"))
				.address(request.getParameter("address"))
				.tel(request.getParameter("tel"))
				.email(request.getParameter("email"))
				.deptId(Integer.parseInt(request.getParameter("deptId")))
				.build();
			if (managementRepository.createProfessor(professor)) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("<script> alert('등록 성공'); </script>");
				response.getWriter()
					.println("<script> alert('등록 성공'); " + "window.location.href = '" + request.getContextPath()
							+ "/management/professor';  </script>");
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
		}
	}

	private void handleCreateStaff(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO 유효성 검사
		try {
			Staff staff = Staff.builder()
				.name(request.getParameter("name"))
				.birthDate(Date.valueOf(request.getParameter("birthDate")))
				.gender(request.getParameter("gender"))
				.address(request.getParameter("address"))
				.tel(request.getParameter("tel"))
				.email(request.getParameter("email"))
				.build();
			if (managementRepository.createStaff(staff)) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter()
					.println("<script> alert('등록 성공'); " + "window.location.href = '" + request.getContextPath() + "/management/staff';  </script>");
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
			}
		} catch (Exception e) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
		}
	}

}
