package com.tenco.group3.filter;

import java.io.IOException;

import com.tenco.group3.model.User;
import com.tenco.group3.util.AlertUtil;

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

@WebFilter(urlPatterns = {"/management/*", "/admin/* " })
public class ManagementFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		User principal = (User) session.getAttribute("principal");
		if (session == null || session.getAttribute("principal") == null) {
			httpResponse.sendRedirect("/user/logIn");
			return;
		}
		if ("staff".equals(principal.getUserRole())) {
			chain.doFilter(request, response);
		} else {
			AlertUtil.backAlert((HttpServletResponse)response, "비정상적인 접근입니다.");
		}
	}

}
