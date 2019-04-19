package com.example.demo.service;

import com.example.demo.module.BaseMessage;
import com.example.demo.module.loginData.LoginDataModel;
import com.example.demo.pojo.User;

public interface ILoginService {
	public LoginDataModel loginRequest(String username,String password,String user_type);
	public BaseMessage postRegister(User user);
}
