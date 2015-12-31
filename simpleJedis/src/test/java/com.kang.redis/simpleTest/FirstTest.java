package com.kang.redis.simpleTest;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/19 19:22
 * @Author 梁健康
 * @Version 2.0
 */
@Data
//@Slf4j
public class FirstTest {

    private String url = "192.168.88.134";
    private Jedis jedis;
    //new Jedis();
    public FirstTest(){
        jedis = new Jedis(url,6379);
        //jedis.auth();验证登陆
    }


    public void testString() throws InterruptedException {

        String strKey =  "firstKey";

        jedis.set(strKey, "firstVal123"); //set a new key which is String
        String firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " is " + firstVal);

        jedis.append(strKey, "newMsg");// append String
        firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " after append is " + firstVal);

        jedis.del(strKey);//del val
        firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " after delete is " + firstVal);

        String batchKey = "str";
        jedis.mset(batchKey+"1", "val1", batchKey+"2", "val2"); //Batch save 批量保存
        firstVal = jedis.get(batchKey+"1");
        System.out.println("the Val of " + strKey + "1" + "  is " + firstVal);

        firstVal = jedis.get(batchKey+"2");
        System.out.println("the Val of "+strKey+"2"+"  is "+firstVal);

        String rangeVal = jedis.getrange(batchKey + "1", 0, 1);//split String
        System.out.println("range String is " + rangeVal);

        String newkStr2  = "newkStr2";
        String getVal = jedis.getSet(batchKey + "1", "newkStr2Val1WFH");//set a new val and return the old val
        System.out.println("getset  return val is "+ getVal);
        String newkStr2Val  = jedis.get(batchKey + "1");
        System.out.println("new Str2 key val is "+newkStr2Val);


        List<String> strList = jedis.mget(batchKey+"1",batchKey+"2");//Batch get vals
        for(int i = 0;i<strList.size(); i++){
            System.out.println("the val of "+batchKey+i+"  is "+strList.get(i));
        }

       String timeoutkey = "timeoutKey";
       int alivePlan = 20;
       String result =  jedis.setex(timeoutkey, alivePlan, "redis");//
        System.out.println("save successfully? " + result);

        int sleepTime = 1000;
        Thread.currentThread().sleep(sleepTime);

        Long aliveTime = jedis.ttl(timeoutkey);
        System.out.println("surplus alive time is " + aliveTime);

        String timeoutVal = jedis.get(timeoutkey);
        System.out.println("the val of " +timeoutkey+" is "+timeoutVal);

        Thread.currentThread().sleep((alivePlan*1000 - sleepTime));

        timeoutVal = jedis.get(timeoutkey);
        System.out.println("the val of " + timeoutkey + " is " + timeoutVal);

    }


    public static void main(String[] args) throws InterruptedException {

        FirstTest firstTest = new FirstTest();
        firstTest.testString();

    }



}
