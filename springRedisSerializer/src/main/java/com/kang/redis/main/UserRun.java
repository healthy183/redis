package com.kang.redis.main;

import com.kang.redis.dao.UserDao;
import com.kang.redis.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/27 17:01
 * @Author 梁健康
 * @Version 2.0
 */
public class UserRun {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        UserDao userDao = (UserDao)ac.getBean("userDaoImpl");

        User user = new User();
        user.setUid("ljkabc");
        user.setAddress("abc");
        user.setMobile("1333");
       /* userDao.save(user);
        User readUser = userDao.read("1");
        System.out.println(readUser.getAddress());*/

        userDao.hmset(user);

        //User users =  userDao.hmget(user.getUid());
        //System.out.println(users);
    }


}
