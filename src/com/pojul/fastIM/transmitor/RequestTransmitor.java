package com.pojul.fastIM.transmitor;

import java.util.HashMap;

import com.pojul.fastIM.requestprocessor.*;
import com.pojul.objectsocket.message.RequestMessage;
import com.pojul.objectsocket.socket.ClientSocket;
import com.pojul.objectsocket.utils.LogUtil;

public class RequestTransmitor {

	private static RequestTransmitor mRequestTransmitor;

	public static RequestTransmitor getInstance() {
		if (mRequestTransmitor == null) {
			synchronized (RequestTransmitor.class) {
				if (mRequestTransmitor == null) {
					mRequestTransmitor = new RequestTransmitor();
				}
			}
		}
		return mRequestTransmitor;
	}
	
	public void transmit(RequestMessage request, ClientSocket clientSocket){
		 Class<?> clazz;
		try {
			clazz = Class.forName(urlClasses.get(request.getRequestUrl()));
			((RequestProcessor)clazz.newInstance()).process(request, clientSocket);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			LogUtil.i(getClass().getName(), "no RequestProcessor found: " + e.getMessage());
			LogUtil.dStackTrace(e);
		}
	}
	
	public static HashMap<String, String> urlClasses = new HashMap<String, String>(){{
		put("GetFriends", GetFriendsProcessor.class.getName());
		put("GetConversionInfo", GetConversionInfoProcessor.class.getName());
		put("HistoryChatReq", HistoryChatProcessor.class.getName());
		put("UploadPicReq", UploadPicProcessor.class.getName());
		put("UploadPicRecordReq", UploadPicRecordProcessor.class.getName());
		put("CommunityMessageReq", CommunityMessageProcessor.class.getName());
		put("HistoryCommunMessReq", HistoryCommunProcessor.class.getName());
		put("CommunThumbReq", CommunThumbProcessor.class.getName());
		put("ReportReasonReq", ReportReasonProcessor.class.getName());
		put("ReportReq", ReportProcessor.class.getName());
		put("GetCommunRoomByMessReq", GetCommunRoomByMessProcessor.class.getName());
		put("GetReplysReq", GetReplysProcessor.class.getName());
		put("ReplyMessReq", ReplyMessReqProcessor.class.getName());
		put("CloseTagMessReq", CloseTagMessProcessor.class.getName());
		put("CheckTagEffictiveReq", CheckTagEffictiveProcessor.class.getName());
		put("MoreSubReplyReq", MoreSubReplyProcessor.class.getName());
		put("LoginByToken", LoginByTokenProcessor.class.getName());
		put("GetTagMessByUidReq", GetTagMessByUidProcessor.class.getName());
		put("GetTagMessLabelsReq", GetTagMessLabelsProcessor.class.getName());
		put("GetTopMessReq", GetTopMessProcessor.class.getName());
		put("GetTopMessMultiReq", GetTopMessMultiProcessor.class.getName());
		put("UpdateUserPhotoReq", UpdateUserPhotoProcessor.class.getName());
		put("UpdateAutographReq", UpdateAutographProcessor.class.getName());
		put("UpdateUserInfoReq", UpdateUserInfoProcessor.class.getName());
		put("LikeUserReq", LikeUserProcessor.class.getName());
		put("LikeFollowInfoReq", LikeFollowInfoProcessor.class.getName());
		put("FollowUserReq", FollowUserProcessor.class.getName());
		put("UserUploadPicReq", UserUploadPicProcessor.class.getName());
		put("LikeUploadPicReq", LikeUploadPicProcessor.class.getName());
		put("CollectUploadPicReq", CollectUploadPicProcessor.class.getName());
		put("ThumbupUploadPicReq", ThumbupUploadPicProcessor.class.getName());
		put("GetUserInfoReq", GetUserInfoProcessor.class.getName());
		put("GetTagMessByUserReq", GetTagMessByUserProcessor.class.getName());
		put("LikedUsersReq", LikedUsersProcessor.class.getName());
		put("BeLikedUsersReq", BeLikedUsersProcessor.class.getName());
		put("FollowedUsersReq", FollowedUsersProcessor.class.getName());
		put("BeFollowedUsersReq", BeFollowedUsersProcessor.class.getName());
		put("PicFilterLabelReq", PicFilterLabelProcessor.class.getName());
		put("GetPicsReq", GetPicsProcessor.class.getName());
		put("ThirdPicLikesCountReq", ThirdPicLikesCountProcessor.class.getName());
		put("GetTagMessNearByReq", GetTagMessNearByProcessor.class.getName());
		put("UpdateLocReq", UpdateLocProcessor.class.getName());
		put("GetNearByPeopleReq", GetNearByPeopleProcessor.class.getName());
		put("GetLikedPicsReq", GetLikedPicsProcessor.class.getName());
		put("GetCollectedPicsReq", GetCollectedPicsProcessor.class.getName());
		put("FriendReq", FriendReqProcessor.class.getName());
		put("GetFriendReqs", GetFriendReqsProcessor.class.getName());
		put("AcceptFriendReq", AcceptFriendProcessor.class.getName());
		put("ChatLegalReq", ChatLegalProcessor.class.getName());
		put("CloseGreetChatReq", CloseGreetChatProcessor.class.getName());
		put("DeleteFriendReq", DeleteFriendProcessor.class.getName());
		put("GetUsersByNickReq", GetUsersByNickProcessor.class.getName());
		put("GetRecomdMessReq", GetRecomdMessProcessor.class.getName());
		put("GetNearbyUserFilterReq", GetNearbyUserFilterProcessor.class.getName());
		put("UpdateNUFilterReq", UpdateNUFilterProcessor.class.getName());
		put("GetUsersByNameReq", GetUsersByNameProcessor.class.getName());
		put("GetRecomdUserReq", GetRecomdUserProcessor.class.getName());
		put("SuggestReq", SuggestProcessor.class.getName());
		put("RegisterReq", RegisterProcessor.class.getName());
		put("UpdateNickReq", UpdateNickProcessor.class.getName());
		put("ExperienceReq", ExperienceProcessor.class.getName());
		put("GetNewVersionReq", GetNewVersionProcessor.class.getName());
		put("MakeQRCodeReq", MakeQRCodeProcessor.class.getName());
		put("ClaimCommunReq", ClaimCommunProcessor.class.getName());
	}};
	
}
