package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Lesson implements Serializable{
    /**
     * 课程id
     */
    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(generator = "JDBC")
    private Integer lessonId;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 作者，与username关联
     */
    private String author;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 视频链接
     */
    @Column(name = "video_url")
    private String videoUrl;

    /**
     * 收藏数量
     */
    @Column(name = "collect_num")
    private Integer collectNum;

    /**
     * 点赞数量
     */
    @Column(name = "zan_num")
    private Integer zanNum;

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
     * 获取课程标题
     *
     * @return title - 课程标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置课程标题
     *
     * @param title 课程标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取作者，与username关联
     *
     * @return author - 作者，与username关联
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者，与username关联
     *
     * @param author 作者，与username关联
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取介绍
     *
     * @return introduce - 介绍
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置介绍
     *
     * @param introduce 介绍
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取视频链接
     *
     * @return video_url - 视频链接
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置视频链接
     *
     * @param videoUrl 视频链接
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * 获取收藏数量
     *
     * @return collect_num - 收藏数量
     */
    public Integer getCollectNum() {
        return collectNum;
    }

    /**
     * 设置收藏数量
     *
     * @param collectNum 收藏数量
     */
    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
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