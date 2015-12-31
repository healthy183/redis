package com.kang.redis.dao;

import com.kang.redis.model.User;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 22:19
 * @Author 梁健康
 * @Version 2.0
 */
public interface UserHashDao {

    public void save(User user);

    public User get(User user);
}
