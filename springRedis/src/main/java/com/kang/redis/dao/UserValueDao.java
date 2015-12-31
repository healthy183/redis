package com.kang.redis.dao;

import com.kang.redis.model.User;

/**
 * @Title ����
 * @Description ������
 * @Date 2015/11/25 16:07
 * @Author ������
 * @Version 2.0
 */
public interface UserValueDao {
    /**
     * @param user
     */
    void save(User user);

    /**
     * @param uid
     * @return
     */
    User get(String uid);
    /**
     * @param uid
     */
    void delete(String uid);

    /**
     * @param uid
     */
    public void boundGet(String uid);
}
