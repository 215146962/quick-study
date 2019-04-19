package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.RequestFriendshipMapper;
import com.example.demo.mapper.UserFriendshipMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.module.BaseMessage;
import com.example.demo.module.friendlist.FriendListRecommandModel;
import com.example.demo.module.friendlist.FriendRecommandBody;
import com.example.demo.module.personinformation.PersonalInformationModel;
import com.example.demo.module.requestfriendshiplist.FriendshipRequestBody;
import com.example.demo.module.requestfriendshiplist.RequestFriendshipListModel;
import com.example.demo.module.requestfriendshiprespones.RequestFriendshipResponseModel;
import com.example.demo.pojo.RequestFriendship;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserFriendship;
import com.example.demo.service.IFriendService;

@Service
public class FriendServiceImpl implements IFriendService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserFriendshipMapper userFriendshipMapper;
	@Autowired
	RequestFriendshipMapper requestFriendshipMapper;
	
	
	
	//获取好友列表
	@Override
	public FriendListRecommandModel requestFriendsList(String username) {
		FriendListRecommandModel model=new FriendListRecommandModel();
		model.list=new ArrayList<FriendRecommandBody>();
		List<User> users=userFriendshipMapper.selectByUsername(username);
		if (users==null || users.isEmpty()) {
			model.ecode="0";
			//如果没有好友，就返回一个空的FriendListRecommandModel
			return model;
		}else {
			for (User user:users) {
				if (user!=null) {
					FriendRecommandBody body=new FriendRecommandBody();
					body.logo=AddressValue.LOGO_PATH+user.getLogo();
					body.signature=user.getSignature();
					body.type=user.getType();
					body.username=user.getUsername();
					body.chatId=user.getChatid();
					body.sex=user.getSex();
					model.list.add(body);
				}
			}
			
			model.ecode="0";
			return model;
		}
		
	}

	/**
	 *获取用户个人信息和是否是好友状态
	 */
	@Override
	public PersonalInformationModel requestFriendInformation(String username, String searchUsername) {
		PersonalInformationModel model=new PersonalInformationModel();
		model.ecode="0";
		User user=userMapper.selectByPrimaryKey(searchUsername);
		//如果该用户不存在，就返回不存在
		if (user==null || "".equals(user.getUsername())) {
			model.isExist=false;
			return model;
		}
		else {
			model.isExist=true;
		}
		//如果用户存在就先查看是否是好友关系
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("username", username);
		map.put("searchUsername", searchUsername);
		Integer isFriend=userFriendshipMapper.selectByUsernameAndFriendname(map);
		if (isFriend==0) {
			model.isFriend=false;
		}else {
			model.isFriend=true;
		}
		model.sex=user.getSex();
		model.sLogo=AddressValue.LOGO_PATH+user.getLogo();
		model.sSignature=user.getSignature();
		model.sType=user.getType();
		model.sUsername=user.getUsername();
		return model;
	}

	/**
	 * 处理申请好友请求
	 */
	@Override
	public RequestFriendshipResponseModel requestFriendship(String username, String friendname) {
		RequestFriendshipResponseModel model=new RequestFriendshipResponseModel();
		model.ecode="0";
		if (username.equals(friendname)) {
			//如果输入的好友请求对象是自己，则返回“不能和自己添加好友”
			model.isAddfriendship=false;
			model.emsg="不能和自己添加好友";
			return model;
		}
		//判断是否是好友
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("username", username);
		map.put("friendname", friendname);
		map.put("searchUsername", friendname);//在UserFriendshipMapper中查找条件是username和searchUsername
		Integer isFriend=userFriendshipMapper.selectByUsernameAndFriendname(map);
		if (isFriend==1) {
			model.isAddfriendship=false;
			model.emsg="你与该用户已经是好友";
			return model;
		}
		//判断是否已经发送过好友请求
		Integer isRequest=requestFriendshipMapper.selectByUsernameAndFriendname(map);
		if (isRequest==1) {
			model.isAddfriendship=false;
			model.emsg="你已发送过好友请求";
			return model;
		}
		//判断对方是否给你发送过好友请求，如果发送过，直接变好友
		map.put("username", friendname);
		map.put("friendname", username);
		Integer isBeRequest=requestFriendshipMapper.selectByUsernameAndFriendname(map);
		if (isBeRequest==1) {
			//在好友请求中删除对方给该用户的请求，并在好友列表中添加双方的好友关系，把对方的信息返回给前端
			requestFriendshipMapper.deleteByUsernameAndFriendname(map);
			UserFriendship userFriendship1=new UserFriendship();
			UserFriendship userFriendship2=new UserFriendship();
			userFriendship1.setUsername(username);
			userFriendship1.setFriendname(friendname);
			userFriendship2.setUsername(friendname);
			userFriendship2.setFriendname(username);
			userFriendshipMapper.insert(userFriendship1);
			userFriendshipMapper.insert(userFriendship2);
			//查找对方的信息
			User user=userMapper.selectByPrimaryKey(friendname);
			if (user!=null) {
				model.chatId=user.getChatid();
				model.isAddfriendship=true;
				model.emsg="成功添加好友";
				model.logo=AddressValue.LOGO_PATH+user.getLogo();
				model.sex=user.getSex();
				model.signature=user.getSignature();
				model.type=user.getType();
				model.username=user.getUsername();
			}
			return model;
		}
		//直接发送好友请求
		RequestFriendship requestFriendship=new RequestFriendship();
		requestFriendship.setUsername(username);
		requestFriendship.setFriendname(friendname);
		requestFriendshipMapper.insert(requestFriendship);
		model.isAddfriendship=false;
		model.emsg="好友请求已发送";
		return model;
	}

	//好友请求列表
	@Override
	public RequestFriendshipListModel requestFriendshipList(String username) {
		RequestFriendshipListModel model=new RequestFriendshipListModel();
		model.ecode="0";
		model.list=new ArrayList<FriendshipRequestBody>();
		List<User> users=requestFriendshipMapper.selectUserByFriendname(username);
		if (users!=null || users.size()>0) {
			for (User user:users) {
				FriendshipRequestBody body=new FriendshipRequestBody();
				body.logo=AddressValue.LOGO_PATH+user.getLogo();
				body.type=user.getType();
				body.username=user.getUsername();
				model.list.add(body);
			}
		}
		return model;
	}

	/**
	 * 好友请求同意或不同意
	 */
	@Override
	public BaseMessage friendshipAgreeOrNot(String username, String friendname, boolean isAgree) {
		BaseMessage model=new BaseMessage();
		model.ecode="0";
		if (isAgree) {
			UserFriendship friendship1=new UserFriendship();
			UserFriendship friendship2=new UserFriendship();
			friendship1.setUsername(username);
			friendship1.setFriendname(friendname);
			friendship2.setUsername(friendname);
			friendship2.setFriendname(username);
			userFriendshipMapper.insert(friendship1);
			userFriendshipMapper.insert(friendship2);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("username", friendname);
			map.put("friendname", username);
			//System.out.println(friendname);
			requestFriendshipMapper.deleteByUsernameAndFriendname(map);
			model.emsg="添加好友成功";
		}else {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("username", friendname);
			map.put("friendname", username);
			System.out.println(friendname);
			requestFriendshipMapper.deleteByUsernameAndFriendname(map);
			model.emsg="已拒绝好友申请";
		}
		return model;
	}
	
}
