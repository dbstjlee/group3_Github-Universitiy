package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
			//request.getRequestDispatcher("/WEB-INF/views/user/studentInfo.jsp").forward(request, response);
			handleInfoSelectStudent(request, response);
			break;
		case "/professor":
			request.getRequestDispatcher("/WEB-INF/views/user/professorInfo.jsp").forward(request, response);
			break;
		case "/staff":
			request.getRequestDispatcher("/WEB-INF/views/user/staffInfo.jsp").forward(request, response);
		default:
			break;
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

			if (principal.getId() == studentInfo.getId()) {
				request.setAttribute("studentInfo", studentInfo);
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
		switch (action) {
		case "/student":
			handleInfoUpdateStudent(request, response);
			break;
		case "/professor":
			handleInfoUpdateProfessor(request, response);
			break;
		case "/staff":
			handleInfoUpdateStaff(request, response);
			break;

		default:
			break;
		}

	}

	private void handleInfoUpdateStaff(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void handleInfoUpdateProfessor(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	private void handleInfoUpdateStudent(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
