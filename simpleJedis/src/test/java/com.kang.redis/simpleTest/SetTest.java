package com.kang.redis.simpleTest;

import org.junit.Test;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/24 10:57
 * @Author 梁健康
 * @Version 2.0
 */
public class SetTest extends ObjectTest {

    private String key ="keySet";



    @Test
    public void sadd(){//add element into set
        jedis.sadd(key,"firstVal");
    }

    @Test
    public void smembers(){ //iteration elements
        Set<String> set = jedis.smembers(key);
        for(String str : set){
            System.out.println(str);
        }
    }

    public void smembers(String key){ //iteration elements
        Set<String> set = jedis.smembers(key);
        for(String str : set){
            System.out.println(str);
        }
    }

    @Test
    public void scard(){
        Long card = jedis.scard(key);
        System.out.println("the card of set is " + card);
    }


    @Test
    public void sdiff(){
        jedis.sadd(key,"diffVal");
        String otherKey = "otherKey";
        jedis.sadd(otherKey,"firstVal");
        jedis.sadd(otherKey,"secondVal");//get  elements of firstSet  which not in  sscondSet (firstSet的差集)
        Set<String> set = jedis.sdiff(key, otherKey);
        itertor(set);
    }

    @Test
    public void sdiffstore(){
        jedis.sadd(key,"diffVal");
        String otherKey = "otherKey";
        String sdiffstore = "sdiffstore";
        //save all the  difference  elements into destSet of   firstSet  (firstSet的差集保存进destSet )
        jedis.sdiffstore(sdiffstore, key, otherKey);
        //smembers(key);
        //smembers(otherKey);
        smembers(sdiffstore);
    }


    @Test
    public void sinter(){
        String sdiffstore = "sdiffstore";
        Set<String> set = jedis.sinter(key, sdiffstore);
        smembers(key);
        System.out.println();

        smembers(sdiffstore);
        System.out.println();

        itertor(set);
    }

    @Test
    public void sinterstore(){ //save the union set  between  firstSet   and secondSet
        String sinterstore = "sinterstore";
        String sdiffstore = "sdiffstore";
        jedis.sinterstore(sinterstore, key, sdiffstore);
        smembers(sinterstore);
    }


    @Test
    public void sismember(){//judge the member is  belong of set
        Boolean b = jedis.sismember(key,"diffVal");
        System.out.println(b);
        b = jedis.sismember(key,"diffVal2");
        System.out.println(b);
    }

    @Test
    public void smove(){
        String moveSet = "moveSet";
        smembers(key);
        System.out.println("===========line for moveSet=============");
        jedis.smove(key, moveSet, "diffVal");
        smembers(moveSet);
    }

    @Test
    public void srandmember(){
        jedis.sadd(key, "moveVal");
        smembers(key);
        System.out.println("===========line for srandmember=============");
        List<String> list =  jedis.srandmember(key, 1);

        for(String s : list){
            System.out.println(s);
        }
    }

    @Test
    public void srem(){
        String remove = "moveVal";
        jedis.sadd(key, remove);

        smembers(key);

        System.out.println("===========line for srem=============");
        jedis.srem(key, remove);
        smembers(key);
    }

    @Test
    public void sunion(){
        String moveSet = "moveSet";
        smembers(key);
        System.out.println();
        smembers(moveSet);
        System.out.println();

        Set<String> set = jedis.sunion(key, moveSet);

        itertor(set);
    }

    @Test
    public void sunionStore(){
        String moveSet = "moveSet";
        smembers(key);
        System.out.println();
        smembers(moveSet);
        System.out.println();

        String sunionStoreKey = "sunionStore";
        jedis.sunionstore(sunionStoreKey, key, moveSet);

        smembers(sunionStoreKey);
    }

    @Test
    public void zscan(){

        //jedis.zscan
        ScanResult<Tuple> result = jedis.zscan(key, 1);
        String cursor = result.getStringCursor();
         System.out.println("cursor :"+cursor);

       /* List<Tuple> resultList = result.getResult();

        for(Tuple str : resultList){
            System.out.println(str.getElement()+":"+str.getScore());
        }*/
    }

}
