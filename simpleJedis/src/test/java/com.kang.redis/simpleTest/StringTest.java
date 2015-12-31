package com.kang.redis.simpleTest;

import lombok.Data;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Title 类名
 * @Description 类描述
 * @Date 2015/11/20 15:41
 * @Author 梁健康
 * @Version 2.0
 */
@Data
class Tom{

     private String Id;
     private String name;

}


public class StringTest {

    private String url = "192.168.88.134";
    private Jedis jedis;

    public StringTest(){
        jedis = new Jedis(url,6379);
        //jedis.auth();验证登陆
    }

    String strKey =  "firstKey";

    @Test
    public  void testObj(){
        Tom tom = new Tom();
        tom.setId("idTom");
        tom.setName("梁健康");
        //jedis.set(tom.getId(), tom);
    }

    @Test
    public  void set(){
        jedis.set(strKey, "梁健康"); //set a new key which is String
        String firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " is " + firstVal);
    }

    @Test
    public void append(){
        jedis.append(strKey, "newMsg");// append String
        String firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " after append is " + firstVal);
    }

    @Test
    public void getrange(){
        String rangeVal = jedis.getrange(strKey, 0, 1);//split String
        System.out.println("range String is " + rangeVal);
    }

    @Test
    public void getSet(){
        String getVal = jedis.getSet(strKey, "newkStr2Val1WFH");//set a new val and return the old val
        System.out.println("getset  return val is "+ getVal);
        String newkStr2Val  = jedis.get(strKey);
        System.out.println("new Str2 key val is "+newkStr2Val);
    }

    @Test
    public void setRange(){
        String oneVal = jedis.get(strKey);
        System.out.println("oneVal is "+ oneVal);
        jedis.setrange(strKey, 2, "newVal"); //MODIFY KEY  start by  index
        oneVal = jedis.get(strKey);
        System.out.println("oneVal is "+ oneVal);
    }

    @Test
    public void strlen(){
        String oneVal = jedis.get(strKey);
        System.out.println("oneVal is "+ oneVal);
        Long len = jedis.strlen(strKey);
        System.out.println("strKey length is "+ len);
    }



    @Test
    public void del(){
        long reuslt =  jedis.del(strKey);  //del val
        System.out.println("del reuslt is "+reuslt);
        String firstVal = jedis.get(strKey);
        System.out.println("the Val of " + strKey + " after delete is " + firstVal);
    }

    String batchKey = "str";

    @Test
    public void mset(){
        jedis.mset(batchKey+"1", "val1", batchKey+"2", "val2"); //Batch save 批量保存
        String firstVal = jedis.get(batchKey + "1");
        System.out.println("the Val of " + strKey + "1" + "  is " + firstVal);
        String firstVal2 = jedis.get(batchKey + "2");
        System.out.println("the Val of " + strKey + "2" + "  is " + firstVal2);
    }


    @Test
    public void mget(){
        List<String> strList = jedis.mget(batchKey + "1", batchKey + "2");//Batch get vals
        for(int i = 0;i<strList.size(); i++){
            System.out.println("the val of "+batchKey+i+"  is "+strList.get(i));
        }
    }

    @Test
    public void setex() throws InterruptedException {

        String timeoutkey = "timeoutKey";
        int alivePlan = 5;
        String result =  jedis.setex(timeoutkey, alivePlan, "redis");//
        System.out.println("save successfully? " + result);

        int sleepTime = 1000;
        Thread.currentThread().sleep(sleepTime);

        Long aliveTime = jedis.ttl(timeoutkey);
        System.out.println("surplus alive time is " + aliveTime);

        String timeoutVal = jedis.get(timeoutkey);
        System.out.println("the val of " +timeoutkey+" is "+timeoutVal);

        Thread.currentThread().sleep((alivePlan * 1000 - sleepTime));//key had timeout

        timeoutVal = jedis.get(timeoutkey);
        System.out.println("the val of " + timeoutkey + " is " + timeoutVal);
    }

    @Test
    public void psetex() throws InterruptedException {
            String timeoutkey = "timeoutKey";
            int alivePlan = 5000;
            String result =  jedis.psetex(timeoutkey, alivePlan, "redis");//
            System.out.println("save successfully? " + result);

            int sleepTime = 1000;
            Thread.currentThread().sleep(sleepTime);

            Long aliveTime = jedis.ttl(timeoutkey);
            System.out.println("surplus alive time is " + aliveTime);

            String timeoutVal = jedis.get(timeoutkey);
            System.out.println("the val of " +timeoutkey+" is "+timeoutVal);

            Thread.currentThread().sleep((alivePlan - sleepTime));//key had timeout

            timeoutVal = jedis.get(timeoutkey);
            System.out.println("the val of " + timeoutkey + " is " + timeoutVal);
    }

    String incrKey = "incrKey";
    @Test
    public void incr(){

        jedis.set(incrKey, "100");
        jedis.incr(incrKey);   //type of integer will be +1;
        String incrVal = jedis.get(incrKey);
        System.out.println("new val is "+incrVal);
    }

    @Test
    public void incrby(){
        jedis.incrBy(incrKey, 10); //type of integer will be +arg1;
        String incrVal = jedis.get(incrKey);
        System.out.println("new val is "+incrVal);
    }

    @Test
    public void incrByFloat(){
        jedis.incrByFloat(incrKey, 10.1);//type of integer will be +arg1;
        String incrVal = jedis.get(incrKey);
        System.out.println("new val is "+incrVal);
    }

    @Test
    public void decr(){
        //jedis.decr(incrKey);//type of integer will be -1,it will throw exception if not integer;
        //String incrVal = jedis.get(incrKey);
        //System.out.println("new val is "+incrVal);
        String decrKey = "decrKey";
        jedis.set(decrKey, "100");
        jedis.decr(decrKey);   //type of integer will be -1;
        String decrKeyVal = jedis.get(decrKey);
        System.out.println("new val is " + decrKeyVal);
    }

/*
    SET key value [EX seconds] [PX milliseconds] [NX|XX]
    EX seconds   -- alive time (seconds)
    PX seconds   -- alive time (milliseconds)
    NX                    --if key not  exists;
    XX                    --if key exists;*/
    @Test
    public void allArgs() throws InterruptedException {
        String key = "allAray";
        int aliveTime = 5;
        jedis.set(key, "allArayVal", "NX", "EX",aliveTime);
        String val = jedis.get(key);
        System.out.println("val is "+val);
        System.out.println("alive time "+jedis.ttl(key));

        Thread.currentThread().sleep(aliveTime * 1000);

        System.out.println(jedis.get(key));
    }



    @Test
    public void setnx(){
        String  oldVal =  jedis.get(strKey);
        System.out.println("the oldVal is " + oldVal);

        boolean exists = jedis.exists(strKey);
        System.out.println("strKey is exists ?"+exists);
        jedis.setnx(strKey, "setnxVal");//save fail if exists
        String  setnxVal =  jedis.get(strKey);
        System.out.println("setnxVal is " + setnxVal);

        String notExistKey = "abc";
        boolean notExist = jedis.exists(notExistKey);
        System.out.println("notExistKey is exists ?" + notExist);
        jedis.setnx(notExistKey, "setnxVal");//save successful if not exists
        setnxVal =  jedis.get(notExistKey);
        System.out.println("setnxVal is " + setnxVal);
    }

    @Test
    public void msetnx(){

        jedis.msetnx("msetNxOne", "oneVal", "msetNxTwo", "twoVal");// set keys as Arrays if all key not exits
        String oneVal = jedis.get("msetNxOne");
        System.out.println("the val of msetNxOne is " + oneVal);
        String twoVal = jedis.get("msetNxTwo");
        System.out.println("the val of msetNxTwo is " + twoVal);

        jedis.msetnx("msetNxOne", "oneVal", "msetNxThree", "threeVal");//save fail
        oneVal = jedis.get("msetNxOne");
        System.out.println("the val of msetNxOne is " + oneVal);
        String threeVal = jedis.get("threeVal");
        System.out.println("the val of threeVal is " + threeVal);
    }




}

