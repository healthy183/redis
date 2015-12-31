package com.kang.redis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 10:44
 * @Author 梁健康
 * @Version 2.0
 */
@Component
@Data
public class RedisPool {

    @Value("${redis.pool.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.pool.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.pool.maxWaitMillis}")
    private Long maxWaitMillis;

    @Value("${redis.pool.testOnBorrow}")
    private Boolean testOnBorrow;

    @Value("${redis.pool.testOnReturn}")
    private Boolean testOnReturn;

    @Value("${redis.pool.ip}")
    private String ip;

    @Value("${redis.pool.port}")
    private Integer port;

    @Value("${redis.pool.password}")
    private String password;
}
