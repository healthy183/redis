package com.kang.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

/**
 * @Title 类名
 * @Description
 * @Date 2015/11/25 10:38
 * @Author 梁健康
 * @Version 2.0
 */
@Component
public class ConfigBean {

    @Autowired
    private RedisPool redisPool;

    /*消息队列,这个注释掉,还有下面的channelTopic，redisMessageListenerContainer，
    pubsub包下面的代码也是*/
   /* @Autowired
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
        JedisShardInfo jedisShardInfo = new JedisShardInfo(redisPool.getIp(),redisPool.getPort());
        jedisShardInfo.setPassword(redisPool.getPassword());
        return jedisShardInfo;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory  jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(redisPool.getIp());
        jedisConnectionFactory.setPort(redisPool.getPort());
        jedisConnectionFactory.setPassword(redisPool.getPassword());
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
        redisTemplate.setEnableTransactionSupport(true);
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


    *//**指定监听topic
     * @return
     *//*
    @Bean
    public ChannelTopic channelTopic(){
        return new ChannelTopic("user:topic");
    }*/


    @Bean
    public ThreadPoolTaskScheduler pulsubThreadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(3);
        return threadPoolTaskScheduler;
    }
}
