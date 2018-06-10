package com.pojul.test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pojul.fastIM.message.chat.TextChatMessage;
import com.pojul.fastIM.message.chat.TextPicMessage;
import com.pojul.fastIM.message.request.LoginMessage;
import com.pojul.fastIM.message.response.LoginResponse;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.message.MessageHeader;
import com.pojul.objectsocket.message.ResponseMessage;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.socket.RequestTimeOut;
import com.pojul.objectsocket.socket.SocketReceiver;
import com.pojul.objectsocket.socket.SocketRequest;
import com.pojul.objectsocket.utils.Constant;
import com.pojul.objectsocket.utils.LogUtil;

public class TestLoginRequest {

	public static ClientSocket mClientSocket;
	public static boolean run =true;
	static String from = "";
	static Scanner input = new Scanner(System.in);
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

		Constant.STORAGE_TYPE = 0;
		LogUtil.allowD = false;
		
		conn();

	}
	
	static void conn(){
		System.out.println("conn...");
		SocketRequest.getInstance().resuestConn(new SocketRequest.IRequestConn() {
            @Override
            public void onError(String msg) {
            	System.out.println("conn fail");
            }

            @Override
            public void onFinished(ClientSocket clientSocket) {
                if(clientSocket == null || clientSocket.getmSocket() == null){
                	System.out.println("conn fail");
                    return;
                }
                mClientSocket = clientSocket;
                mClientSocket.setRecListener(new RecListener());
                mClientSocket.setmOnStatusChangedListener(new StatusListener());
                
                System.out.println("conn success");
                login(1);
            }
        }, "127.0.0.1", 57142);
	}
	
	static void login(int which){
		System.out.println("login...");
		String[] info = listMaps.get(which);
		from = info[0];
        LoginMessage mLoginMessage = new LoginMessage();
        mLoginMessage.setUserName(info[0]);
        mLoginMessage.setPassWd(info[1]);
        mLoginMessage.setDeviceType("windows");
        SocketRequest.getInstance().resuest(mClientSocket, mLoginMessage, new SocketRequest.IRequest(){

			@Override
			public void onError(String msg) {
				// TODO Auto-generated method stub
				System.out.println("login fail: " + msg);
			}

			@Override
			public void onFinished(ResponseMessage mResponse) {
				// TODO Auto-generated method stub
				LoginResponse loginResponse = (LoginResponse)mResponse;
				if(loginResponse.getCode() == 200) {
					System.out.println(mResponse.getMessage() + ": " + mResponse.toString());
					input();
				}else {
					System.out.println(mResponse.getMessage() + ": " + mResponse.toString());
				}
			}
           
        });
    }
	
	static void input() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String inputStr = "";
				while(run) {
					inputStr = input.nextLine();
					String[] messageTo = inputStr.split(",");
					if(messageTo.length != 3) {
						return;
					}
					SendMessage(messageTo[0], messageTo[1], Integer.parseInt(messageTo[2]));
					System.out.println("                                    "
							+ " me: " + inputStr);
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
			if(message instanceof TextChatMessage) {
				TextChatMessage mTextMessage = (TextChatMessage)message;
				System.out.println("         " + message.getFrom() + ": " + mTextMessage.getText());
			}else if(message instanceof TextPicMessage) {
				TextPicMessage mTextPicMessage = (TextPicMessage)message;
				System.out.println("         " + mTextPicMessage.getFrom() + ": "
						+ mTextPicMessage.getText() + "\n"
						+ "              " + mTextPicMessage.getPic().getFilePath());
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
	
	static class StatusListener implements ClientSocket.OnStatusChangedListener{

		@Override
		public void onConnClosed() {
			// TODO Auto-generated method stub
			exit();
		}
	}
	
	static void exit() {
		// TODO Auto-generated method stub
		RequestTimeOut.getInstance().stopMonitor();
		RequestTimeOut.getInstance().clearRequestQuenesNotify();
		run = false;
		input.close();
		input = null;
	}
	
}
