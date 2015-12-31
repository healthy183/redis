package com.kang.redis.poolConfig;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/24 19:20
 * @Author 梁健康
 * @Version 2.0
 */
public class PoolConfig {

    private String url = "192.168.88.134";
    public Jedis jedis;
    public JedisPool pool;
    //new Jedis();
    public PoolConfig(){
        //jedis = new Jedis(url,6379);
        //jedis.auth();验证登陆
        pool = new JedisPool(new JedisPoolConfig(),url);
        jedis = pool.getResource();
    }

    @Test
    public  void set(){
        String strKey = "abc";
        jedis.set(strKey, "firstVal123"); //set a new key which is String
        String firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " is " + firstVal);
    }
}
