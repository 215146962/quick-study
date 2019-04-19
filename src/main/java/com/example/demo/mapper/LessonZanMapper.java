package com.example.demo.mapper;

import java.util.Map;

import com.example.demo.pojo.LessonZan;
import com.example.demo.util.MyMapper;

public interface LessonZanMapper extends MyMapper<LessonZan> {
	 public Integer getZanNum(Map<String, Object> map);
}