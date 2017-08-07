package com.kang.redis.dao.impl;

import com.kang.redis.dao.UserHashDao;
import com.kang.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 22:19
 * @Author 梁健康
 * @Version 2.0
 */
@Repository
public class UserHashDaoImpl implements UserHashDao {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    private HashOperations<String, String, User> hashOperations;
    @PostConstruct
    public void  init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(User user){
        hashOperations.put(user.getPid(),user.getUid(),user);
    }

    public User get(User user){
        return  hashOperations.get(user.getPid(),user.getUid());
    }

}
