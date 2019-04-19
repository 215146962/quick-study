package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.Lesson;
import com.example.demo.pojo.User;
import com.example.demo.util.MyMapper;

public interface LessonMapper extends MyMapper<Lesson> {
	public List<Lesson> selectByParameter(@Param(value="condition") String condition);
	public void addZan(@Param(value="lessonId")int lessonId);
	public void reduceZan(@Param(value="lessonId")int lessonId);
	public Integer getZanNum(@Param(value="lessonId")int lessonId);
	
	public void addCollection(@Param(value="lessonId")int lessonId);
	public void reduceCollection(@Param(value="lessonId")int lessonId);
	public Integer getCollectionNum(@Param(value="lessonId")int lessonId);
	public User getAuthorBylessonId(@Param(value="lessonId")int lessonId);
}