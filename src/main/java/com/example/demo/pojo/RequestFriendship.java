package com.example.demo.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "request_friendship")
public class RequestFriendship implements Serializable {
    private String username;

    private String friendname;

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
     * @return friendname
     */
    public String getFriendname() {
        return friendname;
    }

    /**
     * @param friendname
     */
    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }
}