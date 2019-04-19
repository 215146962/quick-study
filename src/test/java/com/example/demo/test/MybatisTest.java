package com.example.demo.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.mapper.LessonImgMapper;
import com.example.demo.pojo.LessonImg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
	
	@Autowired
	private LessonImgMapper lessonImgMapper;
	
	//@Test
	public void pageHelperTest() {
		List<LessonImg> lessonImgs;
		//第一个参数是页数，第二个参数是每一页数据的数目
		//这个页数是从第一页开始，没有第零页
		PageHelper.startPage(3,2).setOrderBy("lesson_id desc");
		PageInfo<LessonImg> lessonImgInfo=new PageInfo<LessonImg>(lessonImgMapper.selectAll());
		//这里通过PageInfo中的getList()方法获取查询到的数据
		System.err.println(lessonImgInfo);
		lessonImgs=lessonImgInfo.getList();
		for(LessonImg lessonImg:lessonImgs) {
			System.out.println(lessonImg.getLessonId()+"------"+lessonImg.getImageurl());
		}
	}
}
