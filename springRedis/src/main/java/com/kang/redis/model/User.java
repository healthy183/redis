package com.kang.redis.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Title
 * @Description
 * @Date 2015/11/25 16:08
 * @Author
 * @Version 2.0
 */
@Data
@ToString
public class User implements  java.io.Serializable {

    private static final long serialVersionUID = -2718018359143987848L;
    public String pid;
    private String uid;
    private String name;
    private String address;


    public User() {
    }

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public User(String pid, String uid, String name) {
        this.pid = pid;
        this.uid = uid;
        this.name = name;
    }
}
