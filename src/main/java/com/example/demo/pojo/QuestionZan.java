package com.example.demo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "question_zan")
public class QuestionZan implements Serializable {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    private String username;

    private static final long serialVersionUID = 1L;

    /**
     * @return question_id
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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