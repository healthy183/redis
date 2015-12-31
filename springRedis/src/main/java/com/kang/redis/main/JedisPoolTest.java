package com.kang.redis.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Title ¿‡√˚
 * @Description ¿‡√Ë ˆ
 * @Date 2015/11/25 19:51
 * @Author ¡∫Ω°øµ
 * @Version 2.0
 */
public class JedisPoolTest {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        JedisPool jedisPool = (JedisPool)ac.getBean("jedisPool");
        Jedis jedis = jedisPool.getResource();
        jedis.set("abcKey", "valABc");
        String val = jedis.get("abcKey");
        System.out.println(val);
    }
}
