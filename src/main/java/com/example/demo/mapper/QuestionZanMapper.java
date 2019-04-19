package com.example.demo.mapper;

import java.util.Map;

import com.example.demo.pojo.QuestionZan;
import com.example.demo.util.MyMapper;

public interface QuestionZanMapper extends MyMapper<QuestionZan> {
	public Integer getZanNum(Map<String, Object> map);
}