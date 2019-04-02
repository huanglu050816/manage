/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.zwh.manage.util;

import com.zwh.manage.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.PostConstruct;

/**
 * redis工具类
 */
@Component
public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class.getName());

    @Autowired
    private RedisConfig redisConfig;
    private static RedisConfig localRedisConfig;
    public static JedisPool jedisPool;

    @PostConstruct
    public void init() {
        localRedisConfig = this.redisConfig;
        // 初始化redis连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(localRedisConfig.getMaxTotal());
        config.setMaxIdle(localRedisConfig.getMaxIdle());
        jedisPool = new JedisPool(config, localRedisConfig.getHost(), localRedisConfig.getPort(), 2000, localRedisConfig.getPassword());
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (JedisConnectionException e) {
            log.error("Put fail:", e.getMessage());
            return false;
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    /**
     * 不存在时设置值
     * @param key
     * @param value
     * @return
     */
    public static boolean setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long result = jedis.setnx(key, value);
            return result != 0L ? true : false;
        } catch (JedisConnectionException e) {
            log.error("Set fail {}", e.getMessage());
            return false;
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            log.warn("Get fail {}", e.getMessage());
            return null;
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    /**
     * 删除值
     * @param key
     * @return
     */
    public static boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (JedisConnectionException e) {
            log.error("Del fail {}", e.getMessage());
            return false;
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    /**
     * redis自增
     * @param key
     */
    public static Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } catch (JedisConnectionException e) {
            log.error("incr fail {}", e);
            return null;
        } finally {
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
    }

}
