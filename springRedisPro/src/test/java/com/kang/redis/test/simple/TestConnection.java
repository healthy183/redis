package com.kang.redis.test.simple;

import com.kang.redis.main.ConfigMain;
import com.kang.redis.main.RedisPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/9 10:22
 * @Author 梁健康
 * @Version 2.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ConfigMain.class)
public class TestConnection {


    @Autowired
    private RedisTemplate<String, String> template;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> vOps;

    @Autowired
    private RedisPool redisPool;

    @Test
    //@Transactional
    public void testTx(){
        String key = "vopsKeytx";
        vOps.set(key, "vopsVal");
        String value =(String)vOps.get(key);
        System.out.println(value);

    }


    @Test
    public void testAutowired(){
        System.out.println("ip:"+redisPool.getIp());
    }


    @Test
    public void testSetGet(){
        String key = "vopsKeyNtx";
        vOps.set(key, "vopsVal");
        String value =(String)vOps.get(key);
        System.out.println(value);
    }


    @Test
    public void testRollbackSet(){
        template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = "tempkey".getBytes();
                byte[] value = "tempvalue".getBytes();
                connection.set(key, value);
                return true;
            }
        });
    }

    @Test
    public void testRollbackGet(){

        template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = "tempkey".getBytes();
                byte[] bytes = connection.get(key);
                String keyValue = new String(bytes);
                System.out.println("keyValue:"+keyValue);
                return true;
            }
        });
    }

    @Test
    public void testGet(){
        String value =(String)vOps.get("tempkey"); //throw exception!
        System.out.println("tempkey:"+value);
    }

}
