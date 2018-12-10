package com.pojul.fastIM.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pojul.fastIM.dao.DownLoadsDao;

/**
 * Servlet implementation class DownLoadApkServlet
 */
@WebServlet("/DownLoadApkServlet")
public class DownLoadApkServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		new DownLoadsDao().downloadsAdd();
		response.sendRedirect("http://47.93.31.206:8080/resources/app/detail/footstep_signed1.0.apk");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
