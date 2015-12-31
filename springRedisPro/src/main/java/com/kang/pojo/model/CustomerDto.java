package com.kang.pojo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/12/17 19:32
 * @Author 梁健康
 * @Version 2.0
 */
@Data
@NoArgsConstructor
@ToString
public class CustomerDto implements  java.io.Serializable {

    private Integer id;
    private Integer version;
    private String name;
    private Integer credit;

}
