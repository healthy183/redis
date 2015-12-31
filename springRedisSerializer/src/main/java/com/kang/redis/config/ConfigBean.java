package com.kang.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

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
 /*   @Bean
    public JedisPool jedisPool(){
        return new JedisPool
                (jedisPoolConfig(),redisPool.getIp(),
                        redisPool.getPort());
    }*/
    @Bean
    public  JedisShardInfo jedisShardInfo(){
        return new JedisShardInfo(redisPool.getIp(),redisPool.getPort());
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory  jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(redisPool.getIp());
        jedisConnectionFactory.setPort(redisPool.getPort());
        //jedisConnectionFactory.setPassword(redisPool.getPassword());
        //jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setShardInfo(jedisShardInfo());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        //如果不配置Serializer，那么存储的时候智能使用String，如果用User类型存储，
        // 那么会提示错误User can't cast to String！！！
        //缺点:只能是User类型
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }

}
