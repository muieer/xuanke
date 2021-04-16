package com.yhh.xuanke.utils;

import java.util.UUID;

/**
 * 唯一id生成类
 * @author Long
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }
}
