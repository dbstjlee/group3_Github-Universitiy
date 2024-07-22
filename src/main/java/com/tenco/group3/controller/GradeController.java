package com.tenco.group3.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/grade/*")
public class GradeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GradeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action) {
		case "/thisSemester":
			showThisSemester(request, response);
			break;
		case "/evaluation":
			showEvaluation(request, response);
			break;
		case "/semester":
			showSemester(request, response);
			break;
		case "/total":
			showTotal(request, response);
			break;
		default:
			break;
		}

	}

	/**
	 * 전체 누계 성적 페이지로 이동
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showTotal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/grade/total.jsp").forward(request, response);

	}

	/**
	 * 학기별 성적 조회 페이지 이동
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showSemester(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/grade/semester.jsp").forward(request, response);

	}

	/**
	 * 교수 평가 팝업창 띄우기
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showEvaluation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/grade/evaluation.jsp").forward(request, response);
	}

	/**
	 * 이번 학기 성적 조회 페이지로 이동
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showThisSemester(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/grade/thisSemester.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		// TODO - 학생 정보에서 받기
		switch (action) {
		case "/semester":
			handlerGetRecodeBySemester(request, response);
			break;
		case "/evaluation":
			handlerGetEvaluation(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * 교수 평가 제출 핸들링
	 * 
	 * @param request
	 * @param response
	 */
	private void handlerGetEvaluation(HttpServletRequest request, HttpServletResponse response) {
		String subjectId = request.getParameter("subjectId");
		String[] answers = new String[7];
		for (int i = 0; i < 7; i++) {
			answers[i] = request.getParameter("answer" + (i + 1));
		}// 각 문항 답을 배열로 저장
		String improvements = request.getParameter("improvements"); // text 필드 값 저장
//		for (String string : answers) {
//			System.out.println("answer : "+string);
//		}
		// TODO - 강의 평가 여부 확인 후 안했을 경우 교수평가 insert
	}

	/**
	 * 학기별 성적 조회 핸들링
	 * 
	 * @param request
	 * @param response
	 */
	private void handlerGetRecodeBySemester(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type.equals("all")) {
			// TODO - 전체 타입 성적 조회
		} else {
			// TODO - 교양 or 전공 타입 성적 조회
		}
	}

}
