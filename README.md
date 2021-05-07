# 基于SpringBoot的高并发选课系统

## 项目简介

本项目主要解决在高校选课场景下，保证选课系统在大量读写压力下不宕机，以及选课时尽可能提高选课QPS，给学生一个良好的选课体验，完成上述功能同时保证选课安全



## 运行效果图

![登陆](https://gitee.com/muieer/xuanke/raw/temp/image/%E6%88%AA%E5%B1%8F2021-04-27%2022.33.59.png)

![首页](https://gitee.com/muieer/xuanke/raw/temp/image/%E6%88%AA%E5%B1%8F2021-04-27%2022.35.09.png)

![列表](https://gitee.com/muieer/xuanke/raw/temp/image/%E6%88%AA%E5%B1%8F2021-04-27%2022.35.58.png)

![选课](https://gitee.com/muieer/xuanke/raw/temp/image/%E6%88%AA%E5%B1%8F2021-04-27%2022.45.02.png)

![退课](https://gitee.com/muieer/xuanke/raw/temp/image/%E6%88%AA%E5%B1%8F2021-04-27%2022.45.17.png)

其他效果图请到image文件夹中查看



## 技术选型

前端：Bootstrap、JQuery、Thymeleaf

后端：SpringBoot、Shiro、JPA、Caffeine

中间件：Redis、RabbitMQ、Druid

数据库：MySQL



## 优化思路

### 页面查看

解决思路：从Redis缓存中查看数据，减少数据库访问

从数据可见性角度来讲，分为对所有人可见的公有信息，和只有自己可见的私有信息

#### 公有信息

 **不变动：** 必修课课程表中的数据是不变动，一般展现的是该专业学生本学期所必选的的课程信息，这类信息不发生变动，缓存到Redis中，缓存时间为一天

 **变动：** 

1. 必修课课程一般对应好几个授课计划，以高数来讲，这个课可能会对应好几个授课教师以及好几个时段的授课计划，但这类必修课授课计划学校一般会为学生预选，只有少部分学生因不满意授课时段或授课老师会去退课并重新选课，所以这类数据变动频率小，Redis缓存一分钟
2. 选修课授课计划一般是一个课程对应一个授课计划，因为是选修课，所以学校不为学生预选，需要学生自己选，所以这块并发压力大，数据变化频繁，Redis缓存一分钟

 **_缺点：_** 学生看到的课程余量存在延迟，可能已经选完了，但列表上还显示有课，因此会在学生第一次进入选课页面提醒学生数据有延迟

 **_特有优化：_** 数据库在加载授课计划信息时，只会加载有余量的课程

#### 私有信息

 **不变动：** 考试查询和成绩查询，这类数据不会发生变动，学生看个几次就不会再看了，Redis缓存30分钟

 **变动：** 选课结果，在选课初期学生可能频繁查看选课结果，Redis缓存30分钟

 **_特有优化：_** 选课结果按时间降序排序，优先展示最新选的课程

### 登陆

1. 学生可能多次登陆系统，第一次登陆时将学生信息加载进Redis，减少后续登陆时对数据库的访问
2. 通过单例模式构建全局唯一类，根据sessionID保存学生学号，供后续使用

### 选课

 **_此功能为整个系统的重点优化之处，_** 主要分为两大步骤，选课安全验证和执行选课操作

#### **选课安全验证**

1. IP限流，每分钟可以访问三次，与学号绑定，通过Redis实现

```java
Integer sno = StudentIDUtils.getStudentIDFromMap();
        Integer count = (Integer) redisService.get("ip-", String.valueOf(sno));
        if (count == null) {
            //一分钟内可以访问三次
            redisService.set("ip-", String.valueOf(sno), 1, 1, TimeUnit.MINUTES);
        }else if (count < 3){
            redisService.incr("ip-"+sno, 1);
        }else {
            throw new GlobalException(CodeMsg.COUNT_OVER);
        }
```

2. 判断是否存在该课程，从Redis中查看
3. 判断是否在规定选课时限范围内，从Redis中查看
4. 若满足上述步骤，根据课程号生成其md5值，暴露秒杀地址
5. 执行选课操作时验证秒杀地址是否正确

#### 执行选课操作

1. 通过本地标记判断是否有余量，若有，执行后续
2. 判断是否重选，通过查看Redis是否有对应缓存来实现
3. 判断上课时间是否冲突，构造冲突判断算法，遍历Redis中已选课程进行验证
4. 库存预减，当库存为0时，将该授课计划添加到本地缓存中，本地缓存通过Caffeine构建

```java
Long num = redisService.hdecr("forPlanCount", String.valueOf(pno), 1);
//        LOGGER.info("redis中读取pno={} 的授课计划余量为{}", pno, num);
        if(num < 0){
            //没余量，写入本地缓存中
            caffeineCache.put(pno, true);
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }
```

5. 选课请求压入MQ，异步执行，流量削峰，消费端消费选课信息，将结果写入数据库，结果写入数据库这一步采用事务机制，先插入结果，后减余量，减少事务期间锁持有时间，优化数据库读写性能

6. 返回执行成功标识，但结果需要到选课查询页面确认

#### 预加载

```java
@Service
public class ChooseServiceImpl implements ChooseService, InitializingBean {
  @Override
    public void afterPropertiesSet() throws Exception {
      //预加载相关操作
    }
}
```

通过上述代码所示，Spring在生成Bean初期，会将选课所需要的相关数据加载到Redis中

#### 超选

```java
//选课
    @Modifying
    @Query("update PlanEntity p set p.num = p.num-1 where p.pno =?1 and p.num > 0")
    Integer reduceNumByPno(Integer pno);
```

### 退课

1. 到Redis中查看是否存在该条选课记录
2. 删除该条选课记录，对应授课计划余量加一（事务，减少锁持有时间）
3. 修改Redis中对应数据，余量加一，删除掉该条选课记录
4. 返回退课成功标识

### 其他优化点

 **安全相关：** 存储在数据库中的密码和选课链接都经过了MD5加密，对应MD5值的生成经过了两次加盐

 **数据库操作：** 引入Druid数据库连接池，提升对数据库的操作性能



## 压力测试

 测试内容：选课QPS

 测试技术：Jmeter

 测试计划：理想状态下（余量充足，不存在重复选课，上课时间不冲突等），同时执行5000个线程（模仿5000个同学），选同一节课，看选课执行情况

 测试方案：

1. 利用mysql存储过程生成5000个学生学号，并将数据导出为cvs格式，供Jmeter使用

```mysql
delimiter //
create procedure createSno ()
begin
    declare i int;
    set i = 1;
    while i <= 5000 do
        insert into testData values(i);
        set i = i + 1;
    end while;
end //
delimiter ;

call createSno();
```

2. 使用Jmeter图形化界面生成测试方案，验证测试方案可行性

![gui test](https://gitee.com/muieer/xuanke/raw/temp/image/jmeter/%E6%88%AA%E5%B1%8F2021-05-07%2020.13.32.png)

3. 使用Jmeter命令行压测（图形化界面仅用来做验证，并不适合高负载压测），执行5次，查看结果

```
jmeter -n -t [xuanke.jmx,测试文件] -l [xuanke.txt,结果输出] -e -o [/test,web输出]
```

![result](https://gitee.com/muieer/xuanke/raw/temp/image/jmeter/%E6%88%AA%E5%B1%8F2021-05-07%2020.09.01.png)

### 未优化前压测结果

QPS最大为：835，执行成功率为100%，但是在连续多组测试过程中，会出现执行失败情况，第五组压测失败率高达71.66%，失败结果多与tcp传输相关

![failure](https://gitee.com/muieer/xuanke/raw/temp/image/jmeter/%E6%88%AA%E5%B1%8F2021-05-07%2020.04.19.png)

### 优化后压测结果



**补充：**为方便压测，对原先代码有做修改，以方便测试



