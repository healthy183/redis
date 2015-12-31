package com.kang.redis.dao.aop;

import com.kang.redis.dao.BaseRedisDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/10 10:52
 * @Author 梁健康
 * @Version 2.0
 */
@Aspect
@Slf4j
@Component
public class AutoRedisCached extends BaseRedisDao<Object, Object> implements Ordered {
    /*
     * 约束任意包下的包含Dao的类的任意方法，并且被cached注解
     * @Pointcut("execution(* *..*Dao*.*(*,..) && @annotation(com.ns.annotation.Cached))")
     */
    @Pointcut("execution(* *..*Dao*.*(..)) && @annotation(com.kang.redis.dao.aop.RedisCached)")
    private void cacheMethod(){}

    @Around("cacheMethod()")
    public Object doArround(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        final RedisCached cacheinfo = method.getAnnotation(RedisCached.class);

        //定义序列化器
        final RedisSerializer<String> keySerializer = getStringSerializer();
        final RedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(method.getReturnType());

        //序列化参数，作为hashkey
        byte [] keyBytesTemp = keySerializer.serialize(cacheinfo.value());
        for(Object arg : args){
            keyBytesTemp = ArrayUtils.addAll(keyBytesTemp, getDefaultSerializer().serialize(arg));
        }
        //取md5后key
        final byte [] keyBytes = keySerializer.serialize(DigestUtils.md5Hex(keyBytesTemp));

        //是删除方法
        if(cacheinfo.forDelete()){
            execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.del(keyBytes);
                }
            });
            return null;
        }

        Object obj= null;
        log.info("方法"+method.getName()+"切面，尝试从缓存获取...");
        obj = execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte [] tmp = connection.get(keyBytes);
                return valueSerializer.deserialize(tmp);
            }
        });
        if(obj == null){
            log.info("方法"+method.getName()+"切面，缓存未找到...");
            final Object objReturn = pjp.proceed();
            if(objReturn != null){
                execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        if(cacheinfo.timeout() > 0){
                            connection.setEx(keyBytes, TimeUnit.SECONDS.convert(cacheinfo.timeout(), cacheinfo.timeunit()), valueSerializer.serialize(objReturn));
                        }else{
                            connection.set(keyBytes,valueSerializer.serialize(objReturn));
                        }
                        return true;
                    }
                });
            }
            obj = objReturn;
        }else{
            log.info("方法"+method.getName()+"切面，缓存命中...");
        }
        //从dao获取
        return obj;
    }

    @Override
    public int getOrder() {
        return -1;
    }



}
