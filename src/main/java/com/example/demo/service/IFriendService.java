package com.example.demo.service;

import com.example.demo.module.BaseMessage;
import com.example.demo.module.friendlist.FriendListRecommandModel;
import com.example.demo.module.personinformation.PersonalInformationModel;
import com.example.demo.module.requestfriendshiplist.RequestFriendshipListModel;
import com.example.demo.module.requestfriendshiprespones.RequestFriendshipResponseModel;

public interface IFriendService {
	public FriendListRecommandModel requestFriendsList(String username);
	public PersonalInformationModel requestFriendInformation(String username,String searchUsername);
	public RequestFriendshipResponseModel requestFriendship(String username,String friendname);
	public RequestFriendshipListModel requestFriendshipList(String username);
	public BaseMessage friendshipAgreeOrNot(String username,String friendname,boolean isAgree);
}
