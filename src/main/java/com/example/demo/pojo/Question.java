package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Question implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "lesson_id")
    private Integer lessonId;

    @Column(name = "question_username")
    private String questionUsername;

    @Column(name = "question_content")
    private String questionContent;

    @Column(name = "answer_content")
    private String answerContent;

    private Date createtime;

    /**
     * 点赞数量
     */
    @Column(name = "zan_num")
    private Integer zanNum;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识
     *
     * @return question_id - 唯一标识
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 设置唯一标识
     *
     * @param questionId 唯一标识
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

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
     * @return question_username
     */
    public String getQuestionUsername() {
        return questionUsername;
    }

    /**
     * @param questionUsername
     */
    public void setQuestionUsername(String questionUsername) {
        this.questionUsername = questionUsername;
    }

    /**
     * @return question_content
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * @param questionContent
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * @return answer_content
     */
    public String getAnswerContent() {
        return answerContent;
    }

    /**
     * @param answerContent
     */
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取点赞数量
     *
     * @return zan_num - 点赞数量
     */
    public Integer getZanNum() {
        return zanNum;
    }

    /**
     * 设置点赞数量
     *
     * @param zanNum 点赞数量
     */
    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }
}