package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.RequestFriendship;
import com.example.demo.pojo.User;
import com.example.demo.util.MyMapper;

public interface RequestFriendshipMapper extends MyMapper<RequestFriendship> {
	public Integer selectByUsernameAndFriendname(Map<String, Object> map);
	public Integer deleteByUsernameAndFriendname(Map<String, Object> map);
	public List<User> selectUserByFriendname(@Param(value="friendname") String friendname);
}