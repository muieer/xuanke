package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.service.RedisService;
import com.yhh.xuanke.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Boolean set(String prefix, String key, Object value, Integer expireTime, TimeUnit timeUnit) {

        String realKey = prefix + key;

        return redisUtil.set(realKey, value, expireTime, timeUnit);
    }

    @Override
    public Object get(String prefix, String key) {

        String realKey = prefix + key;

        return redisUtil.get(realKey);
    }

    @Override
    public Boolean setToHash(String key, String hashKey, Object hashValue, Integer expireTime, TimeUnit timeUnit) {

        Map<String, Object> map = new HashMap<>();
        map.put(hashKey, hashValue);
        return redisUtil.hmset(key, map, expireTime, timeUnit);
    }

    @Override
    public Object getFromHash(String key, String hashKey) {
        return redisUtil.hget(key, hashKey);
    }

    @Override
    public void del(String... key) {

        redisUtil.del(key);
    }




}
