package com.kang.redis.simpleTest;

import org.junit.Test;
import redis.clients.jedis.Client;

import java.util.List;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/23 16:08
 * @Author 梁健康
 * @Version 2.0
 */
public class ListTest extends ObjectTest {

    String key = "listKey";

    @Test
   public void lpush(){
        jedis.lpush(key,"val");
        jedis.lpush(key,"vaL2");
   }

    @Test
    public void lrange(){ //iterator list
        List<String> list = jedis.lrange(key, 0, -1);
        for(String str : list){
            System.out.println(str);
        }
    }

    public void lrange(String key){ //iterator list
        List<String> list = jedis.lrange(key, 0, -1);
        for(String str : list){
            System.out.println(str);
        }
    }

    @Test
    public void bplop(){

        List<String> list = jedis.blpop(10, key);//remove the last element, if list null ,
        // block it  arg0  seconds ,just wait for somebody add elements;
        if(list != null){//attention,if list is null after blpop,it will throw nullpointexception!
            for(String str : list){
                System.out.println(str);
            }
        }
    }

    @Test
    public void brpoplpush(){
        //PUSH the first element of list(key)  to aother list(brList ),
       // if   key is null, block the client arg0 seconds util timeout or sby add elements;
        jedis.brpoplpush(key,"brList",20);
        List<String> list = jedis.lrange("brList", 0, -1);
        for(String str : list){
            System.out.println(str);
        }
    }

    @Test
    public void lindex(){
        String value = jedis.lindex(key, 0);//get elements by index
        System.out.println("get 1th of list is "+value);
    }

    @Test
    public void linsert(){
        lrange(key);  //add   element before "arg0",if arg0 not exist, the tx will not commit;
        //jedis.linsert(key, Client.LIST_POSITION.BEFORE, "vaL2", "valInsert");
        lrange(key);
        System.out.println("---------fail line---------");
        jedis.linsert(key, Client.LIST_POSITION.BEFORE, "vaLabc", "valInsert");
        lrange(key);
    }


    @Test
    public void llen(){
       long llen = jedis.llen(key);
        System.out.println("the length of " + key + " is " + llen);
    }

    @Test
    public void lpop(){
         String val = jedis.lpop(key);//remove first elements, and return to client;
         System.out.println("remove the first element:"+val);
        System.out.println("iterator elements");
        lrange(key);
    }

    @Test
    public void lpushx(){
        lrange(key);
        String newKey ="newKey";
        jedis.lpushx(newKey, "valInsert");//element  can not  add list if exist in  other lists;
        lrange(newKey);
    }

    @Test
    public void lset(){//  replace  element by index
        jedis.lset(key, 0, "setVal");//throw exception if ERR index out of range
        lrange(key);
    }

    @Test
    public void ltrim(){
        lrange(key); //trim the list by index
        System.out.println("===============line for trim=========");
        jedis.ltrim(key, 0, 0);
        lrange(key);
    }

    @Test
    public void  rpop(){
        lrange(key);
        jedis.lpush(key, "rpopVal");
        System.out.println("=======line for rpop=====");
        jedis.rpop(key);//remove the last element
        lrange(key);
    }

    @Test
    public  void brpop(){
        lrange(key);
        System.out.println("=======line for brpop=====");
        jedis.brpop(20, key);//-remove the last  element ,if the list is empty,it will block it arg0 seconds or somebody add new elements;
        lrange(key);
    }

    @Test
    public void rpoplpush(){ // move the first element to  otherFirstList
        //jedis.lpush(key, "val");
        //jedis.lpush(key, "vaL2");
        lrange(key);
        System.out.println("========line for rpoplpush==========");
        String newKey = "rpoplpushKey";
        jedis.rpoplpush(key, newKey);
        lrange(newKey);
    }

    @Test
    public void rpush(){
        jedis.rpush(key, "rA", "rB"); //add for batch
        lrange(key);
    }

    @Test
    public void rpushx(){
        jedis.rpushx(key, "rpushxVal"); //add if list exist
        lrange(key);

        System.out.println("========line for rpushx==========");
        String rpushxKey = "rpushxKey";
        jedis.rpushx(rpushxKey, "rpushxVal");
        lrange(rpushxKey);
    }




}
