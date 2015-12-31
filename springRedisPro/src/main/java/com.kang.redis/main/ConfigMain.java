package com.kang.redis.main;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/9 9:59
 * @Author 梁健康
 * @Version 2.0
 */
@Configuration
@ComponentScan("com.kang")
public class ConfigMain {

    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(){
        Resource[] resources = new Resource[] { new ClassPathResource("redisPool.properties") };
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocations(resources);
        return propertyPlaceholderConfigurer;
    }

}
