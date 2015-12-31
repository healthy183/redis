package com.kang.redis.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/26 0:48
 * @Author 梁健康
 * @Version 2.0
 */
@Data
@ToString
public class User implements java.io.Serializable {

    private static final long serialVersionUID = -2718018359143987848L;

    private String uid;

    private String address;

    private String mobile;

    private String postCode;
}
