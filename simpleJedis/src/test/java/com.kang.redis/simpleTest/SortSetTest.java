package com.kang.redis.simpleTest;

import org.junit.Test;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/24 13:57
 * @Author 梁健康
 * @Version 2.0
 */
public class SortSetTest extends ObjectTest {

    String key = "sortSetKey";

    @Test
    public void  zadd(){
        jedis.zadd(key,1,"firstVal");
        jedis.zadd(key, 5, "secondVal");
    }

    @Test
    public void zrange(){//iteration element order by index order by score asc;
        Set<String> set = jedis.zrange(key, 0, 4);
        itertor(set);
    }

    @Test
    public void zrangeWithScores(){
        Set<Tuple> tupleSet =  jedis.zrangeWithScores(key, 0, -1);
        for(Tuple tuple : tupleSet){
            //System.out.println(tuple.getBinaryElement());
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }
    }


    public void zrangeWithScores(String key){
        Set<Tuple> tupleSet =  jedis.zrangeWithScores(key, 0, -1);
        for(Tuple tuple : tupleSet){
            //System.out.println(tuple.getBinaryElement());
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }
    }

    @Test
    public void zrevrange(){
        jedis.zadd(key,1,"firstVal");
        jedis.zadd(key,10,"tenthVal");//iteration order by key desc
        Set<String> set =  jedis.zrevrange(key, 0, -1);
        itertor(set);
    }

    @Test
    public void zrevrangeWithScores(){
        Set<Tuple> tupleSet =  jedis.zrevrangeWithScores(key, 0, -1);
        for(Tuple tuple : tupleSet){
            //System.out.println(tuple.getBinaryElement());
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }
    }


    @Test
    public void zcard(){//get count
        Long zcard = jedis.zcard(key);
        System.out.println("zcard is " + zcard);
    }



    @Test
    public void zcount(){  //get count  where score between 0 and 10
        Long zcount = jedis.zcount(key, 0, 10);
        System.out.println("zcount is " + zcount);
    }

    @Test
    public void zincrby(){
        jedis.zincrby(key, 20, "firstVal");// add score
        Set<String> set = jedis.zrange(key, 0, -1);
        itertor(set);
        System.out.println();
    }

    @Test
    public void  zrangeByScore(){//get element by score,  -inf(min) +inf(max)
        Set<String> set = jedis.zrangeByScore(key, Double.MIN_VALUE, Double.MAX_VALUE);
        itertor(set);
    }

    public void  zrangeByScore(String key){
        Set<String> set = jedis.zrangeByScore(key, Double.MIN_VALUE, Double.MAX_VALUE);
        itertor(set);
    }

    @Test
    public void zrank(){//get rank(排名) of set
        Long  rank = jedis.zrank(key, "secondVal");
        System.out.println("the rank of secondVal in " + key + " is " + rank);
    }

    @Test
    public void zrevrank(){
        Long  rank = jedis.zrevrank(key, "tenthVal");
        System.out.println("the rank of tenthVal  order by score desc in " + key + " is " + rank);
    }

    @Test
    public void zrem(){
        jedis.zadd(key,10,"zremVal");
        jedis.zadd(key,11,"zremVal2");
        zrangeByScore(key);
        System.out.println();
        System.out.println("======line for zrem==========");
        jedis.zrem(key, "zremVal", "zremVal2");//remove elements
        zrangeByScore(key);
        System.out.println();
    }

    @Test
    public void zremRangByRank(){

        jedis.zadd(key, 10, "zremVal");
        jedis.zadd(key, 11, "zremVal2");
        zrangeByScore(key);

        jedis.zremrangeByRank(key, 2, 3);//remove by range
        System.out.println();
        zrangeByScore(key);
    }

    @Test
    public void zremrangeByScore(){

        jedis.zadd(key,10,"zremVal");
        jedis.zadd(key,11,"zremVal2");
        zrangeByScore(key);

        jedis.zremrangeByScore(key, 10, 11);// remove by score

        System.out.println();
        zrangeByScore(key);
    }

    @Test
    public void zscore(){
        Double val =  jedis.zscore(key, "firstVal");
        System.out.println("the score of firstVal is " + val);
    }

    @Test
    public void zunionstore(){
        String otherKey = "abcKey";

       /*  jedis.zadd(otherKey,10,"zremVal");
        jedis.zadd(otherKey, 11, "zremVal2");

        zrangeWithScores(key);
        System.out.println();

        zrangeWithScores(otherKey);
        System.out.println();*/

        String zunionstoreSet = "zunionstoreSet";
        jedis.zunionstore(zunionstoreSet, key, otherKey);//获取并集,并且score相加

        zrangeWithScores(zunionstoreSet);
    }

    @Test
    public void zinterstore(){

        String otherKey = " zinterKey";
        jedis.zadd(otherKey,10,"zremVal");
        jedis.zadd(otherKey, 11, "zremVal2");

        zrangeWithScores(key);
        System.out.println();

        zrangeWithScores(otherKey);
        System.out.println();

        String zinterstore = "zinterstore";
        jedis.zinterstore(zinterstore, otherKey, key);//获取交集,并且score相加
        zrangeWithScores(zinterstore);

    }

    @Test
    public void zscan(){
        ScanResult<Tuple> result = jedis.zscan(key, 0);
        int cursor = result.getCursor();
        System.out.println(cursor);
        List<Tuple>  list = result.getResult();
        for(Tuple tuple : list){
            System.out.println(tuple.getElement()+":"+tuple.getScore());
        }
    }

}
