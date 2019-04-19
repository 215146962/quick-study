package com.example.demo.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.ReturnRowsClause;
import com.example.demo.module.BaseMessage;
import com.example.demo.module.friendlist.FriendListRecommandModel;
import com.example.demo.module.lessonData.LessonDataModel;
import com.example.demo.module.lessonlist.LessonListRecommandModel;
import com.example.demo.module.personinformation.PersonalInformationModel;
import com.example.demo.module.questionlist.QuestionDianZanBody;
import com.example.demo.module.questionlist.RequestQuestionListModel;
import com.example.demo.module.requestfriendshiplist.RequestFriendshipListModel;
import com.example.demo.module.requestfriendshiprespones.RequestFriendshipResponseModel;
import com.example.demo.pojo.RequestFriendship;
import com.example.demo.service.IFriendService;
import com.example.demo.service.ILessonService;
import com.example.demo.service.IUserService;
import com.example.demo.sessionManager.MySessionContext;
import com.example.demo.util.JsonUtil;

import net.sf.json.JSONObject;


@RestController
@RequestMapping("/student")
public class StudentController {
	
	MySessionContext myc= MySessionContext.getInstance();
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILessonService lessonService;
	
	@Autowired
	private IFriendService friendService;
	
	//提问请求
	@RequestMapping("/askQuestion")
	public String askQuestion(HttpServletRequest request,HttpServletResponse response) {
		int lessonId=Integer.parseInt(request.getParameter("lessonId"));
		String question_content=request.getParameter("question_content");
		String username=(String) request.getSession().getAttribute("username");
		BaseMessage model=lessonService.askQuestion(lessonId, username, question_content);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	//question加载下一页请求
	@RequestMapping("/questionNextPage")
	public String questionNextPage(HttpServletRequest request,HttpServletResponse response) {
		int lessonId=Integer.parseInt(request.getParameter("lessonId"));
		String username=(String) request.getSession().getAttribute("username");
		int currentPage=Integer.parseInt(request.getParameter("currentPage"));
		RequestQuestionListModel model=lessonService.questionNextPage(lessonId, currentPage, username);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	//question点赞请求
	@RequestMapping("/questionDianZan")
	public String questionDianZan(HttpServletRequest request,HttpServletResponse response) {
		int questionId=Integer.parseInt(request.getParameter("questionId"));
		String username=(String) request.getSession().getAttribute("username");
		QuestionDianZanBody model=lessonService.questionDianZan(questionId, username);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//请求提问列表
	@RequestMapping("/requestQuestionList")
	public String requestQuestionList(HttpServletRequest request,HttpServletResponse response) {
		int lessonId=Integer.parseInt(request.getParameter("lessonId"));
		String username=(String) request.getSession().getAttribute("username");
		RequestQuestionListModel model=lessonService.requestQuestionList(lessonId,1,username);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//是否同意好友请求
	@RequestMapping("/friendshipAgreeOrNot")
	public String friendshipAgreeOrNot(HttpServletRequest request,HttpServletResponse response) {
		String username=(String) request.getSession().getAttribute("username");
		String ISAGREE=request.getParameter("isAgree");
		Boolean isAgree;
		if ("agree".equals(ISAGREE)) {
			isAgree=true;
		}else {
			isAgree=false;
		}
		String friendname=request.getParameter("friendname");
		BaseMessage model=friendService.friendshipAgreeOrNot(username, friendname, isAgree);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	//好友请求列表
	@RequestMapping("/requestFriendshipList")
	public String requestFriendshipList(HttpServletRequest request,HttpServletResponse response) {
		String username=(String) request.getSession().getAttribute("username");
		RequestFriendshipListModel model=friendService.requestFriendshipList(username);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//添加好友请求
	@RequestMapping("/requestFriendship")
	public String RequestFriendship(HttpServletRequest request,HttpServletResponse response) {
		String username=(String) request.getSession().getAttribute("username");
		String friendname=request.getParameter("friendname");
		RequestFriendshipResponseModel model=friendService.requestFriendship(username, friendname);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//搜索用户个人信息
	@RequestMapping("/requestFriendInformation")
	public String requestFriendInformation(HttpServletRequest request,HttpServletResponse response) {
		String username=(String) request.getSession().getAttribute("username");
		String searchUsername=request.getParameter("searchUsername");
		PersonalInformationModel model=friendService.requestFriendInformation(username,searchUsername);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//好友列表请求
	@RequestMapping("/requestFriendsList")
	public String requestFriendsList(HttpServletRequest request,HttpServletResponse response) {
		String username=(String) request.getSession().getAttribute("username");
		FriendListRecommandModel model=friendService.requestFriendsList(username);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//发送点赞请求
	@RequestMapping("/requestDianZan")
	public String requestDianZan(HttpServletRequest request,HttpServletResponse response) {
		int lessonId=Integer.parseInt(request.getParameter("lessonId"));
		String username=(String) request.getSession().getAttribute("username");
		LessonDataModel model=lessonService.requestDianZan(lessonId, username);
		//System.out.println("++++++"+model.lessonData.isDianzan+model.lessonData.dianzan);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	//发送收藏请求
	@RequestMapping("/requestCollection")
	public String requestCollection(HttpServletRequest request,HttpServletResponse response) {
		int lessonId=Integer.parseInt(request.getParameter("lessonId"));
		String username=(String) request.getSession().getAttribute("username");
		LessonDataModel model=lessonService.requestCollection(lessonId, username);
		//System.out.println("++++++"+model.lessonData.isCollection+model.lessonData.collection);
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	/**
	 * 获取课程的详细信息,用于lessionActivity
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getLessonData")
	public String getLessonData(HttpServletRequest request,HttpServletResponse response) {
		//System.out.println(request.getSession().getAttribute("username").toString());
		LessonDataModel model= lessonService.getLessonData(Integer.parseInt(request.getParameter("lessonId")),request.getSession().getAttribute("username").toString());
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	/**
	 * 这里说明一下，传给前端的json最好都是基本数据类型，如果是对象，那么在前端的json可能无法转回，比如Date类型
	 * 获取课程列表（第一次打开StudyFragment的请求）
	 */
	@RequestMapping("/getStudyListView")
	public String getStudyListView(HttpServletRequest request,HttpServletResponse response){
		//System.out.println(request.getSession().getId());
		//System.out.println(request.getHeader("Cookie"));
		//System.out.println(request.getSession().getAttribute("username"));
		LessonListRecommandModel model= lessonService.getStudyListView();
		//转成JSONObject对象
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject.toString();
	}
	
	//课程搜索请求
	@RequestMapping("/getStudySearch")
	public String getStudySearch(HttpServletRequest request,HttpServletResponse response) {
		LessonListRecommandModel model=lessonService.getStudySearch(request.getParameter("condition"),1);
		//转成JSONObject对象
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//课程下拉加载
	@RequestMapping("/loadNextPage")
	public String loadNextPage(HttpServletRequest request,HttpServletResponse response) {
		LessonListRecommandModel model=lessonService.loadNextPage(request.getParameter("condition"),Integer.parseInt(request.getParameter("currentPage")));
		//转成JSONObject对象
		String jsonObject=JsonUtil.toJSONString(model);
		return jsonObject;
	}
	
	//获取当前时间，返回Date类型
	public static Date getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date time = sdf.parse(sdf.format(new Date()));
			return time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*--------------------------------以下是测试的请求-----------------------------------*/
	/**
	 * 注意request和response都是用HttpServletRequest和HttpServletResponse
	 * 要用request.getParameter("name")这种方法才能获取到参数
	 */
	@RequestMapping("/getStudyListViewTest")
	public String getStudyListViewTest(HttpServletRequest request,HttpServletResponse response){
		System.out.println(request.getParameter("name"));
		return "接收到请求啦，哈哈哈";
	}
	
	//插入作者及课程信息
//	@RequestMapping("/initDataTest")
//	public String initDataTest() {
//		User user1=new User();
//		user1.setCreatetime(getCurrentTime());
//		user1.setLogo("http://img3.duitang.com/uploads/item/201407/01/20140701222607_AnKfj.thumb.224_0.jpeg");
//		user1.setPassword("123456");
//		user1.setType("teacher");
//		user1.setUsername("用户1");
//		user1.setFreeze(0);
//		user1.setPoint(0);
//		
//		
//		User user2=new User();
//		user2.setCreatetime(getCurrentTime());
//		user2.setLogo("http://img0.imgtn.bdimg.com/it/u=3266845821,3017593921&fm=21&gp=0.jpg");
//		user2.setPassword("123456");
//		user2.setType("teacher");
//		user2.setUsername("用户2");
//		user2.setFreeze(0);
//		user2.setPoint(0);
//		userService.insertUser(user1);
//		userService.insertUser(user2);
//		
//		
//		Lesson lesson1=new Lesson();
//		lesson1.setAuthor(user1.getUsername());
//		lesson1.setCreatetime(getCurrentTime());
//		lesson1.setIntroduce("来这里看看啦，这里是介绍啊啊啊啊啊啊");
//		lesson1.setTitle("标题一");
//		lesson1.setVideoUrl(null);
//		lesson1.setCollectNum(0);
//		lesson1.setZanNum(0);
//		ArrayList<String> url1=new ArrayList();
//		url1.add("http://img.mukewang.com/549bda090001c53e06000338-590-330.jpg");
//		url1.add("http://img.mukewang.com/5707604300018d0406000338-590-330.jpg");
//		url1.add("http://articles.csdn.net/uploads/allimg/150617/6_150617172820_1.png");
//		url1.add("http://f1.diyitui.com/b3/e1/db/5f/24/ea/d8/59/1e/ea/28/04/b3/57/d6/6f.jpg");
//		url1.add("http://upload1.techweb.com.cn/s/320/2015/0527/1432714922459.jpg");
//		lessonService.insertLesson(lesson1, url1);
//		
//		Lesson lesson2=new Lesson();
//		lesson2.setAuthor(user1.getUsername());
//		lesson2.setCreatetime(getCurrentTime());
//		lesson2.setIntroduce("来这里看看啦，这里是介绍啊啊啊啊啊啊2.0");
//		lesson2.setTitle("标题二");
//		lesson2.setVideoUrl(null);
//		lesson2.setCollectNum(0);
//		lesson2.setZanNum(0);
//		ArrayList<String> url2=new ArrayList();
//		url2.add("http://it.enorth.com.cn/pic2014/002/000/092/00200009288_502cc21c.png");
//		lessonService.insertLesson(lesson2, url2);
//		
//		return "接收到请求了，看看数据库有没有插入";
//	}
	
}
