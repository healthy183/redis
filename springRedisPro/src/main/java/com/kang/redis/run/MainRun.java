package com.kang.redis.run;

import com.kang.redis.main.ConfigMain;
import com.kang.redis.main.RedisPool;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/9 9:47
 * @Author 梁健康
 * @Version 2.0
 */
public class MainRun {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ConfigMain.class);
        RedisPool RedisPool = (com.kang.redis.main.RedisPool) ctx.getBean("redisPool");
        System.out.println(RedisPool.getIp());
    }



}
