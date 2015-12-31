package com.kang.redis.test.simple;

import com.kang.pojo.dao.CustomerDao;
import com.kang.pojo.model.CustomerDto;
import com.kang.redis.main.ConfigMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/17 19:30
 * @Author 梁健康
 * @Version 2.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ConfigMain.class)
public class TestJdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testSelectAll(){

        List<CustomerDto>  customerList = customerDao.selectAll();
        System.out.println(customerList.toString());

    }


}
