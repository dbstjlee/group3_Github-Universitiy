package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.tenco.group3.model.Evaluation;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.EvaluationRepositoryImpl;
import com.tenco.group3.repository.interfaces.EvaluationRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		request.setAttribute("subjectId", subjectId);
		request.getRequestDispatcher("/WEB-INF/views/grade/evaluation.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		HttpSession session = request.getSession();
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
		Object principal = session.getAttribute("principal");

		// 디버깅 코드 추가
		if (principal != null) {
			System.out.println("principal class: " + principal.getClass().getName());
			System.out.println("principal content: " + principal.toString());
		} else {
			System.out.println("principal is null");
		}

		if (principal instanceof User) {
			User user = (User) principal;
			int subjectId = Integer.parseInt(request.getParameter("subjectId"));

			int[] answers = new int[7];
			for (int i = 0; i < 7; i++) {
				answers[i] = Integer.parseInt(request.getParameter("answer" + (i + 1)));
			} // 각 문항 답을 배열로 저장
			String improvements = request.getParameter("improvements");

			// 이미 평가를 작성한 경우 처리
			if (evaluationRepository.isEvaluation(user.getId(), subjectId)) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script> alert('이미 평가를 작성했습니다.'); window.close(); </script>");
				return;
			}

			// 평가 작성
			Evaluation evaluation = Evaluation.builder().answer1(answers[0]).answer2(answers[1]).answer3(answers[2])
					.answer4(answers[3]).answer5(answers[4]).answer6(answers[5]).answer7(answers[6])
					.improvments(improvements).studentId(user.getId()).subjectId(subjectId).build();

			evaluationRepository.addEvaluation(evaluation);

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('제출 되었습니다.'); window.close(); </script>");
		} else {
			// principal 객체가 User 타입이 아닐 경우 오류 처리
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('세션 정보가 올바르지 않습니다. 다시 로그인 해주세요.'); window.close(); </script>");
		}
	}

}
