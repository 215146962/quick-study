package com.example.demo.mapper;

import java.util.Map;

import com.example.demo.pojo.UserCollection;
import com.example.demo.util.MyMapper;

public interface UserCollectionMapper extends MyMapper<UserCollection> {
	public Integer getCollectionNum(Map<String, Object> map);
}