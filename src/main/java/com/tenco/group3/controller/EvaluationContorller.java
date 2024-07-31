package com.tenco.group3.controller;

import java.io.IOException;

import com.tenco.group3.model.Evaluation;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.EvaluationRepositoryImpl;
import com.tenco.group3.repository.interfaces.EvaluationRepository;
import com.tenco.group3.util.AlertUtil;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	private void showEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		request.setAttribute("subjectId", subjectId);
		request.getRequestDispatcher("/WEB-INF/views/grade/evaluation.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	 * 강의 평가 제출 핸들링
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	private void handlerEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("principal");
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));

		int[] answers = new int[7];
		;
		for (int i = 1; i <= 7; i++) {
			// 방어적 코드 - answer 값이 null 값이면 창 닫기
			if (request.getParameter("answer" + i) == null) {
				AlertUtil.closeAlert(response, "올바른 값을 제출하세요.");
				return;
			}
			answers[i - 1] = Integer.parseInt(request.getParameter("answer" + i));
		}

		String improvements = request.getParameter("improvements");

		Evaluation evaluation = Evaluation.builder()
			.answer1(answers[0])
			.answer2(answers[1])
			.answer3(answers[2])
			.answer4(answers[3])
			.answer5(answers[4])
			.answer6(answers[5])
			.answer7(answers[6])
			.improvments(improvements)
			.studentId(user.getId())
			.subjectId(subjectId)
			.build();

		// 이미 평가를 작성한 경우 처리 - 필터링 하는게 더 좋음(오류가 많이 발생해 일단 보류)
		if (evaluationRepository.isEvaluation(user.getId(), subjectId)) {
			AlertUtil.closeAlert(response, "이미 평가를 작성했습니다.");
			return;
		}

		// 평가 작성
		evaluationRepository.addEvaluation(evaluation);

		response.setContentType("text/html;charset=UTF-8");
		AlertUtil.openerHrefAlertClosing(response, "제출 되었습니다.", "/grade/thisSemester");

	}

}
