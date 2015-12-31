package com.kang.redis.dao.impl;

import com.kang.redis.dao.UserDao;
import com.kang.redis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/26 0:47
 * @Author 梁健康
 * @Version 2.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;


    @Override
    public void save(final User user) {

        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(
                        redisTemplate.getStringSerializer().serialize(user.getUid()),
                        redisTemplate.getStringSerializer().serialize(user.getAddress())
                        );
                return null;
            }
        });
    }

    @Override
    public User read(final String uid) {

        User user =  redisTemplate.execute(new RedisCallback<User>() {
            @Override
            public User doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] key = redisTemplate.getStringSerializer().serialize(uid);

                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String address = redisTemplate.getStringSerializer().deserialize(value);
                    User user = new User();
                    user.setAddress(address);
                    user.setUid(uid);
                    return user;
                }
                return null;
            }
        });

        return user;
    }

    @Override
    public User delete(final String uid) {

        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(redisTemplate.getStringSerializer().serialize(uid));
                return null;
            }
        });
        return null;
    }


    public void hmset(final User user){
        ///Caused by: redis.clients.jedis.exceptions.JedisDataException: ERR Protocol error: invalid bulk length
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

            /*    byte[] key = redisTemplate.getStringSerializer().serialize(user.getUid());

                BoundHashOperations<Serializable, byte[], byte[]> boundHashOperations =
                        redisTemplate.boundHashOps(key);

                boundHashOperations.put(
                        redisTemplate.getStringSerializer().serialize("address"),
                        redisTemplate.getStringSerializer().serialize(user.getAddress()));

                boundHashOperations.put(
                        redisTemplate.getStringSerializer().serialize("mobile"),
                        redisTemplate.getStringSerializer().serialize(user.getMobile()));

                boundHashOperations.put(
                        redisTemplate.getStringSerializer().serialize("postCode"),
                        redisTemplate.getStringSerializer().serialize(user.getPostCode()));

                connection.hMSet(key,boundHashOperations.entries());*/
                byte[] key = redisTemplate.getStringSerializer().serialize(user.getUid());
                BoundHashOperations<Serializable, byte[], byte[]> boundHashOperations = redisTemplate
                        .boundHashOps(key);
                boundHashOperations.put(redisTemplate.getStringSerializer()
                        .serialize("mobile"), redisTemplate
                        .getStringSerializer().serialize(user.getMobile()));
                boundHashOperations.put(redisTemplate.getStringSerializer()
                        .serialize("address"), redisTemplate
                        .getStringSerializer().serialize(user.getAddress()));
                boundHashOperations.put(redisTemplate.getStringSerializer()
                        .serialize("postCode"), redisTemplate
                        .getStringSerializer().serialize(user.getPostCode()));
                connection.hMSet(key, boundHashOperations.entries());

                return null;
            }
        });


    }

    @Override
    public User hmget(final String uid) {

        User users =  redisTemplate.execute(new RedisCallback<User>() {
            @Override
            public User doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] key = redisTemplate.getStringSerializer().serialize(uid);
                User user = new User();
                if (connection.exists(key)) {

                    List<byte[]> value = connection.hMGet(key,
                            redisTemplate.getStringSerializer().serialize("address"),
                            redisTemplate.getStringSerializer().serialize("mobile"),
                            redisTemplate.getStringSerializer().serialize("postCode"));

                    String address = redisTemplate.getStringSerializer().deserialize(value.get(0));
                    String mobile = redisTemplate.getStringSerializer().deserialize(value.get(1));
                    String postCode = redisTemplate.getStringSerializer().deserialize(value.get(2));


                    user.setUid(uid);
                    user.setAddress(address);
                    user.setMobile(mobile);
                    user.setPostCode(postCode);

                }
                return user;
            }
        });


        return users;
    }


}
