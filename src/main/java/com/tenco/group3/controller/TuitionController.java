package com.tenco.group3.controller;

import java.io.IOException;

import com.tenco.group3.model.Tuition;
import com.tenco.group3.repository.TuitionRepositoryImpl;
import com.tenco.group3.repository.interfaces.TuitionRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tuition/*")
public class TuitionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TuitionRepository tuitionRepository;

	@Override
	public void init() throws ServletException {
		tuitionRepository = new TuitionRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO - 테스트 나중에
		String action = request.getPathInfo();
		switch (action) {
		case "/payment":
			showPaymentTuition(request, response);
			break;
		case "/check":
			showListTuition(request, response);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 등록금 내역 조회 페이지 이동
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void showListTuition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.getRequestDispatcher("/WEB-INF/views/tuition/check.jsp").forward(request, response);
	}

	/**
	 * 등록금 납부 고지서 화면 이동
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showPaymentTuition(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Tuition tuition = tuitionRepository.getTuitionByStudentId(2023000003);

//		System.out.println(tuituon.toString());
		request.setAttribute("tuition", tuition);
		request.getRequestDispatcher("/WEB-INF/views/tuition/payment.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
