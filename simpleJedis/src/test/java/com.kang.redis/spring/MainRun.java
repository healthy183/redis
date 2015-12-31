package com.kang.redis.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:28
 * @Author 梁健康
 * @Version 2.0
 */
public class MainRun {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        RedisUtils redisUtils  = (RedisUtils)ac.getBean("redisUtils");
        Jedis jedis = redisUtils.getConnection();

        String strKey = "springKey";
        jedis.set(strKey, "firstVal123"); //set a new key which is String
        String firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " is " + firstVal);

        redisUtils.closeConnection(jedis);
    }
}
