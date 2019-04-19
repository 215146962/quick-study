package com.example.demo.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.module.BaseMessage;
import com.example.demo.module.loginData.LoginDataModel;
import com.example.demo.pojo.User;
import com.example.demo.service.ILoginService;
import com.example.demo.sessionManager.MySessionContext;
import com.example.demo.util.JsonUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	MySessionContext myc= MySessionContext.getInstance();
	
	@Autowired
	private ILoginService loginService;
	
	/**
	 * 已将所有请求都加入了username、password和sessionId，直接request.getParameter("username")获取
	 */
	@RequestMapping("/loginRequest")
	public String loginRequest(HttpServletRequest request,HttpServletResponse response) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String user_type=request.getParameter("user_type");
		LoginDataModel model=loginService.loginRequest(username, password, user_type);
		if (model.loginDataBody.isLogin==true) {
			HttpSession session=request.getSession();
			session.setAttribute("username", model.loginDataBody.username);
			myc.addSession(session);
			model.loginDataBody.sessionId=session.getId();
		}
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	/**
	 * 提交注册表单
	 */
	@RequestMapping("/postRegister")
	public String postRegister(HttpServletRequest request,HttpServletResponse response) {
		//这里懒得把这么长的参数传到service层中，所以直接建好user对象再传
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String type=request.getParameter("type");
		String sex=request.getParameter("sex");
		String chatId=request.getParameter("chatid");
		User user=new User();
		user.setChatid(chatId);
		user.setCreatetime(new Date());
		user.setFreeze(0);
		user.setLogo("default_user_image.png");
		user.setPassword(password);
		user.setPoint(0);
		user.setSex(sex);
		user.setSignature("");
		user.setType(type);
		user.setUsername(username);
		BaseMessage model=loginService.postRegister(user);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
}
