package com.kang.redis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:18
 * @Author 梁健康
 * @Version 2.0
 */
@Component
public class RedisUtils {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取数据库连接
     * @return conn
     */
    public Jedis getConnection() {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedis;
    }

    /**
     * 关闭数据库连接
     * @param jedis
     */
    public void closeConnection(Jedis jedis) {
        if (null != jedis) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
