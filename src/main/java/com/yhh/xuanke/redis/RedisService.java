package com.yhh.xuanke.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    Boolean set(String prefix, String key, Object value, Integer expireTime, TimeUnit timeUnit);

    Object get(String prefix, String key);

    Boolean setToHash(String key, String hashKey, Object hashValue, Integer expireTime, TimeUnit timeUnit);

    //得到一对键值队
    Object getFromHash(String key, String hashKey);

    void delFromHash(String key, String hashKey);

    Boolean hHasKey(String key, String hashKey);

    void del(String... key);

    Long hdecr(String key, String item, long by);

    Boolean hasKey(String key);

    void setToSet(String key, long time, TimeUnit timeUnit, Object value);

    Boolean sHasKey(String key, Object value);

    void delFromSet(String key, Object value);

    Long incr(String key, long delta);

    Map<Object, Object> getAllFromHashByKey(String key);
}
