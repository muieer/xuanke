package com.muieer.xuanke.utils;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StudentIDUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentIDUtils.class);

    /*private static ThreadLocal<Integer> stuIDHolder = new ThreadLocal<>();

    public static void setStudentID(Integer sno){

        //日志显示可以成功添加，但是取不到
        stuIDHolder.set(sno);
        LOGGER.info("ThreadLocal添加学生学号 {}", sno);
    }

    public static Integer getStudentID(){
        //日志显示即使是同一用户，后台也会开多个线程，所以当线程发生变化会存在取不到数据的情况
        return stuIDHolder.get();
    }

    public static void main(String[] args) {
        setStudentID(123);
        System.out.println(getStudentID());
    }*/


    /*
     * sessionID会在第一次请求的时候写入到cookie中，键值为JESESSIONID
     * */


    //存在多个学生同时选课，预先定义大小，以免扩容的时候带来性能开销
    private static final Map<String, Integer> map = new HashMap<>(1000);

    //subject有多个,对应多个用户
    public static void addStudentIDToMap(Integer sno) {

        //日志证明每次拿到的subject都不同，此法可用
        Subject subject = SecurityUtils.getSubject();
        String id = (String) subject.getSession().getId();
//        LOGGER.info("根据sessionID {} 将学号存入map", id);
        map.put(id, sno);
    }

    public static Integer getStudentIDFromMap() {
        Subject subject = SecurityUtils.getSubject();
        String id = (String) subject.getSession().getId();
//        LOGGER.info("根据sessionID {} 从map中获得学号", id);
        return map.get(id);
    }

    //退出的时候将数据从map中删除，节省内存
    public static void removeUserIdFromMap() {
        Subject subject = SecurityUtils.getSubject();
        String id = (String) subject.getSession().getId();
        map.remove(id);
    }

}
