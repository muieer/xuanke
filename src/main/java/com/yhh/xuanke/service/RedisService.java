package com.yhh.xuanke.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    Boolean set(String prefix, String key, Object value, Integer expireTime, TimeUnit timeUnit);

    Object get(String prefix, String key);

    Boolean setToHash(String key, String hashKey, Object hashValue, Integer expireTime, TimeUnit timeUnit);

    //得到一对键值队
    Object getFromHash(String key, String hashKey);

    void del(String... key);
}
