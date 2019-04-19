package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.Question;
import com.example.demo.util.MyMapper;

public interface QuestionMapper extends MyMapper<Question> {
	public List<Question> selectBylessonId(@Param(value="lessonId")int lessonId);
	
	public void addZan(@Param(value="questionId")int questionId);
	public void reduceZan(@Param(value="questionId")int questionId);
	public Integer getZanNum(@Param(value="questionId")int questionId);
}