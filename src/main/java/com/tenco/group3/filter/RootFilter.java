package com.tenco.group3.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tenco.group3.model.Notice;
import com.tenco.group3.model.Schedule;
import com.tenco.group3.model.User;
import com.tenco.group3.repository.NoticeRepositoryImpl;
import com.tenco.group3.repository.ScheduleRepositoryImpl;
import com.tenco.group3.repository.UserRepositoryImpl;
import com.tenco.group3.repository.interfaces.NoticeRepository;
import com.tenco.group3.repository.interfaces.ScheduleRepository;
import com.tenco.group3.repository.interfaces.UserRepository;

import jakarta.annotation.Priority;
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

	private NoticeRepository noticeRepository;
	private ScheduleRepository scheduleRepository;
	private UserRepository userRepository;
	
	List<String> ignoreURLs = new ArrayList<>();

	public RootFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ignoreURLs.add("/user/logIn");
		ignoreURLs.add("/favicon.io");
		
		noticeRepository = new NoticeRepositoryImpl();
		scheduleRepository = new ScheduleRepositoryImpl();
		userRepository = new UserRepositoryImpl();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		String path = httpRequest.getRequestURI();
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
				
				// 1. 유저
				
				// 학생
//				studentId = Integer.parseInt(request);
//				student = userRepository.getStudentInfoMain(studentId);
				
				
				
				// 2. 공지
				int pageSize = 6; 
				int offset = 1;

				List<Notice> noticeList =  noticeRepository.getAllNotice(pageSize, offset);
				request.setAttribute("noticeList", noticeList);
				
				// 3. 학사 일정
				List<Schedule> scheduleList = scheduleRepository.getAllSchedule(pageSize);
				request.setAttribute("scheduleList", scheduleList);
				
				request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(httpRequest, response);
				
			} else {
				chain.doFilter(request, response);
				return;
			}
		}
	}


}
