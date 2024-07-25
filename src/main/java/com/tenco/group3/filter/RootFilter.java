package com.tenco.group3.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

@WebFilter("/*")
public class RootFilter extends HttpFilter implements Filter {

	List<String> ignoreURLs = new ArrayList<>();

	public RootFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ignoreURLs.add("/user/logIn");
		ignoreURLs.add("/favicon.io");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		String path = httpRequest.getRequestURI();
		System.out.println(path);
		if (ignoreURLs.contains(path) || path.startsWith("/resources")) {
			chain.doFilter(request, response);
			return;
		}

		if (session == null ||session.getAttribute("principal") == null) {
			httpResponse.sendRedirect("/user/logIn");
		} else {
			if (path.equals("/")) {
				// TODO 홈 화면에서 필요한 값들 지정해줘야함
				// 1. 유저정보, 2. 공지, 3. 학사일정
				request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(httpRequest, response);
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
	}

}
