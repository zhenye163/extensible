package com.netopstec.extensible;

import com.netopstec.extensible.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhenye 2018/9/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 进行Redis的Key以及String类型数据操作的测试如下：
     */
    @Test
    public void redisKeyTest() throws Exception {
        log.info("开始进行Redis工具类RedisUtil的Key以及String类型数据操作相关方法的测试如下：");
        String key1 = "person1";
        boolean hasKey = redisUtil.hasKey(key1);
        log.info("检测redis中是否有Key[{}],检测结果为:{}.",key1,hasKey);
        if (hasKey){
            String key1Value = (String) redisUtil.get(key1);
            log.info("Key[{}]的值为:",key1Value);
        }
        String value1 = "zhenye1";
        redisUtil.set(key1,value1);
        hasKey = redisUtil.hasKey(key1);
        log.info("设置了Key[{}]的值为:{},此时再判断是否存在Key值结果为:{}",key1,value1,hasKey);
        Long toBeExpiredTime0 = redisUtil.getExpire(key1);
        log.info("不设置Key[{}]的过期时间，即表示永不过期---其过期时间默认值为{}",key1,toBeExpiredTime0);
        Long time1 = 10L;
        redisUtil.setExpire(key1,time1);
        log.info("设置了Key[{}]的过期时间为:{}",key1,time1);
        Long toBeExpiredTime1 = redisUtil.getExpire(key1);
        log.info("此时Key[{}]的剩余过期时间为:{}",key1,toBeExpiredTime1);
        Thread.sleep(5000L);
        Long toBeExpiredTime2 = redisUtil.getExpire(key1);
        log.info("此时Key[{}]的剩余过期时间为:{}",key1,toBeExpiredTime2);
        Thread.sleep(6000L);
        Long toBeExpiredTime3 = redisUtil.getExpire(key1);
        log.info("此时Key[{}]的剩余过期时间为:{},此时该键已经过期",key1,toBeExpiredTime3);
        String key2 = "person2";
        String value2 = "zhenye2";
        String key3 = "person3";
        String value3 = "zhenye3";
        redisUtil.set(key2,value2);
        redisUtil.set(key3,value3);
        log.info("设置Key2[{}]和Key3[{}]后，判断其是否存在:{}",key2,key3,(redisUtil.hasKey(key2) || redisUtil.hasKey(key3)));
        redisUtil.del(key2,key3);
        log.info("删除了Key2[{}]和Key3[{}]后,判断其是否存在:{}",key2,key3,(redisUtil.hasKey(key2) || redisUtil.hasKey(key3)));
    }

    /**
     * 进行Redis的Hash类型数据操作的测试如下：
     */
    @Test
    public void redisHashTest(){
        log.info("开始进行Redis工具类RedisUtil的Hash类型数据操作相关方法的测试如下：");
        log.info("下面的操作是往表[user]存入相应的信息");
        redisUtil.hset("user","userId1","{userId:1,name:zhenye1,age:25}");
        redisUtil.hset("user","userId2","{userId:2,name:zhenye2,age:26}");
        redisUtil.hset("user","userId3","{userId:3,name:zhenye3,age:27}");
        Map<Object,Object> map = redisUtil.hmget("user");
        log.info("获取表[user]的全表数据如下：{}",map);
        boolean hasField = redisUtil.hHasKey("user","userId3");
        log.info("判断表[user]是否存在Field[user3],其结果为:{}",hasField);
        String userId3Info = (String) redisUtil.hget("user","userId3");
        log.info("获取表[user]中Field[userId3]的值如下：{}",userId3Info);
        redisUtil.hset("user","userId3","{userId:4,name:zhenye4,age:25}");
        userId3Info = (String) redisUtil.hget("user","userId3");
        log.info("测试更改表[user]中Field[userId3]的值成功后，其值如下：{}",userId3Info);
        redisUtil.hdel("user","user3");
        hasField = redisUtil.hHasKey("user","user3");
        log.info("测试表[user]已经删除Field[user3],判断其是否还存在的结果为:{}",hasField);
    }

    /**
     * 进行Redis的Hash类型数据操作的测试如下：
     */
    @Test
    public void redisSetTest(){
        log.info("开始进行Redis工具类RedisUtil的Set类型数据操作相关方法的测试如下：");
        log.info("下面的操作是往Set[number]中批量插入多条数据");
        redisUtil.sSet("number","1","2","3");
        Set<String> numbers = redisUtil.sGet("number");
        log.info("测试获取Set[number]中的所有数据，其结果为:{}",numbers);
        boolean hasNumber = redisUtil.sHasKey("number","2");
        log.info("测试Set[number]中是否有'2'，结果为:{}",hasNumber);
        Long setSize = redisUtil.sGetSetSize("number");
        log.info("测试获取Set[number]的长度为:{}",setSize);
        redisUtil.setRemove("number","2","3","4");
        numbers = redisUtil.sGet("number");
        log.info("测试批量删除Set[number]中的多条数据后，其数据为:{}",numbers);
    }

    /**
     * 进行Redis的List类型数据操作的测试如下：
     */
    @Test
    public void redisListTest() {
        log.info("开始进行Redis工具类RedisUtil的List类型数据操作相关方法的测试如下：");
        log.info("下面的操作是往List[color]中批量插入多条数据");
        redisUtil.lSet("color", Arrays.asList("red","blue","green","blue","green","blue"));
        String indexValue = (String) redisUtil.lGetIndex("color",2L);
        log.info("测试获取List[color]中指定下标[2L]的值：{}",indexValue);
        Long listSize = redisUtil.lGetListSize("color");
        log.info("测试获取List[color]的长度：{}",listSize);
        List<String> listValue = redisUtil.lGet("color",1L,10L);
        log.info("测试获取List[color]中指定下标范围start[1L]-end[10L]的值：{}",listValue);
        redisUtil.lRemove("color",2L,"blue");
        listValue = redisUtil.lGet("color",1L,10L);
        log.info("测试删除List[color]中2个'blue'后List的值为：{}",listValue);
        listValue = redisUtil.lGet("color",1L,10L);
        log.info("测试删除List[color]中2个'blue'后List的值为：{}",listValue);
    }
}
