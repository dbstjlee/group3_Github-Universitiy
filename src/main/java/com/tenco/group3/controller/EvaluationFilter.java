package com.tenco.group3.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.tenco.group3.model.Student;
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

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		Student student = (Student) session.getAttribute("principal");
		if (student == null) {
			if (evaluationRepository.isEvaluation(2023000013)) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).setContentType("text/html;charset=UTF-8");
				PrintWriter out = ((HttpServletResponse) response).getWriter();
				out.println("<script> alert('이미 평가를 했습니다.'); window.close(); </script>");
				return;
			}
		} else {
			((HttpServletResponse) response).setContentType("text/html;charset=UTF-8");
			PrintWriter out = ((HttpServletResponse) response).getWriter();
			out.println("<script> alert('비정상 접근입니다.'); window.close(); </script>");
			return;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		evaluationRepository = new EvaluationRepositoryImpl();
	}

}
