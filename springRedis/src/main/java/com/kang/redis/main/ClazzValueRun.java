package com.kang.redis.main;

import com.kang.redis.dao.ClazzValueDao;
import com.kang.redis.model.Clazz;
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
        Integer cid = 11111;
        Clazz clazz  = new Clazz();
        clazz.setCId(cid);
        clazz.setClassName("1111Name");
        clazzValueDaoImpl.save(clazz);
        Clazz clazzss =  clazzValueDaoImpl.get(cid+"");
        System.out.println(clazzss);
    }
}
