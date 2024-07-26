package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.Professor;
import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.model.Schedule;
import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.Tuition;
import com.tenco.group3.repository.BreakAppRepositoryImpl;
import com.tenco.group3.repository.ManagementRepositoryImpl;
import com.tenco.group3.repository.ScheduleStateRepositoryImpl;
import com.tenco.group3.repository.StuSchRepositoryImpl;
import com.tenco.group3.repository.StuStatRepositoryImpl;
import com.tenco.group3.repository.StuSubRepositoryImpl;
import com.tenco.group3.repository.TuitionRepositoryImpl;
import com.tenco.group3.repository.interfaces.BreakAppRepository;
import com.tenco.group3.repository.interfaces.ManagementRepository;
import com.tenco.group3.repository.interfaces.ScheduleStateRepository;
import com.tenco.group3.repository.interfaces.StuSchRepository;
import com.tenco.group3.repository.interfaces.StuStatRepository;
import com.tenco.group3.repository.interfaces.StuSubRepository;
import com.tenco.group3.repository.interfaces.TuitionRepository;
import com.tenco.group3.util.AlertUtil;
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
	private ScheduleStateRepository scheduleStateRepository;

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
		scheduleStateRepository = new ScheduleStateRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();

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
			handleBreakState(request, response, Integer.parseInt(request.getParameter("state")));
			break;
		case "/new-semester":
			handleNewSemester(request, response);
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

		String deptId = request.getParameter("deptId");
		String studentId = request.getParameter("studentId");
		List<Student> studentList = null;
		int totalStudents = 0;
		if (deptId == null && studentId == null) {
			studentList = managementRepository.getAllStudents(pageSize, offset);
			// 전체 학생 수 조회
			totalStudents = managementRepository.getTotalStudentCount();
		} else {
			studentList = managementRepository.getAllStudents(studentId, deptId, pageSize, offset);
			totalStudents = managementRepository.getTotalStudentCount(studentId, deptId);
			request.setAttribute("deptId", deptId);
			request.setAttribute("studentId", studentId);
		}

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

		String deptId = request.getParameter("deptId");
		String studentId = request.getParameter("professorId");
		List<Professor> professorList = null;
		int totalProfessors = 0;
		if (deptId == null && studentId == null) {
			professorList = managementRepository.getAllProfessors(pageSize, offset);
			// 전체 학생 수 조회
			totalProfessors = managementRepository.getTotalProfessorCount();
		} else {
			professorList = managementRepository.getAllProfessors(studentId, deptId, pageSize, offset);
			totalProfessors = managementRepository.getTotalProfessorCount(studentId, deptId);
		}

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
		if ((int) getServletContext().getAttribute("breakApp") != ScheduleState.END) {
			AlertUtil.backAlert(response, "휴학 신청 기간이 끝나야 등록금 고지서 발송이 가능합니다.");
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

		// 3. 재학 중인 사람(최근 학적변동이 입학, 복학인 상태) 에게 등록금 고지서 발송
		List<Tuition> tuitionList = tuitionRepository.getTuitions();
		int rowCount = tuitionRepository.addAllTuitions(tuitionList);
		String msg = rowCount + "명에게 등록금 고지서 발송 완료";
		getServletContext().setAttribute("tuition", ScheduleState.TRUE);
		scheduleStateRepository.updateSchedule("tuition", ScheduleState.TRUE);
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
		getServletContext().setAttribute("tuition", ScheduleState.END);
		scheduleStateRepository.updateSchedule("tuition", ScheduleState.END);
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
		int isBreak = (int) getServletContext().getAttribute("breakApp");
		request.setAttribute("isBreak", isBreak);
		request.getRequestDispatcher("/WEB-INF/views/management/break.jsp").forward(request, response);
	}

	/**
	 * 휴학 기간 상태 변경
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleBreakState(HttpServletRequest request, HttpServletResponse response, int state) throws ServletException, IOException {
		if (state == 1 && !managementRepository.checkBreakAppDone()) {
			AlertUtil.backAlert(response, "처리되지 않은 휴학 신청이 있습니다.");
		} else {
			scheduleStateRepository.updateSchedule("break_app", state);
			getServletContext().setAttribute("breakApp", state);
			showBreakPage(request, response);
		}
	}

	/**
	 * 새학기 적용
	 * (새학기 적용을 누르는 시점은 모든 수업이 끝나고 다음 학기 학사일정을 진행하기 직전)
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleNewSemester(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int breakAppState = (int) getServletContext().getAttribute("breakApp");
		int sugangState = (int) getServletContext().getAttribute("sugang");
		int tuitionState = (int) getServletContext().getAttribute("tuition");
		if (breakAppState == ScheduleState.END && sugangState == ScheduleState.END && tuitionState == ScheduleState.END) {
			// 직전학기 성적 확인하여 장학금 타입 설정 후 학생별 장학금 타입 테이블에 인서트
			List<RankedStudent> rankedStudentList = stuSubRepository.selectRankedStudent();
			stuSchRepository.insertStuSch(rankedStudentList);
			// 학사일정 상태 테이블에 다음 학기 추가
			scheduleStateRepository.addSchedule();
			// 변수 업데이트
			getServletContext().setAttribute("breakApp", ScheduleState.FALSE);
			getServletContext().setAttribute("sugang", ScheduleState.FALSE);
			getServletContext().setAttribute("tuition", ScheduleState.FALSE);
			getServletContext().setAttribute("year", SemesterUtil.getAfterYear());
			getServletContext().setAttribute("semester", SemesterUtil.getAfterSemester());
			SemesterUtil.setCurrentYear(SemesterUtil.getAfterYear());
			SemesterUtil.setCurrentSemester(SemesterUtil.getAfterSemester());
			SemesterUtil.setAfterYear(0);
			SemesterUtil.setAfterSemester(0);
			// TODO 입학, 복학 상태인 모든 학생 학년, 학기 상승 (4학년 2학기 학생은 졸업)
			List<Student> studentList =  stuStatRepository.getCurrentGrade();
		} else {
			AlertUtil.backAlert(response, "해당 학기의 모든 학사일정이 완료되어야 합니다.");
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
