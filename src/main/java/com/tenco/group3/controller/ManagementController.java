package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.Professor;
import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.Tuition;
import com.tenco.group3.repository.BreakAppRepositoryImpl;
import com.tenco.group3.repository.ManagementRepositoryImpl;
import com.tenco.group3.repository.StuSchRepositoryImpl;
import com.tenco.group3.repository.StuStatRepositoryImpl;
import com.tenco.group3.repository.StuSubRepositoryImpl;
import com.tenco.group3.repository.TuitionRepositoryImpl;
import com.tenco.group3.repository.interfaces.BreakAppRepository;
import com.tenco.group3.repository.interfaces.ManagementRepository;
import com.tenco.group3.repository.interfaces.StuSchRepository;
import com.tenco.group3.repository.interfaces.StuStatRepository;
import com.tenco.group3.repository.interfaces.StuSubRepository;
import com.tenco.group3.repository.interfaces.TuitionRepository;
import com.tenco.group3.util.AlertUtil;
import com.tenco.group3.util.Define;
import com.tenco.group3.util.SemesterUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/management/*")
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ManagementRepository managementRepository;
	private BreakAppRepository breakAppRepository;
	private StuStatRepository stuStatRepository;
	private StuSubRepository stuSubRepository;
	private StuSchRepository stuSchRepository;
	private TuitionRepository tuitionRepository;

	public ManagementController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		managementRepository = new ManagementRepositoryImpl();
		breakAppRepository = new BreakAppRepositoryImpl();
		stuStatRepository = new StuStatRepositoryImpl();
		stuSubRepository = new StuSubRepositoryImpl();
		stuSchRepository = new StuSchRepositoryImpl();
		tuitionRepository = new TuitionRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
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
			showTuitionPage(request, response);
			break;
		case "/bill":
			handleCreateTuition(request, response);
			break;
		case "/billEnd":
			handleEndTuition(request, response);
			break;
		case "/break":
			showBreakPage(request, response);
			break;
		case "/breakState":
			handleBreakState(request, response, Boolean.parseBoolean(request.getParameter("state")));
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
	 * 등록금 고지서 페이지 호출
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showTuitionPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (managementRepository.getScheduleStat("break_app") == Define.TRUE) {
			AlertUtil.backAlert(response, "휴학 신청 기간에는 등록금 고지서를 보낼 수 없습니다.");
		} else {
			request.getRequestDispatcher("/WEB-INF/views/management/tuition.jsp").forward(request, response);
		}
	}

	/**
	 * 등록금 고지서 발송
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleCreateTuition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 1. 휴학이 끝난 사람을 복학 상태로 변경 (2024 2학기 기준)
		List<BreakApp> breakAppList = breakAppRepository.getBreakAppByApproval();
		List<Integer> studentIdList = SemesterUtil.breakDone(breakAppList);
		stuStatRepository.updateStatusById(studentIdList, "복학");

		// 2. 직전학기 성적 확인하여 장학금 타입 설정 후 학생별 장학금 타입 테이블에 인서트
		List<RankedStudent> rankedStudentList = stuSubRepository.selectRankedStudent();
		stuSchRepository.insertStuSch(rankedStudentList);
		// 3. 재학 중인 사람(최근 학적변동이 입학, 복학인 상태) 에게 등록금 고지서 발송
		List<Tuition> tuitionList = tuitionRepository.getTuitions();
		int rowCount = tuitionRepository.addAllTuitions(tuitionList);
		String msg = rowCount + "명에게 등록금 고지서 발송 완료";
		getServletContext().setAttribute("tuition", true);
		managementRepository.updateSchedule("tuition", true);
		AlertUtil.hrefAlert(response, msg, "/management/tuition");
	}

	/**
	 * 등록금 납부 기한 종료
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleEndTuition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		getServletContext().setAttribute("tuition", false);
		managementRepository.updateSchedule("tuition", false);
		// TODO 등록금을 납부 하지 않은 학생을 제적 (미등록) 으로 학적 변동
		int rowCount = 0;
		String msg = rowCount + "명 제적 처리 완료";
		AlertUtil.hrefAlert(response, msg, "/management/studentList");
	}

	/**
	 * 휴학 처리 페이지 호출
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showBreakPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int isBreak = managementRepository.getScheduleStat("break_app");
		if (isBreak == Define.ERROR) {
			AlertUtil.backAlert(response, "오류 발생");
		} else {
			request.setAttribute("isBreak", isBreak);
			request.getRequestDispatcher("/WEB-INF/views/management/break.jsp").forward(request, response);
		}
	}

	/**
	 * 휴학 기간 상태 변경
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleBreakState(HttpServletRequest request, HttpServletResponse response, boolean state) throws ServletException, IOException {
		if (!state && managementRepository.checkBreakAppDone()) {
			AlertUtil.backAlert(response, "처리되지 않은 휴학 신청이 있습니다.");
		} else {
			managementRepository.updateSchedule("break_app", state);
			getServletContext().setAttribute("break_app", state);
			showBreakPage(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
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
				AlertUtil.hrefAlert(response, "등록 성공", "/management/student");
				;
			} else {
				AlertUtil.backAlert(response, "잘못된 요청입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
		}
	}

	private void handleCreateProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				AlertUtil.hrefAlert(response, "등록 성공", "/management/professor");
			} else {
				AlertUtil.backAlert(response, "잘못된 요청입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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
				AlertUtil.hrefAlert(response, "등록 성공", "/management/staff");
			} else {
				AlertUtil.backAlert(response, "잘못된 요청입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
		}
	}

}
