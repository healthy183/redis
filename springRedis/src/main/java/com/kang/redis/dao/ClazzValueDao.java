package com.kang.redis.dao;


import com.kang.redis.model.Clazz;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/27 20:29
 * @Author 梁健康
 * @Version 2.0
 */
public interface ClazzValueDao {
    /**
     * @param user
     */
    void save(Clazz user);
    /**
     * @param uid
     * @return
     */
    Clazz get(String uid);
}
