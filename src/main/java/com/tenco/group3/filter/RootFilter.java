package com.tenco.group3.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class RootFilter extends HttpFilter implements Filter {

	public RootFilter() {
		super();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		String path = httpRequest.getRequestURI();
		if (session.getAttribute("principal") == null && !path.equals("/user/logIn")) {
			httpResponse.sendRedirect("/user/logIn");
		} else {
			if (path.equals("/")) {
				// TODO 홈 화면에서 필요한 값들 지정해줘야함
				// 1. 유저정보, 2. 공지, 3. 학사일정
				request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(httpRequest, response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

}
