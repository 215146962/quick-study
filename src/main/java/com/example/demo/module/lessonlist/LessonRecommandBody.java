package com.example.demo.module.lessonlist;

import java.util.ArrayList;
import java.util.Date;

import com.example.demo.module.BaseModel;

public class LessonRecommandBody extends BaseModel {
    public int type;        //类型：只有1和2，交给后台作判断，用于区分单图和多图
    public int lessonId;
    public String logo;     //头像
    public String title;    //标题
    public String author;   //作者的用户名
    public String zan;      //点赞数
    public String collection;    //收藏数
    public String introduce;	//介绍
    public String createtime;		//创建时间
    public ArrayList<String> url;   //所有图片的链接
}
