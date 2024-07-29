package com.tenco.group3.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tenco.group3.model.College;
import com.tenco.group3.model.CollegeTuition;
import com.tenco.group3.model.Department;
import com.tenco.group3.model.Room;
import com.tenco.group3.model.Subject;
import com.tenco.group3.repository.CollTuitRepositoryImpl;
import com.tenco.group3.repository.CollegeRepositoryImpl;
import com.tenco.group3.repository.DepartmentRepositoryImpl;
import com.tenco.group3.repository.RoomRepositoryImpl;
import com.tenco.group3.repository.SubjectRepositoryImpl;
import com.tenco.group3.repository.interfaces.CollTuitRepository;
import com.tenco.group3.repository.interfaces.CollegeRepository;
import com.tenco.group3.repository.interfaces.DepartmentRepository;
import com.tenco.group3.repository.interfaces.RoomRepository;
import com.tenco.group3.repository.interfaces.SubjectRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CollegeRepository collegeRepository;
	private DepartmentRepository departmentRepository;
	private RoomRepository roomRepository;
	private CollTuitRepository collTuitRepository;
	private SubjectRepository subjectRepository;

	@Override
	public void init() throws ServletException {
		collegeRepository = new CollegeRepositoryImpl();
		departmentRepository = new DepartmentRepositoryImpl();
		roomRepository = new RoomRepositoryImpl();
		collTuitRepository = new CollTuitRepositoryImpl();
		subjectRepository = new SubjectRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

//		HttpSession session = request.getSession(false);
//		if(session == null || session.getAttribute("principal") == null ) {
//			response.sendRedirect(request.getContextPath() + "");
//		}

		System.out.println("action : " + action);
		switch (action) {
		case "/college":
			collegeList(request, response);
			break;
		case "/department":
			departmentList(request, response);
			break;
		case "/room":
			roomList(request, response);
			break;
		case "/tuition":
			colTuitList(request, response);
			break;
		case "/subject":
			subjectList(request, response);
			break;
		default:
			collegeList(request, response);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	private void subjectList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Subject> subjects = subjectRepository.getAllSubject();
		request.setAttribute("subjects", subjects);
		request.getRequestDispatcher("/WEB-INF/views/admin/subject.jsp").forward(request, response);
	}

	private void colTuitList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<CollegeTuition> collegeTuitions = collTuitRepository.getAllColTuit();
		request.setAttribute("collegeTuitions", collegeTuitions);
		request.getRequestDispatcher("/WEB-INF/views/admin/collegeTuition.jsp").forward(request, response);
	}

	/**
	 * 강의실 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void roomList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Room> rooms = roomRepository.getAllRoom();
		request.setAttribute("rooms", rooms);
		request.getRequestDispatcher("/WEB-INF/views/admin/room.jsp").forward(request, response);
	}

	/**
	 * 학과 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void departmentList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Department> departments = departmentRepository.getAllDepartment();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/views/admin/department.jsp").forward(request, response);
	}

	/**
	 * 단과대학 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void collegeList(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    List<College> colleges = collegeRepository.getAllCollege();
	    request.setAttribute("colleges", colleges);
	    request.getRequestDispatcher("/WEB-INF/views/admin/college.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action : " + action);
		switch (action) {
		case "/addCollege":
			handleAddCollege(request, response);
			break;
		case "/deleteCollege":
			handleDeleteCollege(request, response);
			break;
		case "/addDepartment":
			handleAddDepartment(request, response);
			break;
		case "/deleteDepartment":
			handleDeleteDepartment(request, response);
			break;
		case "/updateDepartment":
			handleUpdateDepartment(request, response);
			break;
		case "/addRoom":
			handleAddRoom(request, response);
			break;
		case "/deleteRoom":
			handleDeleteRoom(request, response);
			break;
		case "addCollTuit":
			handleAddCollegeTuition(request, response);
			break;
		case "/updateCollTuit":
			handleUpdateCollegeTuition(request, response);
			break;
		case "/deleteCollTuit":
			handleDeleteCollegeTuition(request, response);
			break;
		case "/addSubject":
			handleAddSubject(request, response);
			break;
		case "/updateSubject":
			handleUpdateSubject(request, response);
			break;
		case "/deleteSubject":
			handleDeleteSubject(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 강의 삭제
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleDeleteSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");

		if (idStr == null || idStr.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int result = subjectRepository.deleteSubject(id);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/subject");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 강의 수정
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleUpdateSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idStr = request.getParameter("id");
		String name = request.getParameter("name");
		String roomId = request.getParameter("roomId");
		String subDay = request.getParameter("subDay");
		String startTimeStr = request.getParameter("startTime");
		String endTimeStr = request.getParameter("endTime");
		String capacityStr = request.getParameter("capacity");

		if (idStr == null || idStr.trim().isEmpty() || name == null || name.trim().isEmpty() || roomId == null
				|| roomId.trim().isEmpty() || subDay == null || subDay.trim().isEmpty() || startTimeStr == null
				|| startTimeStr.trim().isEmpty() || endTimeStr == null || endTimeStr.trim().isEmpty()
				|| capacityStr == null || capacityStr.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int id, startTime, endTime, capacity;
		try {
			id = Integer.parseInt(idStr);
			startTime = Integer.parseInt(startTimeStr);
			endTime = Integer.parseInt(endTimeStr);
			capacity = Integer.parseInt(capacityStr);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Subject subject = Subject.builder().id(id).name(name).roomId(roomId).subDay(subDay).startTime(startTime)
				.endTime(endTime).capacity(capacity).build();

		int result = subjectRepository.updateSubject(subject);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/subject");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 강의 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleAddSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String professorIdStr = request.getParameter("professorId");
		String roomId = request.getParameter("roomId");
		String deptIdStr = request.getParameter("deptId");
		String type = request.getParameter("type");
		String subYearStr = request.getParameter("subYear");
		String semesterStr = request.getParameter("semester");
		String subDay = request.getParameter("subDay");
		String startTimeStr = request.getParameter("startTime");
		String endTimeStr = request.getParameter("endTime");
		String gradesStr = request.getParameter("grades");
		String capacityStr = request.getParameter("capacity");

		if (name == null || name.trim().isEmpty() || professorIdStr == null || professorIdStr.trim().isEmpty()
				|| roomId == null || roomId.trim().isEmpty() || deptIdStr == null || deptIdStr.trim().isEmpty()
				|| type == null || type.trim().isEmpty() || subYearStr == null || subYearStr.trim().isEmpty()
				|| semesterStr == null || semesterStr.trim().isEmpty() || subDay == null || subDay.trim().isEmpty()
				|| startTimeStr == null || startTimeStr.trim().isEmpty() || endTimeStr == null
				|| endTimeStr.trim().isEmpty() || gradesStr == null || gradesStr.trim().isEmpty() || capacityStr == null
				|| capacityStr.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int professorId, deptId, subYear, semester, startTime, endTime, grades, capacity;
		try {
			professorId = Integer.parseInt(professorIdStr);
			deptId = Integer.parseInt(deptIdStr);
			subYear = Integer.parseInt(subYearStr);
			semester = Integer.parseInt(semesterStr);
			startTime = Integer.parseInt(startTimeStr);
			endTime = Integer.parseInt(endTimeStr);
			grades = Integer.parseInt(gradesStr);
			capacity = Integer.parseInt(capacityStr);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Subject subject = Subject.builder().name(name).professorId(professorId).roomId(roomId).deptId(deptId).type(type)
				.subYear(subYear).semester(semester).subDay(subDay).startTime(startTime).endTime(endTime).grades(grades)
				.capacity(capacity).build();

		// Subject 추가
		int result = subjectRepository.addSubject(subject);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/subject");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 단과대학별 등록금 삭제
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleDeleteCollegeTuition(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String idStr = request.getParameter("id");

		if (idStr == null || idStr.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int id;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int result = collTuitRepository.deleteCollegeTuition(id);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/tuition");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 단과대학별 등록금 수정
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleUpdateCollegeTuition(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 파라미터 값 가져오기
		String idParam = request.getParameter("id");
		String amountParam = request.getParameter("amount");

		// 파라미터 유효성 검사
		if (idParam == null || idParam.trim().isEmpty() || amountParam == null || amountParam.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
			return;
		}
		int id;
		int amount;
		try {
			id = Integer.parseInt(idParam);
			amount = Integer.parseInt(amountParam);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameter format");
			return;
		}
		CollegeTuition collegeTuition = CollegeTuition.builder().college_id(id).amount(amount).build();
		int result = collTuitRepository.updateCollegeTuition(collegeTuition);
		if (result > 0) {
			List<CollegeTuition> collegeTuitions = collTuitRepository.getAllColTuit();
			request.setAttribute("collegeTuitions", collegeTuitions);
			request.getRequestDispatcher("/WEB-INF/views/admin/collegeTuition.jsp").forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update college tuition");
		}

	}

	/**
	 * 단과대학별 등록금 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleAddCollegeTuition(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String collegeIdStr = request.getParameter("collegeId");
		String amountStr = request.getParameter("amount");

		if (collegeIdStr == null || collegeIdStr.trim().isEmpty() || amountStr == null || amountStr.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int collegeId;
		int amount;
		try {
			collegeId = Integer.parseInt(collegeIdStr);
			amount = Integer.parseInt(amountStr);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		CollegeTuition collegeTuition = CollegeTuition.builder().college_id(collegeId).amount(amount).build();
		int result = collTuitRepository.addCollegeTuition(collegeTuition);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/tuition");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 학과 이름 수정
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleUpdateDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idParam = request.getParameter("id");
		String name = request.getParameter("name");
		if (idParam == null || idParam.trim().isEmpty() || name == null || name.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int id;
		try {
			id = Integer.parseInt(idParam);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Department department = Department.builder().id(id).name(name).build();
		int result = departmentRepository.updateDepartment(department);
		if (result > 0) {
			request.setAttribute("department", department);
			try {
				request.getRequestDispatcher("/WEB-INF/views/admin/department.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 학과 삭제
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleDeleteDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idParam = request.getParameter("id");

		if (idParam == null || idParam.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int departmentId;
		try {
			departmentId = Integer.parseInt(idParam);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Department department = departmentRepository.getDepartmentById(departmentId);
		if (department == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		int result = departmentRepository.deleteDepartment(departmentId);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/department");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 강의실 삭제
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleDeleteRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String roomId = request.getParameter("id");
		if (roomId == null || roomId.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int result = roomRepository.deleteRoom(roomId);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/room");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 강의실 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleAddRoom(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String roomId = request.getParameter("roomId");
		String collegeIdParam = request.getParameter("collegeId");

		if (roomId == null || roomId.trim().isEmpty() || collegeIdParam == null || collegeIdParam.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int collegeId;
		try {
			collegeId = Integer.parseInt(collegeIdParam);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Room room = new Room(roomId, collegeId);
		roomRepository.addRoom(room);
		response.sendRedirect(request.getContextPath() + "/admin/room");
	}

	/**
	 * 학과 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleAddDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String departmentName = request.getParameter("departmentName");
	    String collegeIdStr = request.getParameter("collegeId");

	    if (departmentName == null || departmentName.trim().isEmpty() || collegeIdStr == null
	            || collegeIdStr.trim().isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	        return;
	    }
	    
	    int collegeId;
	    try {
	        collegeId = Integer.parseInt(collegeIdStr);
	    } catch (NumberFormatException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	        return;
	    }
	    
	    Department department = new Department();
	    department.setName(departmentName);
	    department.setCollegeId(collegeId);
	    
	    // 학과 추가
	    departmentRepository.addDepartment(department);
	    
	    // 모든 학부를 가져옴
	    List<College> colleges = departmentRepository.getAllColleges();
	    
	    // request에 데이터 설정
	    request.setAttribute("colleges", colleges);
	    
	    // JSP 페이지로 포워딩
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/department.jsp");
	    dispatcher.forward(request, response);
	}


	/**
	 * 단과대학 삭제
	 * 
	 * @param request
	 * @param response
	 */
	private void handleDeleteCollege(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idParam = request.getParameter("id");

		if (idParam == null || idParam.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		int collegeId;
		try {
			collegeId = Integer.parseInt(idParam);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		College college = collegeRepository.getCollegeById(collegeId);
		if (college == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		int result = collegeRepository.deleteCollege(collegeId);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/college");
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 단과대학 등록
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleAddCollege(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String collegeName = request.getParameter("collegeName");

	    if (collegeName == null || collegeName.trim().isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	        return;
	    }

	    int initialTuitionAmount = 0; // 초기 등록금

	    College college = College.builder().name(collegeName).build();

	    int result = collegeRepository.addCollege(college, initialTuitionAmount);

	    if (result > 0) {
	        response.sendRedirect(request.getContextPath() + "/admin/college");
	    } else {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}

	}
