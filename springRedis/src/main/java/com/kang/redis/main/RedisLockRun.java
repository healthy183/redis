package com.kang.redis.main;

import com.google.common.base.Throwables;
import com.kang.redis.lock.LockRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Title 类名
 * @Description 描述
 * @Date 2017/8/7.
 * @Author Healthy
 * @Version
 */
@Slf4j
public class RedisLockRun {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        RedisTemplate<String,String> redisTemplate = (RedisTemplate)ac.getBean("redisTemplate");
         String lockKey = "lockKey";
         long timeout = 100;
         TimeUnit unit = TimeUnit.SECONDS;
        LockRunnable lockRunnable = new LockRunnable(redisTemplate,lockKey,timeout,unit);
        RedisLockRun redisLockRun = new RedisLockRun();
        try {
            redisLockRun.timetasks(100,lockRunnable);
        } catch (InterruptedException e) {
            log.info(Throwables.getStackTraceAsString(e));
        }
    }

    public Long timetasks(int nThreads,final Runnable runnable) throws  InterruptedException{
        final CountDownLatch startCountDown = new CountDownLatch(1);
        final CountDownLatch endCountDown = new CountDownLatch(nThreads);
        for(int i = 0;i<nThreads;i++){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try{
                        try {
                            startCountDown.await();
                            runnable.run();
                        } finally {
                            endCountDown.countDown();
                        }
                    }catch(InterruptedException e){
                        log.info(Throwables.getStackTraceAsString(e));
                    }
                }
            };
        thread.setName(i+"threadLock");
    thread.start();
}
    Long startTime = System.nanoTime();
startCountDown.countDown();
        Long endTime = System.nanoTime();
        endCountDown.await();
        return endTime - startTime;
        }

}
