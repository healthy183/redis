package com.kang.redis.dao;

import com.kang.redis.model.User;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/26 0:46
 * @Author 梁健康
 * @Version 2.0
 */
public interface UserDao {

    public void save(final User user);

    public User read(final String uid);

    public User delete(final String uid);

    public void hmset(final User user);

    public User hmget(final String uid);
}


