package com.tenco.group3.listener;

import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.repository.ScheduleStateRepositoryImpl;
import com.tenco.group3.repository.interfaces.ScheduleStateRepository;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

	private ScheduleStateRepository scheduleStateRepository;
	
	public void contextInitialized(ServletContextEvent sce) {
		scheduleStateRepository = new ScheduleStateRepositoryImpl();
		ScheduleState scheduleState = scheduleStateRepository.getAllScheduleStates();
		sce.getServletContext().setAttribute("breakApp", scheduleState.isBreakApp());
		sce.getServletContext().setAttribute("sugang", scheduleState.isSugang());
		sce.getServletContext().setAttribute("tuition", scheduleState.isTuition());
		sce.getServletContext().setAttribute("year", scheduleState.getYear());
		sce.getServletContext().setAttribute("semester", scheduleState.getSemester());
	}

}
