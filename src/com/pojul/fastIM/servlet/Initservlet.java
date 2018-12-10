package com.pojul.fastIM.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.pojul.fastIM.socketmanager.ServerSocketManager;
import com.pojul.objectsocket.utils.LogUtil;

/**
 * Servlet implementation class Initservlet
 */
@WebServlet("/Initservlet")
public class Initservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Initservlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }
    
    public void init() throws ServletException {
    	ServerSocketManager.getInstance().startServerSocket();
     }

}
