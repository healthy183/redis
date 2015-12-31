package com.kang.redis.dao.impl;

import com.kang.redis.dao.ClazzValueDao;
import com.kang.redis.model.Clazz;
import com.kang.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/27 20:29
 * @Author 梁健康
 * @Version 2.0
 */
@Repository
public class ClazzValueDaoImpl implements ClazzValueDao {


    @Autowired
    private RedisTemplate<String, Clazz> classTemplate;

    @Autowired
    private RedisTemplate<String, User> userTemplate;


    @Override
    public void save(Clazz clazz) {
        ValueOperations<String, Clazz> valueops = classTemplate.opsForValue();
        valueops.set(clazz.getCId().toString(), clazz);

        User user = new User();
        user.setUid("user11");
        user.setAddress("user11Add");

        ValueOperations<String, User>  userOps = userTemplate.opsForValue();
        userOps.set(user.getUid(), user);

    }

    @Override
    public Clazz get(String uid) {
        ValueOperations<String, Clazz>  valueops = classTemplate.opsForValue();

        ValueOperations<String, User>  userOps = userTemplate.opsForValue();
        System.out.println(userOps.get("user11"));

        return valueops.get(uid);




    }
}
