package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class User implements Serializable {
    @Id
    private String username;

    private String password;

    private String type;

    private Integer point;

    private String logo;

    private Date createtime;

    private Integer freeze;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 性别
     */
    private String sex;

    /**
     * 使用环信时聊天的id
     */
    private String chatid;

    private static final long serialVersionUID = 1L;

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

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return point
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
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
     * @return freeze
     */
    public Integer getFreeze() {
        return freeze;
    }

    /**
     * @param freeze
     */
    public void setFreeze(Integer freeze) {
        this.freeze = freeze;
    }

    /**
     * 获取个性签名
     *
     * @return signature - 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置个性签名
     *
     * @param signature 个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取使用环信时聊天的id
     *
     * @return chatid - 使用环信时聊天的id
     */
    public String getChatid() {
        return chatid;
    }

    /**
     * 设置使用环信时聊天的id
     *
     * @param chatid 使用环信时聊天的id
     */
    public void setChatid(String chatid) {
        this.chatid = chatid;
    }
}