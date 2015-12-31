package com.kang.redis.simpleTest;

import org.junit.Test;

import java.util.*;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/23 10:45
 * @Author 梁健康
 * @Version 2.0
 */
public class HashTest extends ObjectTest {

    private String hasnName = "hasnName";

    @Test
    public void hset() {
        Long isSuccess = jedis.hset(hasnName, "hashKeyABd", "hashVals");
        System.out.println("save success as new key?" + !(isSuccess == 0));
    }

    @Test
    public void hget() {
        String val = jedis.hget(hasnName, "hashKey");
        System.out.println("val is " + val);
    }

    @Test
    public void  hdel(){
        Long isSuccess =  jedis.hdel(hasnName, "hashKeyABd");
        System.out.println("delete key successfully ?" + !(isSuccess == 0));
        String delVal = jedis.hget(hasnName, "hashKeyABd");
        System.out.println("val is " + delVal);
    }

    @Test
    public void hexists(){
        Boolean isExists = jedis.hexists(hasnName, "hashKey");
        System.out.println("hashKey is exist ?"+ isExists);

        isExists = jedis.hexists(hasnName, "hashKeyABd");
        System.out.println("hashKeyABd is exist ?"+ isExists);
    }

    @Test
    public void hgetall(){

        Map<String, String> map =  jedis.hgetAll(hasnName);
        Iterator iter =  map.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            System.out.println(key+":"+value);
        }
    }

    @Test
    public void hincrby(){

        String keyName ="hashNumABC";
        Long isSuccess = jedis.hset(hasnName, keyName, "100");
        System.out.println("save success as new key?" + !(isSuccess == 0));

        jedis.hincrBy(hasnName, keyName, 1);

        String numVal = jedis.hget(hasnName, keyName);
        System.out.println("val is " + numVal);
    }


    @Test
    public void hincrbyfloat(){

        String keyName ="hashNumF";
        Long isSuccess = jedis.hset(hasnName, keyName, "ad"); // ERR hash value is not a valid float if not the float
        System.out.println("save success as new key?" + !(isSuccess == 0));

        jedis.hincrByFloat(hasnName, keyName, 1.1);

        String numVal = jedis.hget(hasnName, keyName);
        System.out.println("val is " + numVal);
    }

    @Test
    public void hkeys(){

        Set<String> keySet = jedis.hkeys(hasnName);//get all keys of set
        for(String s :keySet){
            System.out.println(s);
        }

       /* Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            System.out.println(s);
        }*/
    }

    @Test
    public void hlen(){
        Long hlen = jedis.hlen(hasnName);//get length of hash
        System.out.println(hlen);
    }

    @Test
    public void hmget(){ //get key:value as arrays;
        List<String> stringList = jedis.hmget(hasnName, "hashNum", "hashNumABC", "abc");
        for(String str : stringList){
            System.out.println(str);
        }
    }

    @Test
    public void hmset() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("abc","123");
        map.put("efg", "456");
        jedis.hmset(hasnName, map);

        String val = jedis.hget(hasnName, "abc");
        System.out.println("hmset val is "+val);
    }

    @Test
    public void hsetnx(){
        String key ="setNx";
        jedis.hsetnx(hasnName,key,"nxABC");//-set  value if not exist,or nullity
        String val = jedis.hget(hasnName, key);
        System.out.println("setNx val is " + val);

        jedis.hsetnx(hasnName, key, "nxEFG");
        val = jedis.hget(hasnName, key);
        System.out.println("setNx val is "+ val);
    }

    @Test
    public void hvals(){//get all value
        List<String> list =  jedis.hvals(hasnName);
        for(String str : list){
            System.out.println(str);
        }
    }

}
