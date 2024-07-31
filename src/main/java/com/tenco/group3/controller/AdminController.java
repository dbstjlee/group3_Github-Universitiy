package com.tenco.group3.controller;

import java.io.IOException;
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
import com.tenco.group3.util.AlertUtil;
import com.tenco.group3.util.ValidationUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DepartmentRepository departmentRepository;
	private CollTuitRepository collTuitRepository;
	private CollegeRepository collegeRepository;
	private SubjectRepository subjectRepository;
	private RoomRepository roomRepository;

	@Override
	public void init() throws ServletException {
		departmentRepository = new DepartmentRepositoryImpl();
		collTuitRepository = new CollTuitRepositoryImpl();
		collegeRepository = new CollegeRepositoryImpl();
		subjectRepository = new SubjectRepositoryImpl();
		roomRepository = new RoomRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();

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

	/**
	 * 과목 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void subjectList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Subject> subjects = subjectRepository.getAllSubject();
		request.setAttribute("subjects", subjects);
		request.getRequestDispatcher("/WEB-INF/views/admin/subject.jsp").forward(request, response);
	}

	/**
	 * 단과대 별 등록금 조회
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
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

		if (!ValidationUtil.isNotOnlyWhitespace(idStr) || !ValidationUtil.isOnlyNumber(idStr)) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
			return;
		}

		int id = Integer.parseInt(idStr);

		int result = subjectRepository.deleteSubject(id);
		if (result > 0) {
			response.sendRedirect("/admin/subject");
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

		if (!ValidationUtil.isNotOnlyWhitespace(idStr) || !ValidationUtil.isOnlyNumber(idStr)
				|| !ValidationUtil.isNotOnlyWhitespace(startTimeStr) || !ValidationUtil.isOnlyNumber(startTimeStr)
				|| !ValidationUtil.isNotOnlyWhitespace(capacityStr) || !ValidationUtil.isOnlyNumber(capacityStr)
				|| !ValidationUtil.isNotOnlyWhitespace(endTimeStr) || !ValidationUtil.isOnlyNumber(endTimeStr)
				|| !ValidationUtil.isNotOnlyWhitespace(roomId) || !ValidationUtil.isNotOnlyWhitespace(name)
				|| !ValidationUtil.isNotOnlyWhitespace(subDay)) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
			return;
		}

		int	id = Integer.parseInt(idStr);
		int	startTime = Integer.parseInt(startTimeStr);
		int	endTime = Integer.parseInt(endTimeStr);
		int	capacity = Integer.parseInt(capacityStr);

		Subject subject = Subject.builder().id(id).name(name).roomId(roomId).subDay(subDay).startTime(startTime)
				.endTime(endTime).capacity(capacity).build();

		int result = subjectRepository.updateSubject(subject);
		if (result > 0) {
			response.sendRedirect("/admin/subject");
		} else {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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

		
		if (!ValidationUtil.isNotOnlyWhitespace(name) || !ValidationUtil.isNotOnlyWhitespace(professorIdStr)
				|| !ValidationUtil.isNotOnlyWhitespace(roomId) || !ValidationUtil.isOnlyNumber(roomId)
				|| !ValidationUtil.isNotOnlyWhitespace(deptIdStr) || !ValidationUtil.isNotOnlyWhitespace(type)
				|| !ValidationUtil.isNotOnlyWhitespace(subYearStr) || !ValidationUtil.isNotOnlyWhitespace(semesterStr)
				|| !ValidationUtil.isNotOnlyWhitespace(subDay) || !ValidationUtil.isNotOnlyWhitespace(startTimeStr)
				|| !ValidationUtil.isNotOnlyWhitespace(endTimeStr) || !ValidationUtil.isNotOnlyWhitespace(gradesStr)
				|| !ValidationUtil.isNotOnlyWhitespace(capacityStr)) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
			return;
		}

		Subject subject = Subject.builder().name(name).professorId(professorId).roomId(roomId).deptId(deptId).type(type)
				.subYear(subYear).semester(semester).subDay(subDay).startTime(startTime).endTime(endTime).grades(grades)
				.capacity(capacity).build();

		int result = subjectRepository.addSubject(subject);
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/subject");
		} else {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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
		String idParam = request.getParameter("id");
		String amountParam = request.getParameter("amount");

//		if (idParam == null || idParam.trim().isEmpty() || amountParam == null || amountParam.trim().isEmpty()) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
		
		if(!ValidationUtil.isNotOnlyWhitespace(idParam) || !ValidationUtil.isNotOnlyWhitespace(amountParam) || !ValidationUtil.isOnlyNumber(amountParam)
				) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
			return;
		}
		
		int id;
		int amount;
		try {
			id = Integer.parseInt(idParam);
			amount = Integer.parseInt(amountParam);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		CollegeTuition collegeTuition = CollegeTuition.builder().college_id(id).amount(amount).build();
		int result = collTuitRepository.updateCollegeTuition(collegeTuition);
		if (result > 0) {
			List<CollegeTuition> collegeTuitions = collTuitRepository.getAllColTuit();
			request.setAttribute("collegeTuitions", collegeTuitions);
			request.getRequestDispatcher("/WEB-INF/views/admin/collegeTuition.jsp").forward(request, response);
		} else {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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
		
		if (!ValidationUtil.isNotOnlyWhitespace(idParam) || !ValidationUtil.isNotOnlyWhitespace(name)) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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

//		if (roomId == null || roomId.trim().isEmpty() || collegeIdParam == null || collegeIdParam.trim().isEmpty()) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
		
		if (!ValidationUtil.isNotOnlyWhitespace(roomId) || !ValidationUtil.isNotOnlyWhitespace(collegeIdParam)
				|| !ValidationUtil.isOnlyNumber(collegeIdParam)) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
		}
		
		int collegeId;
		try {
			collegeId = Integer.parseInt(collegeIdParam);
		} catch (NumberFormatException e) {
			AlertUtil.backAlert(response, "잘못된 요청입니다.");
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

	    if( !ValidationUtil.isNotOnlyWhitespace(departmentName) || !ValidationUtil.isNotOnlyWhitespace(collegeIdStr)) {
	    	AlertUtil.backAlert(response, "잘못된 요청입니다.");
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
	    
	    departmentRepository.addDepartment(department);
	    
	    List<College> colleges = departmentRepository.getAllColleges();
	    
	    request.setAttribute("colleges", colleges);
	    response.sendRedirect(request.getContextPath() + "/admin/department");
	    
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
