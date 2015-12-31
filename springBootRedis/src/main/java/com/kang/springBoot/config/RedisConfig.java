package com.kang.springBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;


@Configuration
public class RedisConfig {

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
    public JedisShardInfo jedisShardInfo(){
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
        //���������Serializer����ô�洢��ʱ������ʹ��String�������User���ʹ洢��
        // ��ô����ʾ����User can't cast to String������
        //ȱ��:ֻ���Ƿ�������
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }






}
