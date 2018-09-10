package com.netopstec.extensible.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 将常用的Redis操作汇聚成工具类
 * @author zhenye 2018/9/7
 */
@Configuration
@Slf4j
public class RedisUtil {

    /**
     * StringRedisTemplate继承RedisTemplate<String, String>，两种的区别仅仅是序列化方式不一样。
     * 这里选用StringRedisTemplate，能够避免乱码问题。
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.hasKey(String key) ---> ",e);
            return false;
        }
    }

    /**
     * 指定key的过期时间为time，单位是秒
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean setExpire(String key, long time){
        try {
            if(time > 0){
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.setExpire(String key, long time) ---> ",e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永不过期
     */
    public long getExpire(String key){
        return stringRedisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 删除key
     * @param key 可以传一个值 或多个
     */
    public void del(String... key){
        if(key != null && key.length > 0){
            if(key.length == 1){
                stringRedisTemplate.delete(key[0]);
            }else{
                stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String类型的方法=============================
    //存储格式为： key = value
    /**
     * 获取指定键的值
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置指定键的值，如果存在就是更新操作
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.set(String key,String value) ---> ",e);
            return false;
        }

    }

    /**
     * 设置指定键的值，同时设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,String value,long time){
        try {
            if(time > 0){
                stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.set(String key,String value,long time) ---> ",e);
            return false;
        }
    }

    /**
     * 改变键的排序权重
     * @param key 键
     * @param delta 权重数
     * @return
     */
    public long incrBy(String key, long delta){
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    //============================Hash类型的方法=============================
    //存储格式为： key = {field1: value1,field2: value2}
    // Redis中的Hash结构可以类比数据库（key是表名，field是列表）
    /**
     * HashGet
     * @param key 键
     * @param field 项
     * @return 该键某项的值value
     */
    public Object hget(String key,String field){
        return stringRedisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取该键的所有项值对{field1: value1,field2: value2}
     * @param key 键
     * @return 所有项值对
     */
    public Map<Object,Object> hmget(String key){
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 以键值对的方式把map信息保存在键key中
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.hmset(String key, Map<String,Object> map) ---> ",e);
            return false;
        }
    }

    /**
     * 以键值对的方式把map信息保存在键key中，同时设置该键的过期时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String,Object> map, long time){
        try {
            stringRedisTemplate.opsForHash().putAll(key, map);
            if(time > 0){
                setExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.hmset(String key, Map<String,Object> map, long time) ---> ",e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param field 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key,String field,String value) {
        try {
            stringRedisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.hset(String key,String field,Object value) ---> ",e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param field 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key,String field,String value,long time) {
        try {
            stringRedisTemplate.opsForHash().put(key, field, value);
            if(time>0){
                setExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.hset(String key,String field,String value,long time) ---> ",e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param field 项 不能为null
     */
    public void hdel(String key, String field){
        stringRedisTemplate.opsForHash().delete(key,field);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param field 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String field){
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param field 项
     * @param by 排序权重改变值
     * @return
     */
    public double hincr(String key, String field, double by){
        return stringRedisTemplate.opsForHash().increment(key, field, by);
    }

    //============================Set类型的操作=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<String> sGet(String key){
        try {
            return stringRedisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.sGet(String key) ---> ",e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key,String value){
        try {
            return stringRedisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.sHasKey(String key,String value) ---> ",e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, String...values) {
        try {
            return stringRedisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.sSet(String key, String...values) ---> ",e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key,long time,String...values) {
        try {
            Long count = stringRedisTemplate.opsForSet().add(key, values);
            if(time>0) {
                setExpire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.sSetAndTime(String key,long time,String...values) ---> ",e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key){
        try {
            return stringRedisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.sGetSetSize(String key) ---> ",e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object ...values) {
        try {
            Long count = stringRedisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.setRemove(String key, Object ...values) ---> ",e);
            return 0;
        }
    }

    //============================List类型的操作=============================
    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public List<String> lGet(String key, long start, long end){
        try {
            return stringRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lGet(String key, long start, long end) ---> ",e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long lGetListSize(String key){
        try {
            return stringRedisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lGetListSize(String key) ---> ",e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key,long index){
        try {
            return stringRedisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lGetIndex(String key,long index) ---> ",e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, String value) {
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lSet(String key, String value) ---> ",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean lSet(String key, String value, long time) {
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                setExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lSet(String key, String value, long time) ---> ",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<String> value) {
        try {
            stringRedisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lSet(String key, List<String> value) ---> ",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<String> value, long time) {
        try {
            stringRedisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                setExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lSet(String key, List<String> value, long time) ---> ",e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index,String value) {
        try {
            stringRedisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lUpdateIndex(String key, long index,String value) ---> ",e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key,long count,String value) {
        try {
            return stringRedisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("error occurred in RedisUtil.lRemove(String key,long count,String value) ---> ",e);
            return 0L;
        }
    }
}

