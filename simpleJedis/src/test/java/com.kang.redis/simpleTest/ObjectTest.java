package com.kang.redis.simpleTest;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/23 10:46
 * @Author 梁健康
 * @Version 2.0
 */
public class ObjectTest {


    private String url = "192.168.88.134";
    public Jedis jedis;
    //new Jedis();
    public ObjectTest(){
        jedis = new Jedis(url,6379);
        //jedis.auth();验证登陆
    }

    public void itertor(Set set){
        for(Object o: set){
            System.out.println(o);
        }
    }
}
