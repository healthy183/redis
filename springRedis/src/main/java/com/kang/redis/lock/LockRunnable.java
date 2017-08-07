package com.kang.redis.lock;

import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Title 类名
 * @Description 描述
 * @Date 2017/8/7.
 * @Author Healthy
 * @Version
 */
@AllArgsConstructor
@Slf4j
public class LockRunnable  implements Runnable {

    private final  RedisTemplate redisTemplate;
    private final String lockKey;
    private final long timeout;
    private final TimeUnit unit;

    @Override
    public void run() {
        RedisLock redisLock = new RedisLock(redisTemplate,lockKey);
        try {
            Boolean lockResult = redisLock.tryLock(timeout,unit);
            if(lockResult){
                Thread.sleep(50);
            }else{
                log.info("{} get lockFail!",Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.info(Throwables.getStackTraceAsString(e));
        }finally {
            redisLock.unlock();
        }
    }
}
