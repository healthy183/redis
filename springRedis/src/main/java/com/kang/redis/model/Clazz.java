package com.kang.redis.model;

import lombok.Data;
import lombok.ToString;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/27 20:33
 * @Author 梁健康
 * @Version 2.0
 */
@Data
@ToString
public class Clazz implements  java.io.Serializable {

    private Integer cId;
    private String className;
}
