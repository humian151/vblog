package com.zwq.blog.utils;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.session.Session;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 封装redis工具类
 * @author zwq
 * @date 2018/12/5.
 */
@Service
public final class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * 通过前缀和键获取对象
     * @param keyPrefix 前缀
     * @param key 键
     * @param clazz 对象类型
     * @param <T> 返回对象
     * @return
     */
    public <T> T get(String keyPrefix,String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            String result = jedis.get(realKey);
            return stringToBean(result,clazz);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 通过前缀和键获取对象
     * @param key 键
     * @param
     * @return
     */
    public Object getObject(String key ){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            byte[] k = key.getBytes();
            byte[] bytes = jedis.get(k);
            return  SerializationUtils.deserialize(bytes);

        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * @param keyPrefix 键前缀
     * @param key 键
     * @param expireSeconds 有效期 数值为0或者负数则永久有效
     * @param value 存储对象
     * @param <T>
     * @return
     */
    public <T> boolean set(String keyPrefix,String key,int expireSeconds,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            String str = beanToString(value);
            if(StringUtils.isEmpty(str)){
                return false;
            }
            if (expireSeconds <=0){
                jedis.set(realKey,str);
            }else {
                jedis.setex(realKey,expireSeconds,str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * @param key 键
     * @param expireSeconds 有效期 数值为0或者负数则永久有效
     * @param value 存储对象
     * @param <T>
     * @return
     */
    public <T> boolean setObject(String key,int expireSeconds,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            byte[] k = key.getBytes();
            byte[] serialize = SerializationUtils.serialize(value);
            if (expireSeconds <=0){
                jedis.set(k,serialize);
            }else {
               jedis.setex(k,expireSeconds,serialize);
            }
            Session session = (Session) SerializationUtils.deserialize(jedis.get(k));
            return true;
        }finally {
            returnToPool(jedis);
        }
    }



    /**
     * 根据key删除对象
     * @param keyPrefix
     * @param key
     * @return
     */
    public boolean delete(String keyPrefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.del(realKey) > 0;
        }finally {
            returnToPool(jedis);
        }
    }
    /**
     * 根据key删除对象
     * @param key
     * @return
     */
    public boolean deleteObject(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            byte[] serialize = key.getBytes();
            return jedis.del(serialize) > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 移除指定的键。如果给定的密钥不存在，则不对该密钥执行任何操作。该命令返回删除的键数。
     * @param keys
     * @return
     */
    public Long deleteBatch(String ... keys){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 返回符合规则的key
     * @param pattern 正则表达式
     * @return
     */
    public Set<String> keys(String pattern){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.keys(pattern);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断键是否存在
     * @param keyPrefix
     * @param key
     * @return
     */
    public boolean exists(String keyPrefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }
    /**
     * 判断键是否存在
     * @param key
     * @return
     */
    public boolean existsObject(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * @param keyPrefix
     * @param key
     * @return
     */
    public Long incr(String keyPrefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }
    /**
     * 减少值
     * @param keyPrefix
     * @param key
     * @return
     */
    public Long decr(String keyPrefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = keyPrefix + key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     *  fastjson 对象转换成string
     * @param value
     * @param <T>
     * @return
     */
    public static  <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * String 转换成对象 fastjson
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static  <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 关闭资源
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if(jedis != null) {
            jedis.close();
        }
    }
}
