package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.module.BaseMessage;
import com.example.demo.module.lessonData.LessonDataModel;
import com.example.demo.module.lessonlist.LessonListRecommandModel;
import com.example.demo.module.questionlist.QuestionDianZanBody;
import com.example.demo.module.questionlist.RequestQuestionListModel;
import com.example.demo.pojo.Lesson;

public interface ILessonService {
//	public boolean insertLesson(Lesson lesson,ArrayList<String> list);
	public LessonListRecommandModel getStudyListView();
	public LessonListRecommandModel getStudySearch(String condition,int currentPage);
	public LessonListRecommandModel loadNextPage(String condition, int currentPage);
	public LessonDataModel getLessonData(int lessonId, String username);
	public LessonDataModel requestDianZan(int lessonId,String username);
	public LessonDataModel requestCollection(int lessonId,String username);
	public RequestQuestionListModel requestQuestionList(int lessonId,int currentPage,String username);
	public QuestionDianZanBody questionDianZan(int questionId,String username);
	public RequestQuestionListModel questionNextPage(int lessonId, int currentPage, String username);
	public BaseMessage askQuestion(int lessonId,String username,String question_content);
}
