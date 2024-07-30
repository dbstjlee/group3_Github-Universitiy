package com.tenco.group3.controller;

import java.io.IOException;

import com.tenco.group3.model.User;
import com.tenco.group3.repository.UserRepositoryImpl;
import com.tenco.group3.repository.interfaces.UserRepository;
import com.tenco.group3.util.AlertUtil;
import com.tenco.group3.util.Define;
import com.tenco.group3.util.PasswordUtil;
import com.tenco.group3.util.ValidationUtil;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/logIn":
			handleLogin(request, response);
			break;
		case "/findId":
			handleFindId(request, response);
			break;
		case "/findPwd":
			handleFindPwd(request, response);
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
	 * 비밀번호 임시 발급
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @throws NumberFormatException
	 * @throws NullPointerException
	 */
	private void handleFindPwd(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, NumberFormatException, NullPointerException {

		String userIdStrPwd = request.getParameter("userId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String userRole = request.getParameter("userRole");
		User user = null;

		// 유효성 검사

		if (!ValidationUtil.isValidateName(name)) {
			AlertUtil.backAlert(response, "이름을 다시 입력해주세요.");
			return;
		}

		if (!ValidationUtil.isEmail(email)) {
			AlertUtil.backAlert(response, "이메일을 다시 입력해주세요.");
			return;
		}

		if (!ValidationUtil.isValidateId(userIdStrPwd)) {
			AlertUtil.backAlert(response, "유효한 숫자 아이디를 다시 입력해주세요.");
			return;
		}

		int userId = Integer.parseInt(userIdStrPwd);

		// 학생일 때
		if (userRole.equals("student")) {
			user = userRepository.getStudentByNameAndEmailAndId(name, email, userId);

			// 교수일 때
		} else if (userRole.equals("professor")) {
			user = userRepository.getProfessorByNameAndEmailAndId(name, email, userId);

			// 직원일 때
		} else if (userRole.equals("staff")) {
			user = userRepository.getStaffByNameAndEmailAndId(name, email, userId);
		}

		if (user == null) {
			AlertUtil.backAlert(response, "비밀번호를 찾을 수 없습니다.");
		}

		// 비밀번호 생성
		String password = PasswordUtil.generatePassword();
		String salt = PasswordUtil.getSalt();
		String pwSalt = PasswordUtil.hashPassword(password, salt);

		String tempPwd = pwSalt;

		User updatedPwd = User.builder()
			.id(user.getId())
			.username(user.getUsername())
			.userRole(user.getUserRole())
			.email(user.getEmail())
			.password(tempPwd)
			.build();
		
		userRepository.getUpdatePassword(updatedPwd);
		request.setAttribute("username", updatedPwd.getUsername());
		request.setAttribute("password", password);
		request.getRequestDispatcher("/WEB-INF/views/user/temporaryPassword.jsp").forward(request, response);
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

		// 로그인 안 한 경우
		if (principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/logIn");
			return;
		}

		// 유효성 검사
		if (!ValidationUtil.isValidatePwd(currentPassword) || !ValidationUtil.isValidatePwd(newPassword)
				|| !ValidationUtil.isValidatePwd(confirmPassword)) {
			AlertUtil.backAlert(response, "비밀번호는 6자 이상 20자 사이로 설정해주세요.");
			return;
		}

		// 새 비밀번호와 확인 비밀번호가 일치하지 않는 경우
		if (!newPassword.equals(confirmPassword)) {
			AlertUtil.backAlert(response, "변경할 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
			return;
		}
		
		// 현재 비밀번호가 일치하지 않는 경우
		if (!PasswordUtil.checkPassword(principal.getPassword(), currentPassword)) {
			AlertUtil.backAlert(response, "현재 비밀번호가 일치하지 않습니다.");
			return;
		}

		// 현재 비밀번호와 변경할 비밀번호가 동일할 경우
		if (currentPassword.equals(newPassword)) {
			AlertUtil.backAlert(response, "현재 비밀번호와 변경할 비밀번호가 동일합니다.");
			return;
		}
		String salt = PasswordUtil.getSalt();
		String pwSalt = PasswordUtil.hashPassword(newPassword, salt);
		

		// 비밀번호 변경
		principal.setPassword(pwSalt);
		
		userRepository.getUpdatePassword(principal);
		request.getSession().setAttribute("principal", principal);
		AlertUtil.backAlert(response, "비밀번호가 변경되었습니다.");
	}

	/**
	 * id 찾기
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleFindId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 이름, 이메일
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String userRole = request.getParameter("userRole");
		System.out.println("userRole:" + userRole); // TODO 삭제
		User user = null;

		// 유효성 검사

		if (!ValidationUtil.isValidateName(name)) {
			AlertUtil.backAlert(response, "이름을 다시 입력해주세요.");
			return;
		}

		if (!ValidationUtil.isEmail(email)) {
			AlertUtil.backAlert(response, "이메일을 다시 입력해주세요.");
			return;
		}

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

		if (user == null) {
			AlertUtil.backAlert(response, "아이디를 찾을 수 없습니다.");
			return;
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
	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String idStr = request.getParameter("id");
		String password = request.getParameter("password");
		String rememberId = request.getParameter("rememberId");

		// 유효성 검사(양식 확인)
		// 아이디가 문자형일 때
		if (!ValidationUtil.isValidateId(idStr)) {
			AlertUtil.backAlert(response, "아이디를 다시 입력해주세요.");
			return;
		}

		int id = Integer.parseInt(idStr);

		if (!ValidationUtil.isValidatePwd(password)) {
			AlertUtil.backAlert(response, "비밀번호는 6자 이상 20자 이하로 설정해주세요.");
			return;
		}

		User principal = userRepository.getUserById(id);
		System.out.println("principal:" + principal); // TODO 삭제

		// 현재 아이디가 일치하지 않은 경우
		if (principal == null || principal.getId() != id) {
			AlertUtil.backAlert(response, "현재 아이디가 일치하지 않습니다.");
			return;
		}

		// 현재 비밀번호가 일치하지 않는 경우
		if (!PasswordUtil.checkPassword(principal.getPassword(), password)) {
			AlertUtil.backAlert(response, "현재 비밀번호가 일치하지 않습니다.");
			return;
		}

		// 로그인 성공 시 세션 생성
		HttpSession session = request.getSession();

		// 체크박스 - 체크 상태일 때(쿠키 생성)
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
	}
}
