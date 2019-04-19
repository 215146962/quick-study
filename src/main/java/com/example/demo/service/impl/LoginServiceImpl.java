package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.module.BaseMessage;
import com.example.demo.module.loginData.LoginDataBody;
import com.example.demo.module.loginData.LoginDataModel;
import com.example.demo.pojo.User;
import com.example.demo.service.ILoginService;


@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public LoginDataModel loginRequest(String username,String password,String user_type) {
		// TODO Auto-generated method stub
		LoginDataModel model=new LoginDataModel();
		
		if (user_type!=null && "user".equals(user_type)) {
			User user=userMapper.selectByPrimaryKey(username);
			if (user==null) {
				model.loginDataBody=new LoginDataBody();
				model.loginDataBody.isLogin=false;
				model.loginDataBody.reason="用户名不存在";
				model.ecode="0";
				return model;
			}else if (!user.getPassword().equals(password)) {
				model.loginDataBody=new LoginDataBody();
				model.loginDataBody.isLogin=false;
				model.loginDataBody.reason="密码错误";
				model.ecode="0";
				return model;
			}else {
				model.loginDataBody=new LoginDataBody();
				model.loginDataBody.isLogin=true;
				model.loginDataBody.username=user.getUsername();
				model.loginDataBody.password=user.getPassword();
				model.loginDataBody.user_type="user";
				model.loginDataBody.reason="登录成功";
				model.loginDataBody.point=user.getPoint();
				model.loginDataBody.signature=user.getSignature();
				model.loginDataBody.logo=AddressValue.LOGO_PATH+user.getLogo();
				model.loginDataBody.chatId=user.getChatid();
				model.loginDataBody.sex=user.getSex();
				model.ecode="0";
				return model;
			}
			
		}else if (user_type!=null && "manager".equals(user_type)) {
			
		}else {
			model.loginDataBody=new LoginDataBody();
			model.loginDataBody.isLogin=false;
			model.loginDataBody.reason="未知用户类型";
			model.ecode="0";
		}
		return model;
	}

	@Override
	public BaseMessage postRegister(User user) {
		BaseMessage model=new BaseMessage();
		model.ecode="0";
		//首先判断用户名是否已存在
		User user2=userMapper.selectByPrimaryKey(user.getUsername());
		if (user2!=null) {
			model.emsg="注册失败，用户名已存在";
			return model;
		}
		userMapper.insert(user);
		model.emsg="注册成功";
		return model;
	}
	
}
