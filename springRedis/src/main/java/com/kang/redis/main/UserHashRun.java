package com.kang.redis.main;

import com.kang.redis.dao.UserHashDao;
import com.kang.redis.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 22:41
 * @Author 梁健康
 * @Version 2.0
 */
public class UserHashRun {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        UserHashDao userHashDao =  (UserHashDao)ac.getBean("userHashDaoImpl");

        User user = new User("groupA","1","a");
        userHashDao.save(user);

        user =  userHashDao.get(user);
        System.out.println(user.toString());



    }
}
