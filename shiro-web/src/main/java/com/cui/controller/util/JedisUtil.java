package com.cui.controller.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: cuijq
 */
@Component
public class JedisUtil {
    @Resource
    private JedisPool jedisPool;

    public Jedis getResource(){
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try {
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        System.out.println("【从reids中取session】");
        Jedis jedis = getResource();
        try {
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void expire(byte[] key ,int time) {
        Jedis jedis = getResource();
        try {
            jedis.expire(key,time);
        }finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((shiro_session_prefix+"*").getBytes());
        }finally {
            jedis.close();
        }

    }
}
