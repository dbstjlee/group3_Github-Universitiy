package com.tenco.group3.util;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public class AlertUtil {

	public static void errorAlert(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script> alert('" + msg + "'); history.back(); </script>");
	}
}
