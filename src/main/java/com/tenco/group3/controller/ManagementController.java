package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.BreakApp;
import com.tenco.group3.model.Professor;
import com.tenco.group3.model.RankedStudent;
import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.model.Staff;
import com.tenco.group3.model.Student;
import com.tenco.group3.model.Subject;
import com.tenco.group3.model.Sugang;
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

	private ScheduleStateRepository scheduleStateRepository;
	private ManagementRepository managementRepository;
	private BreakAppRepository breakAppRepository;
	private StuStatRepository stuStatRepository;
	private TuitionRepository tuitionRepository;
	private StuSubRepository stuSubRepository;
	private StuSchRepository stuSchRepository;

	public ManagementController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		scheduleStateRepository = new ScheduleStateRepositoryImpl();
		managementRepository = new ManagementRepositoryImpl();
		breakAppRepository = new BreakAppRepositoryImpl();
		tuitionRepository = new TuitionRepositoryImpl();
		stuStatRepository = new StuStatRepositoryImpl();
		stuSubRepository = new StuSubRepositoryImpl();
		stuSchRepository = new StuSchRepositoryImpl();
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
		case "/breakDetail":
			showBreakDetailPage(request, response);
			break;
		case "/breakState":
			handleBreakState(request, response, Integer.parseInt(request.getParameter("state")));
			break;
		case "/sugang":
			handleSugangState(request, response);
			break;
		case "/new-semester":
			checkNewSemester(request, response);
			break;
		case "/new-semester-confirm":
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
		if ((int) getServletContext().getAttribute("sugang") != ScheduleState.END) {
			AlertUtil.backAlert(response, "수강 신청 기간이 끝나야 등록금 고지서 발송이 가능합니다.");
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
		if (isBreak == ScheduleState.TRUE) {
			List<BreakApp> breakAppList = breakAppRepository.getAllBreakAppInProgress();
			request.setAttribute("breakAppList", breakAppList);
		}
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
			return;
		} else {
			scheduleStateRepository.updateSchedule("break_app", state);
			getServletContext().setAttribute("breakApp", state);
			showBreakPage(request, response);
		}
	}

	/**
	 * 휴학 상세 보기 페이지
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showBreakDetailPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BreakApp breakApp = breakAppRepository.getBreakAppDetail(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("breakApp", breakApp);
		request.getRequestDispatcher("/WEB-INF/views/management/breakDetail.jsp").forward(request, response);
	}

	/**
	 * 새학기 적용을 실행할 지 다시 물어봄 (돌이킬수 없음)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkNewSemester(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AlertUtil.hrefConfirm(response, "새학기를 적용 하시겠습니까 결정은 돌이킬 수 없습니다.", "/management/new-semester-confirm");
	}

	/**
	 * 새학기 적용 (새학기 적용을 누르는 시점은 모든 수업이 끝나고 다음 학기 학사일정을 진행하기 직전)
	 * 
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
			// 휴학이 끝난 사람을 복학 상태로 변경
			List<BreakApp> breakAppList = breakAppRepository.getBreakAppByApproval();
			List<Integer> studentIdList = SemesterUtil.breakDone(breakAppList);
			stuStatRepository.updateStatusById(studentIdList, "복학");
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
			List<Student> studentList = stuStatRepository.getCurrentGrade();
			List<Student> updatedList = new ArrayList<>();
			List<Integer> graduatedList = new ArrayList<>();
			for (Student student : studentList) {
				student = SemesterUtil.updateStudent(student);
				if (student.getGrade() == 5) {
					graduatedList.add(student.getId());
				} else {
					updatedList.add(student);
				}
			}
			managementRepository.updateGradeAndSemester(updatedList);
			stuStatRepository.updateStatusById(graduatedList, "졸업");
			String msg = "새학기 적용이 완료 되었습니다. " + SemesterUtil.getCurrentYear() + "년도 " + SemesterUtil.getCurrentSemester() + "학기가 되었습니다.";
			AlertUtil.hrefAlert(response, msg, "/management/studentList");
		} else {
			AlertUtil.backAlert(response, "해당 학기의 모든 학사일정이 완료되어야 합니다.");
		}
	}

	/**
	 * 수강 신청 상태 변경과 동시에 데이터 처리
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleSugangState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((int) getServletContext().getAttribute("breakApp") != ScheduleState.END) {
			AlertUtil.backAlert(response, "휴학 신청 기간이 끝나야 수강 신청기간을 진행할 수 있습니다.");
			return;
		}
		String stateStr = request.getParameter("state");
		int sugang = (int) getServletContext().getAttribute("sugang");
		// 받아온 수강 신청기간에 대한 상태를 반영
		if (stateStr != null) {
			sugang = Integer.parseInt(stateStr);
			getServletContext().setAttribute("sugang", sugang);
		}

		// 수강 신청기간 시작 --> 예비 수강 신청 내역 처리
		if (sugang == ScheduleState.TRUE) {

			/**
			 * 예비 수강 신청 내역에서 정원이 초과 되지 않은 신청 처리
			 */
			// 정원이 초과되지 않은 과목 리스트 받아옴
			List<Subject> subjectList = stuSubRepository.getAllSubjectSatisfied();
			// 과목 리스트를 이용해서 예비 수강 신청 테이블로 부터 학생id 과목id 학점 받아옴
			List<Sugang> sugangList = stuSubRepository.getAllPreBySubject(subjectList);
			// 예비 수강 신청 내역에서 삭제
			stuSubRepository.deletePreConfirmSubject(subjectList);
			// stu_sub_tb에 insert
			stuSubRepository.addSugang(sugangList);

			/**
			 * 예비 수강 신청 내역에서 정원이 초과된 신청 처리
			 */
			// 정원 초과된 과목 학생 수 0명으로
			stuSubRepository.updateSubjectOver();

		} else if (sugang == ScheduleState.END) {
			// 수강 신청 기간 종료
			// 예비 수강 신청 리스트 비우기
			stuSubRepository.deleteAllPre();
			// 강의 디테일 테이블에 insert
			List<Sugang> sugangList = stuSubRepository.getAllSugang();
			stuSubRepository.addStuSubDetail(sugangList);
		}
		request.setAttribute("sugang", sugang);
		request.getRequestDispatcher("/WEB-INF/views/management/sugang.jsp").forward(request, response);

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
		case "/break":
			handleBreakState(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * 학사관리 학생 등록 학생 테이블, 유저 테이블, 학적 상태 테이블 동시 등록
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

	private void handleBreakState(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int breakId = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		breakAppRepository.updateBreakAppStatus(breakId, status);
		if ("승인".equals(status)) {
			List<Integer> studentList = new ArrayList<>();
			studentList.add(Integer.parseInt(request.getParameter("studentId")));
			stuStatRepository.updateStatusById(studentList, "휴학", type);
		}
		response.sendRedirect("/management/break");
	}

}
