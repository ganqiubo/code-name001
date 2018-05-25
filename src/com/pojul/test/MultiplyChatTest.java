package com.pojul.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.pojul.fastIM.message.chat.TextChatMessage;
import com.pojul.fastIM.message.login.LoginMessage;
import com.pojul.fastIM.message.response.LoginResponse;
import com.pojul.fastIM.utils.DateUtil;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.message.MessageHeader;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.socket.SocketReceiver;
import com.pojul.objectsocket.utils.LogUtil;

public class MultiplyChatTest {

	public static ClientSocket mClientSocket;
	public static boolean run =true;
	static String from = "";
	public static List<String[]> listMaps = new ArrayList<String[]>(){{
		add(new String[] {"Lucy","123123"});
		add(new String[] {"Tony","123456"});
		add(new String[] {"Jack","000000"});
		add(new String[] {"root","111111"});
		add(new String[] {"陈平","222222"});
		add(new String[] {"萧何","333333"});
		add(new String[] {"刘邦","444444"});
		add(new String[] {"张良","55555"});
		add(new String[] {"韩信","666666"});
	}};
	
	public static void main(String[] args) {
		
		try {
			mClientSocket = new ClientSocket("127.0.0.1", 57142);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mClientSocket.setRecListener(new RecListener());
		
		login(6);
		
	}
	
	static void login(int which) {
		/*String[] info = new String[2];//listMaps.get(which);
		Scanner input = new Scanner(System.in);
		System.out.println("请输入用户名：");
		info[0] = input.nextLine();
		System.out.println("请输入密码：");
		info[1] = input.nextLine();*/
		String[] info = listMaps.get(which);
		from = info[0];
		LoginMessage mLoginMessage = new LoginMessage();
		mLoginMessage.setUserName(info[0]);
		mLoginMessage.setPassWd(info[1]);
		mLoginMessage.setDeviceType("windows");
		mClientSocket.sendData(mLoginMessage);
		
	}
	
	static void input() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Scanner input = new Scanner(System.in);
				String inputStr = "";
				while(run) {
					inputStr = input.nextLine();
					String[] messageTo = inputStr.split(",");
					if(messageTo.length != 3) {
						return;
					}
					SendMessage(messageTo[0], messageTo[1], Integer.parseInt(messageTo[2]));
					/*System.out.println("                                    "
							+ " me: " + inputStr);*/
				}
			}
		}).start();
	}

	static void SendMessage(String message, String to, int type) {
		// TODO Auto-generated method stub
		TextChatMessage mTextMessage = new TextChatMessage();
		mTextMessage.setFrom(from);
		mTextMessage.setTo(to);
		mTextMessage.setText(message);
		mTextMessage.setChatType(type);
		mClientSocket.sendData(mTextMessage);
	}
	
	static class RecListener implements SocketReceiver.ISocketReceiver{

		@Override
		public void onReadHead(MessageHeader header) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String onReadFile(StringFile mStringFile) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onReadEntity(BaseMessage message) {
			// TODO Auto-generated method stub
			if(message instanceof LoginResponse) {
				LoginResponse mLoginResponse = (LoginResponse)message;
				if(200 == mLoginResponse.getCode()) {
					mClientSocket.setChatId(mLoginResponse.getChatId()); 
					input();
				}
				LogUtil.i(getClass().getName(), message.toString());
			}else if(message instanceof TextChatMessage) {
				TextChatMessage mTextMessage = (TextChatMessage)message;
				System.out.println("         " + message.getFrom() + ": " + mTextMessage.getText());
			}else {
				
			}
		}

		@Override
		public void onReadFinish() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			run = false;
		}
		
	}
	
	
}
