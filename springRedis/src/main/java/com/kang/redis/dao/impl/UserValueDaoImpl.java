package com.kang.redis.dao.impl;

import com.kang.redis.dao.UserValueDao;
import com.kang.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * @Title ����
 * @Description ������
 * @Date 2015/11/25 16:34
 * @Author ������
 * @Version 2.0
 */
@Repository
public class UserValueDaoImpl implements UserValueDao {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @Override
    public void save(final User user) {
       /* boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                byte[] key = redisSerializer.serialize(user.getUid());
                byte[] value = redisSerializer.serialize(user.getName());
                return redisConnection.setNX(key, value);
            }
        });*/
        //return result;
        ValueOperations<String, User>  valueops = redisTemplate.opsForValue();

        String uid = user.getUid();
        valueops.set(user.getUid(),user);

       /* ValueOperations<String, User> valueOperations = redisTemplate
                .opsForValue();
        ListOperations<String, User> listOperations = redisTemplate.opsForList();
        User u1 = new User("27","drunk");
        User u2 = new User("28","fuck");
        listOperations.leftPush("user1", u1);
        valueOperations.set("user2", u2);
        System.out.println(listOperations.leftPop("user1").toString());
        System.out.println(valueOperations.get("user2").toString());*/

    }

    @Override
    public User get(String uid) {
        ValueOperations<String, User>  valueops = redisTemplate.opsForValue();
        return valueops.get(uid);
    }

    @Override
    public void delete(String uid) {
        //ValueOperations<String, User>  valueops = redisTemplate.opsForValue();
        redisTemplate.delete(uid);
    }


    public void boundGet(String uid){
       /* BoundValueOperations<String, User>  boundValueOperations =    redisTemplate.boundValueOps(uid);

        User user = new User();
        user.setUid(uid);
        user.setAddress("add");
        boundValueOperations.set(user);*/
        //boundValueOperations.getOperations();
    }
}
