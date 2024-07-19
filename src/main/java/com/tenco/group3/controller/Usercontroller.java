package com.tenco.group3.controller;
import java.io.IOException;

import com.tenco.group3.model.User;
import com.tenco.group3.repository.userRepositoryImpl;
import com.tenco.group3.repository.interfaces.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/*")
public class Usercontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserRepository userRepository;
 
    public Usercontroller() {
    	
    }
    
    @Override
    public void init() throws ServletException {
    	userRepository = new userRepositoryImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action: " + action);
		switch (action) {
		case "/logIn":
			request.getRequestDispatcher("/WEB-INF/views/user/logIn.jsp").forward(request, response);
			break;
		case "/logOut":
			handleLogout(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
		
	}

	/**
	 * 로그아웃 기능
	 * @param request
	 * @param request2
	 * @throws IOException 
	 */
	private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/user/login");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/logIn":
			handleLogin(request, response);
			break;

		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 로그인 기능
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		User principal = userRepository.getUserById(id);
		
		if(principal != null && principal.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("principal", principal);
			response.sendRedirect(request.getContextPath() + "/main"); // 로그인 성공 - 메인 홈으로 이동
		} else {
			// 에러 메시지 (로그인 실패)
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
		}
		
	}

}
