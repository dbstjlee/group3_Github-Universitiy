package com.tenco.group3.filter;

import java.io.IOException;
import java.io.PrintWriter;

import com.tenco.group3.model.User;
import com.tenco.group3.repository.EvaluationRepositoryImpl;
import com.tenco.group3.repository.interfaces.EvaluationRepository;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/evaluation")
public class EvaluationFilter extends HttpFilter implements Filter {

	private EvaluationRepository evaluationRepository;

	public void destroy() {
		// 필터 종료 코드
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(); // 기존 세션 가져오기

		if (session != null) {
			Object principalObj = session.getAttribute("principal");

			if (principalObj instanceof User) {
				User principal = (User) principalObj;
				System.out.println("principal : " + principal);

				if (evaluationRepository.isEvaluation(principal.getId())) {
					chain.doFilter(request, response);
				} else {
					sendErrorMessage((HttpServletResponse) response, "이미 평가를 했습니다.");
				}
			} else {
				sendErrorMessage((HttpServletResponse) response, "비정상 접근입니다.");
			}
		} else {
			sendErrorMessage((HttpServletResponse) response, "세션이 존재하지 않습니다.");
		}
	}

	private void sendErrorMessage(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script> alert('" + message + "'); window.close(); </script>");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		evaluationRepository = new EvaluationRepositoryImpl();
	}
}
