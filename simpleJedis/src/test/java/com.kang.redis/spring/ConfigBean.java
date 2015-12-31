package com.kang.redis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 10:38
 * @Author 梁健康
 * @Version 2.0
 */
@Component
public class ConfigBean {

    @Autowired
    private RedisPool redisPool;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPool.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisPool.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisPool.getMaxWaitMillis());
        jedisPoolConfig.setTestOnReturn(redisPool.getTestOnReturn());
        jedisPoolConfig.setTestOnBorrow(redisPool.getTestOnBorrow());
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(){
        return new JedisPool
                (jedisPoolConfig(),redisPool.getIp(),
                        Integer.valueOf(redisPool.getPort()));
    }


}
