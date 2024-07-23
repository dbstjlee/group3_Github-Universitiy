package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.tenco.group3.model.Evaluation;
import com.tenco.group3.model.Student;
import com.tenco.group3.repository.EvaluationRepositoryImpl;
import com.tenco.group3.repository.interfaces.EvaluationRepository;

@WebServlet("/evaluation")
public class EvaluationContorller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EvaluationRepository evaluationRepository;

	@Override
	public void init() throws ServletException {
		evaluationRepository = new EvaluationRepositoryImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		HttpSession session = request.getSession();
//		if (session == null) {
//			response.sendRedirect(request.getContextPath() + "/user/login");
//		}
		switch (action) {
		case "/evaluation":
			showEvaluation(request, response, session);
			break;
		default:
			break;
		}
	}

	/**
	 * 교수 평가 팝업창 띄우기
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		session.setAttribute("principal", session);
		request.getRequestDispatcher("/WEB-INF/views/grade/evaluation.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		HttpSession session = request.getSession();
//		if (session == null) {
//			response.sendRedirect(request.getContextPath() + "/user/login");
//		}
		switch (action) {
		case "/evaluation":
			handlerEvaluation(request, response, session);
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
	 * @param session
	 * @throws IOException
	 */
	private void handlerEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
//		Student student = (Student) session.getAttribute("principal");
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		int[] answers = new int[7];
		for (int i = 0; i < 7; i++) {
			answers[i] = Integer.parseInt(request.getParameter("answer" + (i + 1)));
		} // 각 문항 답을 배열로 저장
		String improvements = request.getParameter("improvements");

		if (evaluationRepository.isEvaluation(2023000012)) {
			Evaluation evaluation = Evaluation.builder().answer1(answers[0]).answer2(answers[1]).answer3(answers[2])
					.answer4(answers[3]).answer5(answers[4]).answer6(answers[5]).answer7(answers[6])
					.improvments(improvements).studentId(2023000012).subjectId(subjectId).build();
			evaluationRepository.addEvaluation(evaluation);
		} else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('이미 평가를 했습니다.'); history.back() </script>");
		}
	}
}