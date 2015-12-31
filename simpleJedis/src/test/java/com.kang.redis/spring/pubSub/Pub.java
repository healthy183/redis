package com.kang.redis.spring.pubSub;

import com.kang.redis.spring.RedisUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:48
 * @Author 梁健康
 * @Version 2.0
 */
public class Pub {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        RedisUtils redisUtils  = (RedisUtils)ac.getBean("redisUtils");
        final Jedis jedis = redisUtils.getConnection();
        jedis.publish("hello_foo", "bar123");
        jedis.publish("hello_test", "hello watson");
    }
}
