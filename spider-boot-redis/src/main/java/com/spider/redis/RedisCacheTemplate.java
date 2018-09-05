package com.spider.redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spider.util.Strings;
import redis.clients.jedis.JedisCluster;

import java.util.Collection;
import java.util.concurrent.locks.Lock;

/**
 * redis的缓存模板类
 *
 * @author liuzhongkai
 */

public class RedisCacheTemplate {

    private final LockFactory lockFactory;

    private final JedisCluster jedisCluster;

    private final ObjectMapper objectMapper;

    public RedisCacheTemplate(JedisCluster jedisCluster) {
        this(jedisCluster, new ObjectMapper());
    }

    public RedisCacheTemplate(JedisCluster jedisCluster, ObjectMapper objectMapper) {
        this(jedisCluster, new JVMLockFactory(), objectMapper);
    }

    public RedisCacheTemplate(JedisCluster jedisCluster, LockFactory lockFactory) {
        this(jedisCluster, lockFactory, new ObjectMapper());
    }

    public RedisCacheTemplate(JedisCluster jedisCluster, LockFactory lockFactory, ObjectMapper objectMapper) {
        this.lockFactory = lockFactory;
        this.jedisCluster = jedisCluster;
        this.objectMapper = objectMapper;
    }

    /**
     * redis set
     * 在查询数据的时首先先查询redis缓存中是否有缓存，如果没有则会通过LoadCallBack的callBack方法获取数据库的数据在写入redis中
     * 由{@link LoadCallBack}描绘调用数据库查询具体操作实现
     *
     * @param key                 存入redis的标识键
     * @param effectiveSecondTime redis缓存的过期时间(0表示无过期时间)单位秒
     * @param listType            集合类型（不是集合则为空）
     * @param clazz               json反序列化的类型
     * @param callBack            描绘调用数据库查询具体操作的载入对象
     * @return {@link LoadCallBack}的泛型
     */
    @SuppressWarnings("unchecked")
    public <T> T executeSet(String key, int effectiveSecondTime, Class<? extends Collection> listType, Class<?> clazz, LoadCallBack<T> callBack) throws Exception {
        String jsonStr = null;
        jsonStr = jedisCluster.get(key);
        if (Strings.isBlankAndNullString(jsonStr)) {
            Lock lock = lockFactory.getLock(key);
            lock.lock();
            try {
                jsonStr = jedisCluster.get(key);
                if (Strings.isBlankAndNullString(jsonStr)) {
                    T value = callBack.callBack();
                    if (value != null) {
                        if (effectiveSecondTime == 0)
                            jedisCluster.set(key, objectMapper.writeValueAsString(value));
                        else
                            jedisCluster.setex(key, effectiveSecondTime, objectMapper.writeValueAsString(value));
                    }
                    return value;
                } else
                    jedisCluster.expire(key, effectiveSecondTime);

            } finally {
                lock.unlock();
            }
            if (listType == null) {
                return (T) objectMapper.readValue(jsonStr, clazz);
            } else {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(listType, clazz);
                return (T) objectMapper.readValue(jsonStr, javaType);
            }
        } else
            jedisCluster.expire(key, effectiveSecondTime);

        if (listType == null) {
            return (T) objectMapper.readValue(jsonStr, clazz);
        } else {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(listType, clazz);
            return (T) objectMapper.readValue(jsonStr, javaType);
        }
    }


    /**
     * redis hset
     * 在查询数据的时首先先查询redis缓存中是否有缓存，如果没有则会通过LoadCallBack的callBack方法获取数据库的数据在写入redis中
     * 由{@link LoadCallBack}描绘调用数据库查询具体操作实现
     *
     * @param key                 存入redis的标识键
     * @param territory           (hash redis的二级key)
     * @param effectiveSecondTime redis缓存的过期时间(0表示无过期时间)单位秒
     * @param listType            集合类型（不是集合则为空）
     * @param clazz               json反序列化的类型
     * @param callBack            描绘调用数据库查询具体操作的载入对象
     * @return {@link LoadCallBack}的泛型
     */
    @SuppressWarnings("unchecked")
    public <T> T executeHSet(String key, String territory, int effectiveSecondTime, Class<? extends Collection> listType, Class<?> clazz, LoadCallBack<T> callBack) throws Exception {
        String jsonStr = null;
        jsonStr = jedisCluster.hget(key, territory);
        if (Strings.isBlankAndNullString(jsonStr)) {
            Lock lock = lockFactory.getLock(key);
            lock.lock();
            try {
                jsonStr = jedisCluster.hget(key, territory);
                if (Strings.isBlankAndNullString(jsonStr)) {
                    T value = callBack.callBack();
                    if (value != null) {
                        jedisCluster.hset(key, territory, objectMapper.writeValueAsString(value));
                        if (effectiveSecondTime != 0)
                            jedisCluster.expire(key, effectiveSecondTime);

                    }
                    return value;
                } else
                    jedisCluster.expire(key, effectiveSecondTime);

            } finally {
                lock.unlock();
            }
            if (listType == null) {
                return (T) objectMapper.readValue(jsonStr, clazz);
            } else {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(listType, clazz);
                return (T) objectMapper.readValue(jsonStr, javaType);
            }
        } else
            jedisCluster.expire(key, effectiveSecondTime);

        if (listType == null) {
            return (T) objectMapper.readValue(jsonStr, clazz);
        } else {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(listType, clazz);
            return (T) objectMapper.readValue(jsonStr, javaType);
        }
    }
}
