package com.tenco.group3.util;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public class AlertUtil {

	public static void backAlert(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script> alert('" + msg + "'); history.back(); </script>");
	}

	public static void hrefAlert(HttpServletResponse response, String msg, String path) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script> alert('" + msg + "');");
		response.getWriter().println(" window.location.href = '" + path + "'; </script>");
	}
	
	public static void hrefConfirm(HttpServletResponse response, String msg, String path) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script> if(confirm('" + msg + "')){");
		response.getWriter().println(" window.location.href = '" + path + "';} else {history.back();} </script>");
	}
}
