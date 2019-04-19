package com.example.demo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "lesson_zan")
public class LessonZan implements Serializable {
    @Id
    @Column(name = "lesson_id")
    private Integer lessonId;

    @Id
    private String username;

    private static final long serialVersionUID = 1L;

    /**
     * @return lesson_id
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}