package com.kang.redis.main;

import com.kang.redis.dao.ClazzValueDao;
import com.kang.redis.dao.UserValueDao;
import com.kang.redis.dao.impl.ClazzValueDaoImpl;
import com.kang.redis.model.Clazz;
import com.kang.redis.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/25 11:28
 * @Author 梁健康
 * @Version 2.0
 */
public class ClazzValueRun {


    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        ClazzValueDao clazzValueDaoImpl = (ClazzValueDao)ac.getBean("clazzValueDaoImpl");

        Clazz clazz  = new Clazz();
        clazz.setCId(11111);
        clazz.setClassName("1111Name");
        clazzValueDaoImpl.save(clazz);

        Clazz clazzss =  clazzValueDaoImpl.get(11111+"");
        System.out.println(clazzss);





    }
}
