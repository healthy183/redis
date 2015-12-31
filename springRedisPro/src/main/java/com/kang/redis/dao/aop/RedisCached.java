package com.kang.redis.dao.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/10 11:25
 * @Author 梁健康
 * @Version 2.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCached {
    /**
     * redis key
     * @return
     */
    String value();
    /**
     * 过期时间，默认为1分钟，如果要设置永不过期，请设置小于等于0的值
     * @return
     */
    long timeout() default 1;
    /**
     * 时间单位，默认为分钟
     * @return
     */
    TimeUnit timeunit() default TimeUnit.MINUTES;
    /**
     * 额外定义一个空方法，调用该方法来对之前的缓存进行更新
     * @return
     */
    boolean forDelete() default false;



}
