package com.yhh.xuanke.redis;

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
    public void delFromHash(String key, String hashKey) {

        redisUtil.hdel(key, hashKey);
    }

    @Override
    public Boolean hHasKey(String key, String hashKey) {

        return redisUtil.hHasKey(key, hashKey);
    }

    @Override
    public void del(String... key) {

        redisUtil.del(key);
    }

    @Override
    public Long hdecr(String key, String item, long by) {

        return redisUtil.hdecr(key, item, by);
    }

    @Override
    public Boolean hasKey(String key) {

        return redisUtil.hasKey(key);
    }

    @Override
    public void setToSet(String key, long time, TimeUnit timeUnit, Object value) {

        redisUtil.sSetAndTime(key, time, timeUnit, value);
    }

    @Override
    public Boolean sHasKey(String key, Object value) {

        return redisUtil.sHasKey(key, value);
    }

    @Override
    public void delFromSet(String key, Object value) {

        redisUtil.setRemove(key, value);
    }

    @Override
    public Long incr(String key, long delta) {
        return redisUtil.incr(key, delta);
    }

    @Override
    public Map<Object, Object> getAllFromHashByKey(String key) {
        return redisUtil.hmget(key);
    }
}
