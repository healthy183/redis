package com.kang.springBoot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "redis.pool")
@Data
public class RedisPool {

    private Integer maxTotal;

    private Integer maxIdle;

    private Long maxWaitMillis;

    private Boolean testOnBorrow;

    private Boolean testOnReturn;

    private String ip;

    private Integer port;

    private String password;
}
