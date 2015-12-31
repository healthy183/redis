package com.kang.redis.main;

import com.kang.redis.pubsub.pub.PubService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/30 17:12
 * @Author 梁健康
 * @Version 2.0
 */
public class PubSub {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        PubService pubService = (PubService)ac.getBean("pubServiceImpl");
        pubService.publisher("abc");

    }

}


