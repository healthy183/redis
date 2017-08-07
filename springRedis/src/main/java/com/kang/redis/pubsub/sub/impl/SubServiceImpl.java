package com.kang.redis.pubsub.sub.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * @Title 类名
 * @Description 订阅
 * @Date 2015/11/30 11:43
 * @Author 梁健康
 * @Version 2.0
 */
//@Service
public class SubServiceImpl implements MessageListener {

    @Autowired
    private ChannelTopic channelTopic;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //redisTemplate.convertAndSend(channelTopic,message);
        byte[] bytes =  message.getBody();
        //System.out.println(message.toString() + " :   " + channelTopic.getTopic());
        System.out.println("pattern:"+new String(pattern)
                +"\tchannel:"+new String(message.getChannel())
                    +"\tbody:"+new String(message.getBody()));
    }
}
