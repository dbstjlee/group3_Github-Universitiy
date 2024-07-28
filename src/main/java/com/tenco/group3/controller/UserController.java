package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.tenco.group3.model.Professor;
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
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserRepository userRepository;

	public UserController() {

	}

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
		case "/findId":
			request.getRequestDispatcher("/WEB-INF/views/user/findId.jsp").forward(request, response);
			break;
		case "/findPwd":
			request.getRequestDispatcher("/WEB-INF/views/user/findPwd.jsp").forward(request, response);
			break;
		case "/password":
			request.getRequestDispatcher("/WEB-INF/views/user/updatePassword.jsp").forward(request, response);
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
		case "/password":
			handleUpdatePassword(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}
	
	/**
	 * 알림 메시지
	 * 
	 * @param response
	 * @param message
	 * @throws IOException
	 */
	private void sendMessage(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script>alert('" + message + "');");
        out.println("history.go(-1);</script>");
        out.close();
    }

	/**
	 * 비밀번호 변경 기능
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void handleUpdatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 User principal = (User) request.getSession().getAttribute("principal");
	        String currentPassword = request.getParameter("currentPassword");
	        String newPassword = request.getParameter("newPassword");
	        String confirmPassword = request.getParameter("confirmPassword");

	        if (principal == null) {
	            response.sendRedirect(request.getContextPath() + "/login");
	            return;
	        }

	        if (!principal.getPassword().equals(currentPassword)) {
	            // 현재 비밀번호가 일치하지 않는 경우
	        	sendMessage(response, "현재 비밀번호가 일치하지 않습니다.");
	            return;
	        }

	        if (!newPassword.equals(confirmPassword)) {
	            // 새 비밀번호와 확인 비밀번호가 일치하지 않는 경우
	        	sendMessage(response, "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
	            return;
	        }

	        // 비밀번호 변경
	        User updatedUser = User.builder()
	                .id(principal.getId())
	                .password(newPassword)
	                .build();

	        userRepository.getUpdatePassword(updatedUser);
	        sendMessage(response, "비밀번호가 변경되었습니다. 비밀번호 재변경 시 로그아웃 후 사용해주세요.");
	    }

	/**
	 * id 찾기
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
		String userRole = request.getParameter("userRole");
		User user = null;

		// TODO - 유효성 검사

			// 학생일 때
			if (userRole.equals("student")) {
				user = userRepository.getStudentByNameAndEmail(name, email);
				
			// 교수일 때
			} else if (userRole.equals("professor")) {
				user = userRepository.getProfessorByNameAndEmail(name, email);

			// 직원일 때
			} else if (userRole.equals("staff")) {
				user = userRepository.getStaffByNameAndEmail(name, email);
			}
			if(user == null) {
				sendMessage(response, "아이디를 찾을 수 없습니다.");
			}
			request.setAttribute("user", user);
			request.setAttribute("userRole", userRole);
			request.getRequestDispatcher("/WEB-INF/views/user/findSuccess.jsp").forward(request, response);
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

			// 체크박스 - 체크 상태일 때
			if ("on".equals(rememberId)) {
				Cookie cookie = new Cookie("id", String.valueOf(id));
				cookie.setMaxAge(60 * 60 * 24);
				response.addCookie(cookie);
			} else { // 체크 박스 - 체크 해제 상태일 때
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
			sendMessage(response, "아이디 또는 비밀번호가 틀립니다.");

			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
		}
	}
}
