package com.tenco.group3.listener;

import java.awt.Font;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.tenco.group3.model.ScheduleState;
import com.tenco.group3.repository.ScheduleStateRepositoryImpl;
import com.tenco.group3.repository.interfaces.ScheduleStateRepository;
import com.tenco.group3.util.SemesterUtil;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

	private ScheduleStateRepository scheduleStateRepository;
	
	public ContextListener() {
		scheduleStateRepository = new ScheduleStateRepositoryImpl();
	}
	
	public void contextInitialized(ServletContextEvent sce) {
		ScheduleState scheduleState = scheduleStateRepository.getAllScheduleStates();
		sce.getServletContext().setAttribute("breakApp", scheduleState.getBreakApp());
		sce.getServletContext().setAttribute("sugang", scheduleState.getSugang());
		sce.getServletContext().setAttribute("tuition", scheduleState.getTuition());
		sce.getServletContext().setAttribute("year", scheduleState.getYear());
		sce.getServletContext().setAttribute("semester", scheduleState.getSemester());
		SemesterUtil.setCurrentYear(scheduleState.getYear());
		SemesterUtil.setCurrentSemester(scheduleState.getSemester());
	}
	
}
