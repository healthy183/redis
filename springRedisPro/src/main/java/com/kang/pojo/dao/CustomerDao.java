package com.kang.pojo.dao;

import com.kang.pojo.model.CustomerDto;
import com.kang.redis.dao.aop.RedisCached;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/17 19:35
 * @Author 梁健康
 * @Version 2.0
 */
@Repository
public class CustomerDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RedisCached(value = "allCustomer")
    public List<CustomerDto> selectAll(){
        String sql ="select id,name from customer ";
        final List<CustomerDto> customerList = new ArrayList<CustomerDto>();
        jdbcTemplate.query(sql,new RowCallbackHandler(){
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                CustomerDto customer = new CustomerDto();
                customer.setId(rs.getInt(1));
                customer.setName(rs.getString(2));
                customerList.add(customer);
            }
        });
        return customerList;
    }
}
