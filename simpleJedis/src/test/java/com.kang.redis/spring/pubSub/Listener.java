package com.kang.redis.spring.pubSub;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:37
 * @Author 梁健康
 * @Version 2.0
 */
//@Component
public class Listener  extends JedisPubSub{

    // 取得订阅的消息后的处理
    public void onMessage(String channel,String message){
        System.out.println(channel+":"+message);
    }

    // 初始化订阅时候的处理
    public void onSubscribe(String channel,String subscribedChannels){
        //System.out.println();
    }

    // 取消订阅时候的处理
    public void onUnsubscribe(String channel,String subscribedChannels){
    //System.out.println(channel + "=" + subscribedChannels);
    }

    // 初始化按表达式的方式订阅时候的处理
    public void onPSubscribe(String pattern, int subscribedChannels) {
        // System.out.println(pattern + "=" + subscribedChannels);
    }

    // 取消按表达式的方式订阅时候的处理
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        // System.out.println(pattern + "=" + subscribedChannels);
    }

    // 取得按表达式的方式订阅的消息后的处理
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(pattern + "=" + channel + "=" + message);
    }


}

