package com.kang.redis.spring.pubSub;

import com.kang.redis.spring.RedisUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:45
 * @Author 梁健康
 * @Version 2.0
 */
public class Sub {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        RedisUtils redisUtils  = (RedisUtils)ac.getBean("redisUtils");
        final Jedis jedis = redisUtils.getConnection();

        final Listener listener  = new Listener();
        //这里启动了订阅监听，线程将在这里被阻塞
        //订阅得到信息在lister的onPMessage(...)方法中进行处理
        jedis.psubscribe(listener,new String[]{"hello_*"});//使用模式匹配的方式设置频道

    }
}
