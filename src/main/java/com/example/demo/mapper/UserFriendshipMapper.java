package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.example.demo.pojo.User;
import com.example.demo.pojo.UserFriendship;
import com.example.demo.util.MyMapper;

public interface UserFriendshipMapper extends MyMapper<UserFriendship> {
	public List<User> selectByUsername(@Param(value="username") String username);
	public Integer selectByUsernameAndFriendname(Map<String, Object> map);
}