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
		response.getWriter().println("<script> alert('" + msg + "'); window.location.href = '" + path + "'; </script>");
	}
}
