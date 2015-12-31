package com.kang.redis.dao;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
/**
 * key统一为String，省略.
 * HK为hash类型的hashkey类型，
 * HV为value类型或者hashvalue类型（这两个不可能同时存在，所以只取一个）
 */
public class BaseRedisDao<HK, HV> implements InitializingBean{

    //实际参数的class start
    private Class<HK> hkClass;

    private Class<HV> hvClass;

    private Class<HK> getHKClass(){
        if (hkClass == null) {
            hkClass = (Class<HK>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return hkClass;
    }

    private Class<HV> getHVClass(){
        if (hvClass == null) {
            hvClass = (Class<HV>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return hvClass;
    }
    // end
    @Autowired
    private RedisTemplate<String, HV> redisTemplate;

    protected ValueOperations<String, HV> valueOperations;
    //
    protected HashOperations<String, HK, HV> hashOperations;
    //
    protected ListOperations<String, HV> listOperations;

    protected SetOperations<String, HV> setOperations;
    /**
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    protected void set(String key, HV value, long expire) {
        valueOperations.set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * get value
     *
     * @param key
     * @return
     */
    protected HV get(String key) {
        return valueOperations.get(key);
    }

    /**
     * key delete
     * @param key
     */
    protected void delete(String key){
        getRedisTemplate().delete(key);
    }

    /**
     * key exist
     * @param key
     * @return
     */
    protected boolean hasKey(String key){
        return  getRedisTemplate().hasKey(key);
    }
    /**
     *key expire
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    protected Boolean expire(String key,long timeout,TimeUnit unit){
        return getRedisTemplate().expire(key, timeout, unit);
    }
    /**
     * redistemplate是全局唯一的，子类不要出现对redistemplate的成员变量的设置(比如keyserializer,)
     * @return
     */
    RedisTemplate<String, HV> getRedisTemplate() {
        return redisTemplate;
    }
    /**
     * 当需要更改serializer,可以直接通过connection.set等方法实现
     * @param callback
     * @return
     */
    protected <T> T execute(RedisCallback<T> callback){
        return redisTemplate.execute(callback);
    }
    /**
     * 获取stringserializer
     */
    protected RedisSerializer<String> getStringSerializer(){
        return redisTemplate.getStringSerializer();
    }
    /**
     * 获取JdkSerializationRedisSerializer
     */
    @SuppressWarnings("unchecked")
    protected <T> RedisSerializer<T> getDefaultSerializer(){
        return (RedisSerializer<T>) redisTemplate.getDefaultSerializer();
    }
    /**
     * 获取stringserializer
     * @return
     */
    @SuppressWarnings("unchecked")
    protected  RedisSerializer<String> getKeySerializer(){
        return (RedisSerializer<String>) redisTemplate.getKeySerializer();
    }
    /**
     * 获取jackson2jsonredisserializer
     * @return
     */
    protected RedisSerializer<HV> getValueSerializer(){
        return (RedisSerializer<HV>) redisTemplate.getValueSerializer();
    }
    /**
     * 获取jackson2jsonredisserializer
     * @return
     */
    @SuppressWarnings("unchecked")
    protected RedisSerializer<HK> getHashKeySerializer() {
        return (RedisSerializer<HK>) redisTemplate.getHashKeySerializer();
    }

    /**
     * 获取jackson2jsonredisserializer
     * @return
     */
    @SuppressWarnings("unchecked")
    protected RedisSerializer<HV> getHashValueSerializer() {
        return (RedisSerializer<HV>) redisTemplate.getHashValueSerializer();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(getHKClass() == null || getHVClass() == null){
            throw new IllegalArgumentException("获取泛型class失败");
        }
        //
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        //
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<HV>(getHVClass()));
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<HK>(getHKClass()));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<HV>(getHVClass()));
    }
}
