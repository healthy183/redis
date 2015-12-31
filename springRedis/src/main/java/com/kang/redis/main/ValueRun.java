package com.kang.redis.main;

import com.kang.redis.dao.UserValueDao;
import com.kang.redis.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisConnectionUtils;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:28
 * @Author 梁健康
 * @Version 2.0
 */
public class ValueRun {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        UserValueDao userDao = (UserValueDao)ac.getBean("userValueDaoImpl");

        User user = new User();
        String suid = "iam";
        user.setUid(suid);
        user.setName("efg");
        userDao.save(user);

        User rUser = null;
        rUser =  userDao.get(suid);
        System.out.println(rUser.getName());
        /*for(int i = 0;i <=21;i++){
             rUser =  userDao.get(suid);
             System.out.println(rUser.getName());
        }*/



        userDao.delete("1");

        rUser =  userDao.get("1");
        System.out.println(rUser == null);

        //RedisConnectionUtils redis;

        String uid = "abcId";
        userDao.boundGet(uid);
        rUser =  userDao.get(uid);
        System.out.println(rUser == null);


    }
}
