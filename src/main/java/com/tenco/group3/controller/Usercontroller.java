package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.UserRepositoryImpl;
import com.tenco.group3.repository.interfaces.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/*")
public class Usercontroller extends HttpServlet {
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
		case "/logIn":
			request.getRequestDispatcher("/WEB-INF/views/user/logIn.jsp").forward(request, response);
			break;
		case "/logOut":
			handleLogout(request, response);
			break;
		case "/password":
			request.getRequestDispatcher("/WEB-INF/views/user/pwd.jsp").forward(request, response);
			break;
		case "/update":
			request.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(request, response);
			break;
		case "/findId":
			request.getRequestDispatcher("/WEB-INF/views/user/findId.jsp").forward(request, response);
			break;
		case "/findPwd":
			request.getRequestDispatcher("/WEB-INF/views/user/findPwd.jsp").forward(request, response);
			break;
		case "/findSuccess":
			request.getRequestDispatcher("/WEB-INF/views/user/findSuccess.jsp").forward(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

	}

	/**
	 * 로그아웃 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/user/logIn");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/logIn":
			handleLogin(request, response);
			break;
		case "/findId":
			handleFindId(request, response);
			break;

		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * id 찾기
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleFindId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 이름, 이메일
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		Student student = userRepository.getStudentByNameAndEmail(name, email);
		if (student != null) {
			// 이름과 id 값 보여주기
			// id 찾기 성공
			response.sendRedirect(request.getContextPath() + "/user/findSuccess");
			// 값 넣어주기
			request.setAttribute("student", student);

		} else {
			// 에러 창 띄우기(alert)
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script> alert('아이디를 찾을 수 없습니다.');");
			out.println("history.go(-1); </script>");
			out.close();
		}
	}

	/**
	 * 로그인 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String password = request.getParameter("password");
		String rememberId = request.getParameter("rememberId");
		User principal = userRepository.getUserById(id);

		if (principal != null && principal.getPassword().equals(password)) {
			HttpSession session = request.getSession();

			if ("on".equals(rememberId)) {
				Cookie cookie = new Cookie("id", String.valueOf(id));
				cookie.setMaxAge(60 * 60 * 24);
				response.addCookie(cookie);
			} else {
				if (rememberId == null) {
					Cookie[] cookies = request.getCookies();
					for (Cookie cookie : cookies) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
			session.setAttribute("principal", principal);
			response.sendRedirect(request.getContextPath() + "/"); // 로그인 성공 - 메인 홈으로 이동
		} else {
			// 에러 메시지 (로그인 실패)
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script> alert('아이디 또는 비밀번호가 틀립니다.');");
			out.println("history.go(-1); </script>");
			out.close();

			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
		}

	}

}
