package com.example.demo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "lesson_img")
public class LessonImg implements Serializable {
    /**
     * 课程id
     */
    @Column(name = "lesson_id")
    private Integer lessonId;

    /**
     * 图片地址
     */
    private String imageurl;

    private static final long serialVersionUID = 1L;

    /**
     * 获取课程id
     *
     * @return lesson_id - 课程id
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * 设置课程id
     *
     * @param lessonId 课程id
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * 获取图片地址
     *
     * @return imageurl - 图片地址
     */
    public String getImageurl() {
        return imageurl;
    }

    /**
     * 设置图片地址
     *
     * @param imageurl 图片地址
     */
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}