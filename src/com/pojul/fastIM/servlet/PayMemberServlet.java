package com.pojul.fastIM.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pojul.fastIM.dao.UserDao;
import com.pojul.fastIM.message.other.NotifyPayMemberOk;
import com.pojul.fastIM.socketmanager.ClientSocketManager;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.socket.ClientSocket;

@WebServlet("/PayMember")
public class PayMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PayMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String imsi = request.getParameter("imsi");
		String moneyStr = request.getParameter("money");
		double money = 0;
		Result result = new Result();
		try {
			money = Double.parseDouble(moneyStr);
		}catch(Exception e) {
			result.resultCode = 101;
			String json = new Gson().toJson(result);
			response.getWriter().append(json);
			return;
		}
		if(imsi == null || imsi.isEmpty() || money <= 0) {
			result.resultCode = 102;
			String json = new Gson().toJson(result);
			response.getWriter().append(json);
			return;
		}
		double monthDoubles = money / 2 + 0.01;
		int months = new BigDecimal(("" + monthDoubles)).setScale(0, RoundingMode.HALF_EVEN).intValue();
		if(months <= 0) {
			result.resultCode = 103;
			String json = new Gson().toJson(result);
			response.getWriter().append(json);
			return;
		}
		System.out.println("paymember months: " + months);
		payMember(imsi, months, response);
	}

	private void payMember(String imsi, int months, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		UserDao userDao = new UserDao();
		Result result = new Result();
		String valideDate = userDao.getMemberValidDate(imsi);
		if(valideDate == null) {
			result.resultCode = 104;
			String json = new Gson().toJson(result);
			response.getWriter().append(json);
			return;
		}
		String newValidDate;
		if(DateUtil.isDateOverdue(valideDate)) {
			newValidDate = DateUtil.convertLongToDate((System.currentTimeMillis() + months * 31L * 24L * 60L * 60L * 1000L));
		}else {
			long newValidMilli = DateUtil.convertTimeToLong(valideDate) + months * 31L * 24L * 60L * 60L * 1000L;
			newValidDate = DateUtil.convertLongToDate(newValidMilli);
		}
		long payResult = userDao.updateMemberValidDate(imsi, newValidDate);
		if(payResult > 0) {
			result.resultCode = 200;
			String json = new Gson().toJson(result);
			response.getWriter().append(json);
			
			HashMap<String, ClientSocket> mClientSockets = ClientSocketManager.clientSockets.get(imsi);
			if(mClientSockets != null && mClientSockets.size() > 0) {
				for ( Entry<String, ClientSocket> entity : mClientSockets.entrySet()) {
					ClientSocket mClientSocket = entity.getValue();
					if(mClientSocket != null) {
						NotifyPayMemberOk message = new NotifyPayMemberOk();
						message.setValidDate(newValidDate);
						message.setTo(imsi);
						mClientSocket.sendData(message);
					}
				}
			}
		}else {
			result.resultCode = 105;
			String json = new Gson().toJson(result);
			response.getWriter().append(json);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	class Result {
		public int resultCode;
	}

}
