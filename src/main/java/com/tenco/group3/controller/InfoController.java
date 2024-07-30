package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.Professor;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.UserRepositoryImpl;
import com.tenco.group3.repository.interfaces.UserRepository;
import com.tenco.group3.util.AlertUtil;
import com.tenco.group3.util.PasswordUtil;
import com.tenco.group3.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

		if (principal.getUserRole().equals("student")) {
			Student studentInfo = userRepository.getStudentInfo(principal.getId());
			request.setAttribute("studentInfo", studentInfo);
			request.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(request, response);
		} else if (principal.getUserRole().equals("professor")) {
			Professor professorInfo = userRepository.getProfessorInfo(principal.getId());
			request.setAttribute("professorInfo", professorInfo);
			request.getRequestDispatcher("/WEB-INF/views/user/update.jsp").forward(request, response);
		} else if (principal.getUserRole().equals("staff")) {
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
		Staff staffInfo = userRepository.getStaffInfo(principal.getId());
		request.setAttribute("staffInfo", staffInfo);
		request.getRequestDispatcher("/WEB-INF/views/user/staffInfo.jsp").forward(request, response);
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
		Professor professorInfo = userRepository.getProfessorInfo(principal.getId());
		request.setAttribute("professorInfo", professorInfo);
		request.getRequestDispatcher("/WEB-INF/views/user/professorInfo.jsp").forward(request, response);
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

		// 세션 조회
		User principal = (User) request.getSession().getAttribute("principal");

		// 세션 아이디로 DB 정보 조회
		Student studentInfo = userRepository.getStudentInfo(principal.getId());
		List<Student> studentStat = userRepository.getStudentStat(principal.getId());

		request.setAttribute("studentInfo", studentInfo);
		request.setAttribute("studentStat", studentStat);

		// 4. jsp에 뿌리기
		request.getRequestDispatcher("/WEB-INF/views/user/studentInfo.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getPathInfo();

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
		if (!PasswordUtil.checkPassword(principal.getPassword(), password)) {
			AlertUtil.backAlert(response, "비밀번호가 일치하지 않습니다.");
			return;
		}

		// 유효성 검사
		if (email == null || !ValidationUtil.isEmail(email)) {
			AlertUtil.backAlert(response, "이메일 형식이 아닙니다.");
			return;
		}
		if (address == null || !ValidationUtil.isValidateAddress(address)) {
			AlertUtil.backAlert(response, "잘못된 주소 형식 입니다.");
			return;
		}

		if (tel == null || !ValidationUtil.isValidateTel(tel)) {
			AlertUtil.backAlert(response, "전화번호를 확인해 주세요");
			return;
		}
		tel = ValidationUtil.formatTel(tel);

		if (principal.getUserRole().equals("student")) {
			Student studentUpdate = Student.builder().address(address).email(email).tel(tel).id(principal.getId())
					.build();
			userRepository.getStudentInfoUpdate(studentUpdate);
			response.sendRedirect("/info/student");

		} else if (principal.getUserRole().equals("professor")) {
			Professor professorUpdate = Professor.builder().address(address).email(email).tel(tel).id(principal.getId())
					.build();
			userRepository.getProfessorInfoUpdate(professorUpdate);
			response.sendRedirect("/info/professor");

		} else if (principal.getUserRole().equals("staff")) {
			Staff staffUpdate = Staff.builder().address(address).email(email).tel(tel).id(principal.getId()).build();
			userRepository.getStaffInfoUpdate(staffUpdate);
			response.sendRedirect("/info/staff");
		}

	}
}
