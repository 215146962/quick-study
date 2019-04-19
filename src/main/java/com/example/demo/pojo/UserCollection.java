package com.example.demo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "user_collection")
public class UserCollection implements Serializable {
    /**
     * 这个是中间表，用户名
     */
    @Id
    private String username;

    /**
     * 课程id
     */
    @Id
    @Column(name = "lesson_id")
    private Integer lessonId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取这个是中间表，用户名
     *
     * @return username - 这个是中间表，用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置这个是中间表，用户名
     *
     * @param username 这个是中间表，用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

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
}