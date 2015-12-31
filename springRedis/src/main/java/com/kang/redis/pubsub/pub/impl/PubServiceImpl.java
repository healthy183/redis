package com.kang.redis.pubsub.pub.impl;

import com.kang.redis.pubsub.pub.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Title 类名
 * @Description 发布
 * @Date 2015/11/30 16:09
 * @Author 梁健康
 * @Version 2.0
 */
@Service
public class PubServiceImpl implements PubService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String channelTopic = "user:topic";

    /*发布消息到Channel*/
    public void publisher(String message) {
        redisTemplate.convertAndSend(channelTopic, message);
    }

}
