package com.tenco.group3.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.group3.model.College;
import com.tenco.group3.model.Department;
import com.tenco.group3.model.Room;
import com.tenco.group3.repository.CollegeRepositoryImpl;
import com.tenco.group3.repository.DepartmentRepositoryImpl;
import com.tenco.group3.repository.RoomRepositoryImpl;
import com.tenco.group3.repository.interfaces.CollegeRepository;
import com.tenco.group3.repository.interfaces.DepartmentRepository;
import com.tenco.group3.repository.interfaces.RoomRepository;

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
	
	@Override
	public void init() throws ServletException {
		collegeRepository = new CollegeRepositoryImpl();
		departmentRepository = new DepartmentRepositoryImpl();
		roomRepository = new RoomRepositoryImpl();
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
		default:
			collegeList(request, response);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 강의실 조회
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void roomList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Room> rooms = roomRepository.getAllRoom();
		request.setAttribute("rooms", rooms);
		request.getRequestDispatcher("/WEB-INF/views/admin/room.jsp").forward(request, response);
	}

	/**
	 * 학과 조회
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
		System.out.println("메서드 호출됨");
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
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

	/**
	 * 학과 이름 수정
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
	    Department department = Department.builder()
	            .id(id)
	            .name(name)
	            .build();
	    int result = departmentRepository.updateDepartment(department);
	    if (result > 0) {
	        response.sendRedirect(request.getContextPath() + "/admin/department");
	    } else {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}
	/**
	 * 학과 삭제
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
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void handleAddDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String departmentName = request.getParameter("departmentName");
	    String collegeIdStr = request.getParameter("collegeId");
	    
	    if (departmentName == null || departmentName.trim().isEmpty() || collegeIdStr == null || collegeIdStr.trim().isEmpty()) {
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
	    
	    departmentRepository.addDepartment(department);
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
		College college = College.builder().name(collegeName).build();
		collegeRepository.addCollege(college);
		response.sendRedirect(request.getContextPath() + "/admin/college");
	}

}
