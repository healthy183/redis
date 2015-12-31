package com.kang.redis.main;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @Title 类名
 * @Description
 * @Date 2015/11/25 10:38
 * @Author 梁健康
 * @Version 2.0
 */
@Configuration
public class ConfigBean {

    @Autowired
    private RedisPool redisPool;

    /*@Autowired
    private MessageListener subServiceImpl;*/

    @Bean
    public JedisPoolConfig jedisPoolConfig(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPool.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisPool.getMaxIdle());
        //jedisPoolConfig.setMinIdle(20);
        jedisPoolConfig.setMaxWaitMillis(redisPool.getMaxWaitMillis());
        jedisPoolConfig.setTestOnReturn(redisPool.getTestOnReturn());
        jedisPoolConfig.setTestOnBorrow(redisPool.getTestOnBorrow());
        return jedisPoolConfig;
    }
    @Bean
    public  JedisShardInfo jedisShardInfo(){
        return new JedisShardInfo(redisPool.getIp(),redisPool.getPort());
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory  jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(redisPool.getIp());
        jedisConnectionFactory.setPort(redisPool.getPort());
        //jedisConnectionFactory.setPassword(redisPool.getPassword());
        //jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setShardInfo(jedisShardInfo());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        //如果不配置Serializer，那么存储的时候智能使用String，如果用User类型存储，
        // 那么会提示错误User can't cast to String！！！
        //缺点:只能是泛型类型
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(false);
        return redisTemplate;
    }

   /* @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer redisMessageListenerContainer
                  = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
        List<ChannelTopic> toplicList = new ArrayList<ChannelTopic>();
        toplicList.add(channelTopic());
        HashMap<MessageListener, Collection<? extends Topic>> listeners
                = new HashMap<MessageListener, Collection<? extends Topic>>();
        listeners.put(subServiceImpl, toplicList);
        redisMessageListenerContainer.setMessageListeners(listeners);
        //redisMessageListenerContainer.setTaskExecutor(pulsubThreadPoolTaskScheduler());
        return redisMessageListenerContainer;
    }

    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("user:topic");
    }


    @Bean
    public ThreadPoolTaskScheduler pulsubThreadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(3);
        return threadPoolTaskScheduler;
    }*/


    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/springbatch3?useunicode=true&amp;characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("Qq123456");
        return dataSource;
    }

   /* @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
       // return new DataSourceTransactionManager(dataSource());
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }
*/

   /* @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.TRUE);
        vendorAdapter.setShowSql(Boolean.TRUE);

        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.jdon.springapp.entities");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        factory.setJpaProperties(jpaProperties);

        factory.afterPropertiesSet();
        factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return factory;
    }*/

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }








    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator(){
        AnnotationAwareAspectJAutoProxyCreator aop=new AnnotationAwareAspectJAutoProxyCreator();
        return aop;
    }




}
