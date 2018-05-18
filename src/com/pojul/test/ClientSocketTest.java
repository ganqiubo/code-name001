package com.pojul.test;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.pojul.fastIM.entity.LoginMessage;
import com.pojul.fastIM.entity.LoginStatus;
import com.pojul.fastIM.entity.LoginoutMessage;
import com.pojul.fastIM.entity.response.LoginResponse;
import com.pojul.objectsocket.message.BaseMessage;
import com.pojul.objectsocket.message.MessageHeader;
import com.pojul.objectsocket.message.MulitMessage;
import com.pojul.objectsocket.message.StringFile;
import com.pojul.objectsocket.message.TextMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.socket.SocketReceiver;
import com.pojul.objectsocket.socket.SocketSender;
import com.pojul.objectsocket.utils.LogUtil;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;

public class ClientSocketTest {

	private static String TAG = "ClientSocketTest";
	
	static HashMap<String, String> localImgs1 = new HashMap<String, String>() {{
		put("D:\\testpic\\1.jpg", "1.jpg");
		put("D:\\siheal_doc\\05结果页.rar", "05结果页.rar");
		put("D:\\siheal_doc\\家庭健康包APP标注切图\\血压计串口控制协议V1.4.pdf", "血压计串口控制协议V1.4.pdf");
		put("D:\\siheal_doc\\20180403TO 李阳 家庭手机APP(1).rar", "20180403TO 李阳 家庭手机APP(1).rar");
	}};
	
	static HashMap<String, String> localImgs2 = new HashMap<String, String>() {{
		put("D:\\testpic\\3.jpg", "3.jpg");
		put("D:\\testpic\\4.jpg", "4.jpg");
		put("C:\\Users\\24439\\Desktop\\o5_build_0422\\app-debug_20180422.apk", "app-debug_20180422.apk");
	}};
	
	static HashMap<String, String> localImgs3 = new HashMap<String, String>() {{
		put("D:\\testpic\\a2.jpg", "a2.jpg");
		put("C:\\Users\\24439\\Desktop\\o5_build_0422\\05A测试20180422.xlsx", "05A测试20180422.xlsx");
	}};
	
	public static Socket socket;
	public static ClientSocket mClientSocket;
	public static int which;
	
	public static void main(String[] args) {
		
		//testSend();
		
		//testLinkHashMap();
		
		//testSendMulti();
		
		try {
			mClientSocket = new ClientSocket("127.0.0.1", 57142);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mClientSocket.setRecListener(new RecListener());
		
		which = 1;
		for(int i = 0; i < 1; i++) {
			login();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
    }  
	
	static void testSendMulti(){
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", 57142);
			ClientSocket cs= new ClientSocket(socket);
			cs.setSenderListener(new MySocketSenderListener());
			int i = 0;
			while (true) {
				MulitMessage bm = new MulitMessage();
				bm.setText("Hello" + i);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startTime = new Date();
				String startTimeSting  = sdf.format(startTime);
				bm.setSendTime(startTimeSting);
				bm.setFrom("Jack");
				bm.setTo("Tony");
				StringFile[] sfs = new StringFile[4];
				int index = 0;
				for (Entry<String, String> paths : localImgs1.entrySet()) {
					sfs[index] = new StringFile(0);
					sfs[index].setFileName(paths.getValue());
					sfs[index].setFileType("unknow");
					sfs[index].setFilePath(paths.getKey());
					bm.setmStringFile1s(sfs);
					index ++;
				}
				cs.sendData(bm);
				
				index = 0;
				bm = new MulitMessage();
				bm.setText("Hello" + i);
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startTime = new Date();
				startTimeSting  = sdf.format(startTime);
				bm.setSendTime(startTimeSting);
				bm.setFrom("Jack");
				bm.setTo("Tony");
				sfs = new StringFile[3];
				for (Entry<String, String> paths : localImgs2.entrySet()) {
					sfs[index] = new StringFile(0);
					sfs[index].setFileName(paths.getValue());
					sfs[index].setFileType("unknow");
					sfs[index].setFilePath(paths.getKey());
					bm.setmStringFile1s(sfs);
					index ++;
				}
				cs.sendData(bm);
				
				Thread.sleep(1 * 60 * 1000);
				
				index = 0;
				bm = new MulitMessage();
				bm.setText("Hello" + i);
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startTime = new Date();
				startTimeSting  = sdf.format(startTime);
				bm.setSendTime(startTimeSting);
				bm.setFrom("Jack");
				bm.setTo("Tony");
				sfs = new StringFile[2];
				for (Entry<String, String> paths : localImgs3.entrySet()) {
					sfs[index] = new StringFile(0);
					sfs[index].setFileName(paths.getValue());
					sfs[index].setFileType("unknow");
					sfs[index].setFilePath(paths.getKey());
					bm.setmStringFile1s(sfs);
					index ++;
				}
				cs.sendData(bm);
				
				Thread.sleep(10 * 60 * 1000);
				
				
				i++;
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println(" :ClientSocketTest IOException------>" + e);
		}
	}
	
	static void testLinkHashMap() {
		
	}
	
	static void testSend() {
		int i = 0;
		while (true) {
			TextMessage bm = new TextMessage();
			bm.setText("Hello" + i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = new Date();
			String startTimeSting  = sdf.format(startTime);
			bm.setSendTime(startTimeSting);
			
			bm.setFrom("Jack");
			bm.setTo("Tony");
			
			mClientSocket.sendData(bm);
			try {
				Thread.sleep(600 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;

		}
	}
	
	static void login() {
		if(which%2 == 1) {
			LoginMessage mLoginMessage = new LoginMessage();
			mLoginMessage.setUserName("Tony");
			mLoginMessage.setPassWd("123456");
			mLoginMessage.setDeviceType("windows");
			mClientSocket.sendData(mLoginMessage);
			
			/*try {
				Thread.sleep(18000);
				TextMessage mTextMessage = new TextMessage();
				mTextMessage.setFrom(mClientSocket.getChatId());
				mTextMessage.setTo("Jack");
				mTextMessage.setText("哈喽。");
				mClientSocket.sendData(mTextMessage);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			/*try {
				Thread.sleep(10000);
				LoginoutMessage mLoginoutMessage = new LoginoutMessage();
				mClientSocket.sendData(mLoginoutMessage);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}else {
			LoginMessage mLoginMessage = new LoginMessage();
			mLoginMessage.setUserName("Jack");
			mLoginMessage.setPassWd("000000");
			mClientSocket.sendData(mLoginMessage);
			
			try {
				Thread.sleep(18000);
				TextMessage mTextMessage = new TextMessage();
				mTextMessage.setFrom(mClientSocket.getChatId());
				mTextMessage.setTo("Tony");
				mTextMessage.setText("你好！");
				mClientSocket.sendData(mTextMessage);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
				}
				LogUtil.i(TAG, message.toString());
			}else if(message instanceof TextMessage) {
				TextMessage mTextMessage = (TextMessage)message;
				LogUtil.i(TAG, "message from " + message.getFrom() + ": " + mTextMessage.getText() );
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
			
		}
		
	}
	
	static class MySocketSenderListener implements SocketSender.ISocketSender{

		@Override
		public void onSendFinish(BaseMessage mBaseMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNullMessage(BaseMessage mBaseMessage) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSendError(Exception e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSendFail(BaseMessage mBaseMessage) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
