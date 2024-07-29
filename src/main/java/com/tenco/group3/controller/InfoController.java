package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.UserRepositoryImpl;
import com.tenco.group3.repository.interfaces.UserRepository;

@WebServlet("/info/*")
public class InfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserRepository userRepository;

	@Override
	public void init() throws ServletException {
		userRepository = new UserRepositoryImpl();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action: " + action); // TODO - 삭제 예정
		switch (action) {
		case "/student":
			handleInfoSelectStudent(request, response);
			break;
		case "/professor":
			handleInfoSelectProfessor(request, response);
			break;
		case "/staff":
			handleInfoSelectStaff(request, response);
			break;
		case "/update":
			handleUserInfo(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 마이페이지 유저 정보 기본값 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleUserInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User principal = (User) request.getSession().getAttribute("principal");
		
		if(principal.getUserRole().equals("student")) {
			Student studentInfo = userRepository.getStudentInfo(principal.getId());
			request.setAttribute("studentInfo", studentInfo);
			request.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(request, response);
		} else if(principal.getUserRole().equals("professor")) {
			Professor professorInfo = userRepository.getProfessorInfo(principal.getId());
			request.setAttribute("professorInfo", professorInfo);
			request.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(request, response);
		} else if(principal.getUserRole().equals("staff")) {
			Staff staffInfo = userRepository.getStaffInfo(principal.getId());
			request.setAttribute("staffInfo", staffInfo);
			request.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(request, response);
		} 
	}
	

	/**
	 * 직원 정보 조회(마이페이지)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleInfoSelectStaff(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User principal = (User) request.getSession().getAttribute("principal");
		if (principal != null) {
			Staff staffInfo = userRepository.getStaffInfo(principal.getId());
			if (principal.getId() == staffInfo.getId()) {
				request.setAttribute("staffInfo", staffInfo);
				request.getRequestDispatcher("/WEB-INF/views/user/staffInfo.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn"); // 로그인 페이지로
			return;
		}
	}

	/**
	 * 교수 정보 조회(마이페이지)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleInfoSelectProfessor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User principal = (User) request.getSession().getAttribute("principal");
		if (principal != null) {
			Professor professorInfo = userRepository.getProfessorInfo(principal.getId());
			if (principal.getId() == professorInfo.getId()) {
				request.setAttribute("professorInfo", professorInfo);
				request.getRequestDispatcher("/WEB-INF/views/user/professorInfo.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn"); // 로그인 페이지로
			return;
		}
	}

	/**
	 * 학생 정보 조회(마이페이지)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleInfoSelectStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 세션 조회
		User principal = (User) request.getSession().getAttribute("principal");
		// 2. 로그인 여부 확인
		if (principal != null) {

			// 3. 세션 아이디로 DB 정보 조회
			Student studentInfo = userRepository.getStudentInfo(principal.getId());
			List<Student> studentStat = userRepository.getStudentStat(principal.getId());

			if (principal.getId() == studentInfo.getId()) {
				request.setAttribute("studentInfo", studentInfo);
				request.setAttribute("studentStat", studentStat);
				
				// 4. jsp에 뿌리기
				request.getRequestDispatcher("/WEB-INF/views/user/studentInfo.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/user/logIn"); // 로그인 페이지로
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();
		System.out.println("action : " + action); // TODO - 삭제 예정

		switch (action) {
		case "/update":
			HandleUserInfoUpdate(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 유저 정보 수정
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void HandleUserInfoUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User principal = (User) request.getSession().getAttribute("principal");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String password = request.getParameter("password");

		// 비밀번호 확인
		if (principal.getPassword().equals(password)) {

			if (principal.getUserRole().equals("student")) {
				Student studentUpdate = Student.builder().address(address).email(email).tel(tel).id(principal.getId())
						.build();
				userRepository.getStudentInfoUpdate(studentUpdate);
				response.sendRedirect(request.getContextPath() + "/info/student");
				
			} else if (principal.getUserRole().equals("professor")) {
				Professor professorUpdate = Professor.builder().address(address).email(email).tel(tel)
						.id(principal.getId()).build();
				userRepository.getProfessorInfoUpdate(professorUpdate);
				response.sendRedirect(request.getContextPath() + "/info/professor");
				
			} else if (principal.getUserRole().equals("staff")) {
				Staff staffUpdate = Staff.builder().address(address).email(email).tel(tel).id(principal.getId())
						.build();
				userRepository.getStaffInfoUpdate(staffUpdate);
				response.sendRedirect(request.getContextPath() + "/info/staff");
				
			} else {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=UTF-8");
				out.println("<script> alert('비밀번호가 일치하지 않습니다.');");
				out.println("history.go(-1); </script>");
				out.close();
			}
		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script> alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.go(-1); </script>");
			out.close();
		}

	}
}
