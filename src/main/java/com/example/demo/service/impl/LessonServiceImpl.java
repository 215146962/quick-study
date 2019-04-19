package com.example.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.collections.functors.AndPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.LessonImgMapper;
import com.example.demo.mapper.LessonMapper;
import com.example.demo.mapper.LessonZanMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.QuestionZanMapper;
import com.example.demo.mapper.UserCollectionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.module.BaseMessage;
import com.example.demo.module.lessonData.LessonDataBody;
import com.example.demo.module.lessonData.LessonDataModel;
import com.example.demo.module.lessonlist.LessonListRecommandModel;
import com.example.demo.module.lessonlist.LessonRecommandBody;
import com.example.demo.module.questionlist.QuestionBody;
import com.example.demo.module.questionlist.QuestionDianZanBody;
import com.example.demo.module.questionlist.RequestQuestionListModel;
import com.example.demo.pojo.Lesson;
import com.example.demo.pojo.LessonImg;
import com.example.demo.pojo.LessonZan;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.QuestionZan;
import com.example.demo.pojo.User;
import com.example.demo.pojo.UserCollection;
import com.example.demo.service.ILessonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class LessonServiceImpl implements ILessonService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	LessonMapper lessonMapper;
	@Autowired
	LessonImgMapper lessonImgMapper;
	@Autowired
	UserCollectionMapper userCollectionMapper;
	@Autowired
	LessonZanMapper lessonZanMapper;
	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	QuestionZanMapper questionZanMapper;
	
	//设置每一页的大小
	private static final int PAGE_SIZE=3;
	
	//提问请求
	@Override
	public BaseMessage askQuestion(int lessonId, String username, String question_content) {
		BaseMessage model=new BaseMessage();
		model.ecode="0";
		if (question_content==null || "".equals(question_content.trim())) {
			//如果提问内容为空或者是空字符串
			model.emsg="提问不能为空哦";
		}else {
			//如果有内容，插入question表中
			Question question=new Question();
			question.setCreatetime(new Date());
			question.setLessonId(lessonId);
			question.setQuestionContent(question_content);
			question.setQuestionUsername(username);
			question.setZanNum(0);
			questionMapper.insert(question);
			model.emsg="提问成功，请等待老师解答。";
		}
		return model;
	}
	//question加载下一页
	@Override
	public RequestQuestionListModel questionNextPage(int lessonId, int currentPage, String username) {
		RequestQuestionListModel model=new RequestQuestionListModel();
		model.list=new ArrayList<QuestionBody>();
		model.ecode="0";
		int nextPage=currentPage+1;
		PageHelper.startPage(nextPage,PAGE_SIZE);
		PageInfo<Question> pageInfo=new PageInfo<Question>(questionMapper.selectBylessonId(lessonId));
		//这里通过判断当前页数是否小于等于总页数来推断是否存在当前页
		if (pageInfo.getPages()>=nextPage) {
			//存在
			List<Question> questions=pageInfo.getList();
			User user=lessonMapper.getAuthorBylessonId(lessonId);
			for (Question question:questions) {
				if (question.getAnswerContent()==null || question.getAnswerContent().trim().equals("")) {
					//如果老师没有回答,就跳过
					continue;
				}else {
					//获得提问者的头像和用户名
					User user2=userMapper.selectByPrimaryKey(question.getQuestionUsername());
					QuestionBody body=new QuestionBody();
					body.answer_content=question.getAnswerContent();
					body.answer_username=user.getUsername();
					body.question_content=question.getQuestionContent();
					body.question_logo=AddressValue.LOGO_PATH+user2.getLogo();
					body.question_username=user2.getUsername();
					body.dianzan_num=question.getZanNum();
					body.question_id=question.getQuestionId();
					body.question_createtime=initCreateData2(question.getCreatetime());
					//查询用户是否有点赞
					Map map=new HashMap<String, Object>();
					map.put("username", username);
					map.put("questionId", question.getQuestionId());
					Integer isDianZan=questionZanMapper.getZanNum(map);
					if (isDianZan==0) {
						//如果没有点赞
						body.isDianzan=false;
					}else {
						//如果有点赞
						body.isDianzan=true;
					}
					model.list.add(body);
				}
			}
		}else {
			//不存在
			
		}
		return model;
	}
	//question点赞
	@Override
	public QuestionDianZanBody questionDianZan(int questionId, String username) {
		QuestionDianZanBody model=new QuestionDianZanBody();
		model.ecode="0";
		//查询用户是否有点赞
		Map map=new HashMap<String, Object>();
		map.put("username", username);
		map.put("questionId", questionId);
		Integer isDianZan=questionZanMapper.getZanNum(map);
		if (isDianZan==0) {
			//没有点赞，添加点赞记录
			QuestionZan questionZan=new QuestionZan();
			questionZan.setQuestionId(questionId);
			questionZan.setUsername(username);
			questionZanMapper.insert(questionZan);
			//点赞数加1
			questionMapper.addZan(questionId);
			//返回点赞数
			int zan_num=questionMapper.getZanNum(questionId);
			model.isDianZan=true;
			model.zan_num=zan_num;
		}else {
			//有点赞，删除点赞记录
			QuestionZan questionZan=new QuestionZan();
			questionZan.setQuestionId(questionId);
			questionZan.setUsername(username);
			questionZanMapper.delete(questionZan);
			//点赞数减1
			questionMapper.reduceZan(questionId);
			//返回点赞数
			int zan_num=questionMapper.getZanNum(questionId);
			model.isDianZan=false;
			model.zan_num=zan_num;
		}
		return model;
	}
	//请求提问列表
	@Override
	public RequestQuestionListModel requestQuestionList(int lessonId,int currentPage,String username) {
		RequestQuestionListModel model=new RequestQuestionListModel();
		model.list=new ArrayList<>();
		model.ecode="0";
		
		//分页查找，查找第一页
		PageHelper.startPage(currentPage,PAGE_SIZE);
		PageInfo<Question> pageInfo=new PageInfo<Question>(questionMapper.selectBylessonId(lessonId));
		List<Question> questions=pageInfo.getList();
		//获得作者的用户名
		User user=lessonMapper.getAuthorBylessonId(lessonId);
		for (Question question:questions) {
			if (question.getAnswerContent()==null || question.getAnswerContent().trim().equals("")) {
				//如果老师没有回答,就跳过
				continue;
			}else {
				//获得提问者的头像和用户名
				User user2=userMapper.selectByPrimaryKey(question.getQuestionUsername());
				QuestionBody body=new QuestionBody();
				body.answer_content=question.getAnswerContent();
				body.answer_username=user.getUsername();
				body.question_content=question.getQuestionContent();
				body.question_logo=AddressValue.LOGO_PATH+user2.getLogo();
				body.question_username=user2.getUsername();
				body.dianzan_num=question.getZanNum();
				body.question_id=question.getQuestionId();
				body.question_createtime=initCreateData2(question.getCreatetime());
				//查询用户是否有点赞
				Map map=new HashMap<String, Object>();
				map.put("username", username);
				map.put("questionId", question.getQuestionId());
				Integer isDianZan=questionZanMapper.getZanNum(map);
				if (isDianZan==0) {
					//如果没有点赞
					body.isDianzan=false;
				}else {
					//如果有点赞
					body.isDianzan=true;
				}
				model.list.add(body);
			}
		}
		return model;
	}
	
	@Override
	public LessonDataModel requestDianZan(int lessonId, String username) {
		// TODO Auto-generated method stub
		LessonDataModel model=new LessonDataModel();
		model.lessonData=new LessonDataBody();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lessonId", lessonId);
		map.put("username", username);
		Integer isDianZan=lessonZanMapper.getZanNum(map);
		//如果没有点赞，就添加点赞，有就删除
		if (isDianZan==0) {
			LessonZan lessonZan=new LessonZan();
			lessonZan.setLessonId(lessonId);
			lessonZan.setUsername(username);
			lessonZanMapper.insert(lessonZan);
			//课程点赞数加1
			lessonMapper.addZan(lessonId);
			//返回点赞数量
			Integer zanNum=lessonMapper.getZanNum(lessonId);
			model.lessonData.dianzan=zanNum;
			model.lessonData.isDianzan=true;
		}else {
			LessonZan lessonZan=new LessonZan();
			lessonZan.setLessonId(lessonId);
			lessonZan.setUsername(username);
			lessonZanMapper.delete(lessonZan);
			//课程点赞数减1
			lessonMapper.reduceZan(lessonId);
			//返回点赞数量
			Integer zanNum=lessonMapper.getZanNum(lessonId);
			model.lessonData.dianzan=zanNum;
			model.lessonData.isDianzan=false;
		}
		model.ecode="0";
		return model;
	}

	@Override
	public LessonDataModel requestCollection(int lessonId, String username) {
		LessonDataModel model=new LessonDataModel();
		model.lessonData=new LessonDataBody();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lessonId", lessonId);
		map.put("username", username);
		Integer isCollection=userCollectionMapper.getCollectionNum(map);
		//如果没有收藏，就添加收藏，有就删除收藏
		if (isCollection==0) {
			UserCollection userCollection=new UserCollection();
			userCollection.setLessonId(lessonId);
			userCollection.setUsername(username);
			userCollectionMapper.insert(userCollection);
			//课程收藏数量加1
			lessonMapper.addCollection(lessonId);
			//返回课程收藏数量
			Integer collectionNum=lessonMapper.getCollectionNum(lessonId);
			model.lessonData.collection=collectionNum;
			model.lessonData.isCollection=true;
		}else {
			UserCollection userCollection=new UserCollection();
			userCollection.setLessonId(lessonId);
			userCollection.setUsername(username);
			userCollectionMapper.delete(userCollection);
			//课程收藏数量减1
			lessonMapper.reduceCollection(lessonId);
			//返回课程收藏数量
			Integer collectionNum=lessonMapper.getCollectionNum(lessonId);
			model.lessonData.collection=collectionNum;
			model.lessonData.isCollection=false;
		}
		model.ecode="0";
		return model;
	}
	
	//这里返回课程的详细内容
	@Override
	public LessonDataModel getLessonData(int lessonId,String username) {
		LessonDataModel model=new LessonDataModel();
		model.lessonData=null;
		Lesson lesson= lessonMapper.selectByPrimaryKey(lessonId);
		if (lesson!=null) {
			model.lessonData=new LessonDataBody();
			model.lessonData.resource=AddressValue.VIDEO_PATH+lesson.getVideoUrl();
			model.lessonData.author=lesson.getAuthor();
			model.lessonData.collection=lesson.getCollectNum();
			model.lessonData.dianzan=lesson.getZanNum();
			model.lessonData.createtime=initCreateData2(lesson.getCreatetime());
			model.lessonData.introduction=lesson.getIntroduce();
			model.lessonData.title=lesson.getTitle();
			User user=userMapper.selectByPrimaryKey(lesson.getAuthor());
			if (user!=null) {
				model.lessonData.logo=AddressValue.LOGO_PATH+user.getLogo();
			}
			//把查询条件放入到Map中
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("username", username);
			map.put("lessonId", lessonId);
			Integer isZan=lessonZanMapper.getZanNum(map);
			Integer isCollection=userCollectionMapper.getCollectionNum(map);
			if (isZan==0) {
				model.lessonData.isDianzan=false;
			}else {
				model.lessonData.isDianzan=true;
			}
			if (isCollection==0) {
				model.lessonData.isCollection=false;
			}else {
				model.lessonData.isCollection=true;
			}
		}
		model.ecode="0";
		return model;
	}
	
	@Override
	public LessonListRecommandModel loadNextPage(String condition,int currentPage) {
		int nextPage=currentPage+1;
		PageHelper.startPage(nextPage,PAGE_SIZE);
		PageInfo<Lesson> pageInfo=new PageInfo<Lesson>(lessonMapper.selectByParameter(condition));
		//这里通过判断当前页数是否小于等于总页数来推断是否存在当前页
		if (pageInfo.getPages()>=nextPage) {
			return packLessonListRecommandModel(pageInfo.getList());
		}
		//如果当前页不存在
		else {
			return packLessonListRecommandModel(null);
		}
	}
	
	@Override
	public LessonListRecommandModel getStudySearch(String condition,int currentPage) {
		List<Lesson> lessonList;
		//判断condition是否为空
		if (condition!=null && !"".equals(condition)) {
			//分页查找
			PageHelper.startPage(currentPage,PAGE_SIZE);
			PageInfo<Lesson> pageInfo=new PageInfo<Lesson>(lessonMapper.selectByParameter(condition));
			lessonList=pageInfo.getList();
		}else {
			//查找所有,按时间顺序查找，所以要创建example
			Example example=new Example(Lesson.class);
			example.setOrderByClause("createtime desc");
			//分页查找
			PageHelper.startPage(currentPage,PAGE_SIZE);
			PageInfo<Lesson> pageInfo=new PageInfo<Lesson>(lessonMapper.selectByExample(example));
			lessonList=pageInfo.getList();
		}
		return packLessonListRecommandModel(lessonList);
	}
	
	@Override
	public LessonListRecommandModel getStudyListView() {
		List<Lesson> lessonList;
		//查找所有,按时间顺序查找，所以要创建example
		Example example=new Example(Lesson.class);
		example.setOrderByClause("createtime desc");
		//分页查找
		PageHelper.startPage(1,PAGE_SIZE);
		PageInfo<Lesson> pageInfo=new PageInfo<Lesson>(lessonMapper.selectByExample(example));
		lessonList=pageInfo.getList();
		
		return packLessonListRecommandModel(lessonList);
	}
	
	//封装好类LessonListRecommandModel
	private LessonListRecommandModel packLessonListRecommandModel(List<Lesson> lessonList) {
		//返回的对象
		LessonListRecommandModel model=new LessonListRecommandModel();
		model.list=new ArrayList<LessonRecommandBody>();
		if (lessonList!=null && !lessonList.isEmpty()) {
			for(Lesson lesson:lessonList) {
				LessonRecommandBody body=new LessonRecommandBody();
				body.author=lesson.getAuthor();
				body.collection=lesson.getCollectNum().toString();
				body.createtime=initCreateDate(lesson.getCreatetime());
				body.introduce=lesson.getIntroduce();
				body.logo=AddressValue.LOGO_PATH+userMapper.selectByPrimaryKey(lesson.getAuthor()).getLogo();
				body.title=lesson.getTitle();
				body.lessonId=lesson.getLessonId();
				body.zan=lesson.getZanNum().toString();
				
				//把关于课程图片介绍的图片链接放进url中
				body.url=new ArrayList<String>();
				//创建一个Example对象并添加查询条件，通过selectByExample(example)查找出所有符合条件的LessonImg
				Example example=new Example(LessonImg.class);
				Criteria criteria=example.createCriteria();
				//注意，这里用的是LessonImg类中的属性名，不是数据库中的字段名
				criteria.andEqualTo("lessonId", lesson.getLessonId());
				java.util.List<LessonImg> lessonImgs= lessonImgMapper.selectByExample(example);
				
				//把查询到的结果都放进url中
				if (!lessonImgs.isEmpty()) {
					for(LessonImg lessonImg:lessonImgs) {
						body.url.add(lessonImg.getImageurl());
					}
				}
				if (lessonImgs.size()<=1) {
					body.type=2;
				}else {
					body.type=1;
				}
				
				//最后把整个LessonRecommandBody对象放到model.list中
				model.list.add(body);
				
			}
		}
		
		//这里设置ecode是因为前端使用了一个坑爹的Http框架，如果没有设置就不会执行返回对象（而且不会报错）
		model.ecode="0";
		return model;
	}
	
	//格式化Date
    private String initCreateDate(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(date);
        return "发布时间："+dateNowStr;
    }
    private String initCreateData2(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }
	
	

	
//	@Override
//	public boolean insertLesson(Lesson lesson,ArrayList<String> list) {
//		lessonMapper.insert(lesson);
//		if(lesson.getLessonId()>=0) {
//			if(!list.isEmpty()) {
//				for(String url:list) {
//					LessonImg lessonImg=new LessonImg();
//					lessonImg.setLessonId(lesson.getLessonId());
//					lessonImg.setImageurl(url);
//					lessonImgMapper.insert(lessonImg);
//				}
//			}
//			return true;
//		}
//		return false;
//	}

	
	
}
