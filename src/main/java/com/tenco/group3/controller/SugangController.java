package com.tenco.group3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.tenco.group3.repository.SugangRepositoryImpl;
import com.tenco.group3.repository.interfaces.SugangRepository;

@WebServlet("/sugang/*")
public class SugangController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SugangRepository sugangRepository;

	@Override
	public void init() throws ServletException {
		sugangRepository = new SugangRepositoryImpl;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
