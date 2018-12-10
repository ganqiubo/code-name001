package com.pojul.fastIM.requestprocessor;

import java.util.List;

import com.pojul.fastIM.dao.ConversationDao;
import com.pojul.fastIM.entity.Conversation;
import com.pojul.fastIM.message.request.GetConversionInfoRequest;
import com.pojul.fastIM.message.response.GetConversionInfoResponse;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;

public class GetConversionInfoProcessor implements RequestProcessor{

	@Override
	public void process(RequestMessage request, ClientSocket clientSocket) {
		// TODO Auto-generated method stub
		GetConversionInfoRequest mRequest = (GetConversionInfoRequest)request;
		List<Conversation> conversations = new ConversationDao()
				.getConversationInfo(mRequest.getChatRoomType(), mRequest.getConversionFrom(), mRequest.getOwner());
		GetConversionInfoResponse response = new GetConversionInfoResponse();
		response.setMessageUid(request.getMessageUid());
		if(conversations == null) {
			response.setCode(100);
			response.setMessage("系统错误");
		}else if(conversations.size() <= 0) {
			response.setCode(201);
			response.setMessage("未查询到数据");
		}else {
			response.setCode(200);
			response.setMessage("sucesses");
			response.setConversation(conversations.get(0));
		}
		clientSocket.sendData(response);
		
	}

	
	
}
