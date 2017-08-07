package com.kang.redis.lock;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title RedisLock分布式锁  如何防止jvm自动唤醒?
 * @Description 参考LinkedBlockingQueue
 * @Date 2017/8/4.
 * @Author Healthy
 * @Version
 */
@Slf4j
public class RedisLock implements Lock, java.io.Serializable {

    private RedisTemplate redisTemplate;
    private final String lockKey;
    /*private final Long time;
    private final TimeUnit unit;*/
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition =  lock.newCondition();
    private final String lockValue;
    private static final AtomicBoolean isLock = new AtomicBoolean(Boolean.FALSE);


    public RedisLock(RedisTemplate redisTemplate,String lockKey){
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.lockValue = UUID.randomUUID().toString();
        Thread currentThread = Thread.currentThread();
        log.info("{}-{}-{} had ready!",currentThread.getName()+currentThread.getId(),lockKey,lockValue);
        /*time = Long.valueOf(Integer.MAX_VALUE);
        unit =  TimeUnit.NANOSECONDS;*/
    }

   /* public RedisLock(String lockKey,Long time,TimeUnit unit){
        this.lockKey = lockKey;
        this.time = time;
        this.unit = unit;
    }*/


    /**
     *  如果本机其他线程一直无法获取lock,
     * 那么就无法在放锁时触发condition.signal();
     *   会导致线程饥饿
     */
    @Override
    @Deprecated
    public void lock() {
        final ReentrantLock lock = this.lock;
        AtomicBoolean isLock = this.isLock;
        try{
            lock.lock();//请求锁
            while(isLock.get()){//如果锁在本机则等待
                condition.await();
            }
            //redis key已经存在则等待
            while(!this.setNX(lockKey, lockValue)) {
                condition.await();
            }
            isLock.compareAndSet(isLock.get(),true);
        }catch (Exception e){
            log.error("setNX redis error, key : {},cause:\n{}", lockKey, Throwables.getStackTraceAsString(e));
        }finally {
            lock.unlock();
        }
    }

    /**
     *   如果本机其他线程一直无法获取lock,
     *   那么就无法在放锁时触发condition.signal();
     *   会导致线程饥饿
     * @throws InterruptedException
     */
    @Override
    @Deprecated
    public void lockInterruptibly() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        AtomicBoolean isLock = this.isLock;
        try{
            lock.lockInterruptibly();//请求锁
            while(isLock.get()){//如果锁在本机则等待
                condition.await();
            }
            //redis key已经存在则等待
            while(!this.setNX(lockKey, lockValue)){
                condition.await();
            }
            isLock.compareAndSet(isLock.get(),true);
        }catch (Exception e){
            log.error("setNX redis error, key : {},cause:\n{}", lockKey, Throwables.getStackTraceAsString(e));
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean tryLock() {
        final ReentrantLock lock = this.lock;
        AtomicBoolean isLock = this.isLock;
        try{
            //请求锁
            if(!lock.tryLock()){
                return false;
            }
            if(isLock.get()){//如果锁在本机则返回false
               return false;
            }
            //redis key已经存在则等待
            if(!this.setNX(lockKey, lockValue)) {
                return false;
            }
            Thread currentThread = Thread.currentThread();
            log.info("{}-{}-{} getLock",currentThread.getName()+currentThread.getId(),lockKey,lockValue);
            return isLock.compareAndSet(isLock.get(),true);
        }catch (Exception e){
            log.error("setNX redis error, key : {},cause:\n{}", lockKey, Throwables.getStackTraceAsString(e));
        }finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        final ReentrantLock lock = this.lock;
        AtomicBoolean isLock = this.isLock;
        try{
            long nanos = unit.toNanos(timeout);
            lock.lockInterruptibly();
            //log.info("isLock {}",isLock.get());
            while (isLock.get()) {
                if (nanos <= 0){
                    return false;
                }
                log.info("first awaitNanos {}",nanos);
                nanos = condition.awaitNanos(nanos);
            }
            do{
                if(setNX(lockKey, lockValue)){
                    Thread currentThread = Thread.currentThread();
                    log.info("{}-{}-{} getLock",currentThread.getName()+currentThread.getId(),lockKey,lockValue);
                    return isLock.compareAndSet(isLock.get(),true);
                }else{
                    log.info("second awaitNanos {}",nanos);
                    nanos = condition.awaitNanos(nanos);
                    if (nanos <= 0){
                        //log.info("return false160");
                        return false;
                    }
                }
            }while (nanos > 0);
            /*do{
                if(nanos > 0){
                    if(setNX(lockKey, lockValue)){
                        Thread currentThread = Thread.currentThread();
                        log.info("{}-{}-{} getLock",currentThread.getName()+currentThread.getId(),lockKey,lockValue);
                        return isLock.compareAndSet(isLock.get(),true);
                    }
                }
                nanos = condition.awaitNanos(nanos);
                if(nanos <= 0) {
                    return false;
                }
            } while(!isLock.get());*/
            /*while (!isLock.get()) {
                if(nanos <= 0) {
                    return false;
                }
                if(!this.setNX(lockKey, lockValue)){
                    nanos = condition.awaitNanos(nanos);
                }else{
                    Thread currentThread = Thread.currentThread();
                    log.info("{}{}{} getLock",currentThread.getName()+currentThread.getId(),lockKey,lockValue);
                    return isLock.compareAndSet(isLock.get(),true);
                }
            }*/
        }catch (Exception e){
            log.error("setNX redis error, key : {},cause:\n{}", lockKey, Throwables.getStackTraceAsString(e));
        }finally {
            lock.unlock();
        }
        //log.info("return false200");
        return false;
    }

    /**
     *  AB并发取锁，A拿锁isLock.get()=true
     *  但B没有到锁,然后finally里面unlock时候怎么办？
     *  貌似只能通过value(随机数)来判断
     */
    @Override
    public void unlock() {
        final ReentrantLock lock = this.lock;
        AtomicBoolean isLock = this.isLock;
        lock.lock();
        try{
            if (isLock.get() && StringUtils.equals(get(lockKey), lockValue)) {
                redisTemplate.delete(lockKey);
                if(isLock.compareAndSet(isLock.get(),false)){
                    log.info(Thread.currentThread().getName()+"signal");
                    condition.signal();
                };
                Thread currentThread = Thread.currentThread();
                log.info("{}-{}-{} release Lock",currentThread.getName()+currentThread.getId(),lockKey,lockValue);
            }
        }catch (Exception e){
            log.error("delete  redis error, key : {},cause:\n{}", lockKey, Throwables.getStackTraceAsString(e));
        }finally {
            lock.unlock();
        }
    }

    private boolean setNX(final String key, final String value) {
        Boolean result = false;
        try {
            result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            log.error("setNX redis error, key : {},cause:{}", key, Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    private String get(final String key) {
        String obj = null;
        try {
            obj = (String) redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = connection.get(serializer.serialize(key));
                    connection.close();
                    return serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            log.error("getSet redis error, key : {}", key);
        }
        return obj != null ?  obj : null;
    }




    @Override
    @Deprecated
    public Condition newCondition() {
        return null;
    }
}
