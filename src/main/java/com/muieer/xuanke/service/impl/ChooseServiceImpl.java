package com.muieer.xuanke.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.base.Preconditions;
import com.muieer.xuanke.common.CodeMsg;
import com.muieer.xuanke.dto.ExposerDTO;
import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.dto.ResultDTO;
import com.muieer.xuanke.entiy.ChooseTimeEntity;
import com.muieer.xuanke.entiy.PlanEntity;
import com.muieer.xuanke.entiy.ResultEntity;
import com.muieer.xuanke.redis.RedisService;
import com.muieer.xuanke.utils.MD5Util;
import com.muieer.xuanke.utils.StudentIDUtils;
import com.muieer.xuanke.exception.GlobalException;
import com.muieer.xuanke.rabbitmq.MQSender;
import com.muieer.xuanke.repository.ChooseTimeRepository;
import com.muieer.xuanke.repository.PlanRepository;
import com.muieer.xuanke.repository.ResultRepository;
import com.muieer.xuanke.service.ChooseService;
import com.muieer.xuanke.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ChooseServiceImpl implements ChooseService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseServiceImpl.class);

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ResultRepository resultRepository;

    //本地缓存
    @Autowired
    private Cache<Integer, Boolean> caffeineCache;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ChooseTimeRepository chooseTimeRepository;


    //实现分页逻辑
    @Override
    public ListDTO<PlanEntity> getPlanEntityListPage(Integer pageNum, Integer size) {

        ListDTO<PlanEntity> listDTO = (ListDTO<PlanEntity>)redisService.getFromHash("forXuanXiu", String.valueOf(pageNum));
        if(listDTO != null) return listDTO;

        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("pno"));

        //复杂条件查询,只查询余量不为0的课程
        Page<PlanEntity> page = planRepository.findAll((Specification<PlanEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //余量需要大于0
            predicates.add(builder.greaterThan(root.get("num"), 0));

            //只筛选选修课,代码大于0
            predicates.add(builder.greaterThan(root.get("naturecode"), 0));

            return builder.and(predicates.toArray(new Predicate[0]));

        }, pageable);

        ListDTO<PlanEntity> planDto = new ListDTO<>(page.stream().collect(Collectors.toList()), page.getNumber(), size, page.getTotalPages());

        //选修课结果变动快，缓存一分钟，减少数据库压力，缺陷是课程余量显示会存在延迟
        redisService.setToHash("forXuanXiu", String.valueOf(pageNum), planDto, 1, TimeUnit.MINUTES);
        return planDto;
    }

    @Override
    public ExposerDTO exposer(Integer pno) {
        //IP限流
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

        //先判断是否有此课程
        Boolean flag = redisService.hHasKey("forPlanCount", String.valueOf(pno));
        //该课程不存在，
        if(!flag) {
            throw new GlobalException(CodeMsg.NO_PLAN);
        }

        //mysql默认时间不是东八区，需要到数据库修改时区
        Date nowTime = new Date();
//        LOGGER.info("当下时间是{}", nowTime.toString());
        Date startTime = (Date)redisService.get("start", "time");
//        LOGGER.info("开始时间是{}", startTime.toString());
        Date endTime = (Date)redisService.get("end", "time");
//        LOGGER.info("结束时间是{}", endTime.toString());
        if(nowTime.getTime() < startTime.getTime()){
            throw new GlobalException(CodeMsg.LESS_START_TIME);
        }

        if(nowTime.getTime() > endTime.getTime()){
            throw new GlobalException(CodeMsg.TIME_END);
        }

        String md5 = MD5Util.inputPassToFormPass(String.valueOf(pno));

        return new ExposerDTO(true, md5);
    }

    @Override
    public ResultDTO<String> doChoose(Integer pno, String md5) {

        //判断链接是否正确
        if(md5 == null || !md5.equals(MD5Util.inputPassToFormPass(String.valueOf(pno)))){
            throw new GlobalException(CodeMsg.LINK_ERROR);
        }

        // 1.本地标记，判断是否有余量
        Boolean over = caffeineCache.get(pno, (k) -> {
            Boolean flag = caffeineCache.getIfPresent(k);
            if (flag == null) return false;
            return flag;
        });

        //无余量直接返回
        if (over) {
//            LOGGER.info("通过本地标记判断已无余量");
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }

        //2.判断是否重选
        Integer sno = StudentIDUtils.getStudentIDFromMap();
        // 先读取此节课的选课结果
        // 优先从redis中加载
        ResultEntity entity = resultService.findResultEntityByPnoAndSno(pno, sno);
        if (entity != null) {
            throw new GlobalException(CodeMsg.CHOOSE_REPEAT);
        }

        //3.判断是否时间冲突
        Map<Object, Object> all = redisService.getAllFromHashByKey("forResult" + "-" + sno);
        for(Object result: all.values()){
            Integer pnoFrom = ((ResultEntity) result).getPno();
            if(isTimeRepeat(pno, pnoFrom)){
                throw new GlobalException(CodeMsg.TIME_HIT);
            }
        }

        //4.库存预减
        //如果key不存在，会写入并返回-1,需先判断
        if(!redisService.hasKey("forPlanCount")){
            try {
                //重新加载，防止键过期读错数据
                afterPropertiesSet();
                LOGGER.info("redis缓存课程余量数据过期，重新加载");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Long num = redisService.hdecr("forPlanCount", String.valueOf(pno), 1);
//        LOGGER.info("redis中读取pno={} 的授课计划余量为{}", pno, num);
        if(num < 0){
            //没余量，写入本地缓存中
            caffeineCache.put(pno, true);
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }

        //5。
        //选课请求发送给mq
        mqSender.send(sno, pno);

        //6。返回结果
        return new ResultDTO<>(CodeMsg.CHOOSE_END.getMsg());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeChoose(Integer sno, Integer pno) {

        ResultEntity entity = resultService.findResultEntityByPnoAndSno(pno, sno);
        Preconditions.checkArgument(entity==null, "重复选课！");

        //保存选课结果
        entity = new ResultEntity();
        entity.setPno(pno);
        entity.setSno(sno);
        entity.setCreateTime(new Date());
        resultRepository.saveAndFlush(entity);

        //余量减一
        Integer a = planRepository.reduceNumByPno(pno);
        if(a ==0) caffeineCache.put(pno, true);
        Preconditions.checkArgument(a != 0, "此节课已经没有剩余数量可选！");

        //该条选课结果写入redis中
        redisService.setToHash("forResult" + "-" + sno, String.valueOf(pno), entity, 30,
                TimeUnit.MINUTES);
        //选课结果发生变化，删掉redis中旧数据
        redisService.del("forResultList::" + sno);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        //系统启动时就将授课计划对应的余量加载进redis中
        List<PlanEntity> list = planRepository.findAll();

        for(PlanEntity entity: list){
            //授课计划余量
            redisService.setToHash("forPlanCount", String.valueOf(entity.getPno()),
                    entity.getNum(), 1, TimeUnit.DAYS);

            //授课计划上课时间
            redisService.setToHash("forPlanTime", String.valueOf(entity.getPno()),
                    entity.getStudytime(), 1, TimeUnit.DAYS);
        }

        //将选课开启结束时间主动加载进redis
        Optional<ChooseTimeEntity> optional = chooseTimeRepository.findById(1);
        Preconditions.checkArgument(optional.isPresent(), "数据库加载错误");
        ChooseTimeEntity entity = optional.get();
        redisService.set("start", "time", entity.getStartTime(), 1, TimeUnit.DAYS);
        redisService.set("end", "time", entity.getEndTime(), 1, TimeUnit.DAYS);


        //系统启动，将预选结果加载
        List<ResultEntity> resultEntities = resultRepository.findAll();
        for(ResultEntity result: resultEntities){
            redisService.setToHash("forResult" + "-" +result.getSno(),
                    "" + result.getPno(), result, 1, TimeUnit.DAYS);
        }
    }

    //判断上课时间是否冲突
    private boolean isTimeRepeat(Integer pnoTarget, Integer pnoFrom){
        String targetTime = (String) redisService.getFromHash("forPlanTime", String.valueOf(pnoTarget));
        String formTime = (String) redisService.getFromHash("forPlanTime", String.valueOf(pnoFrom));

        String[] splitT = targetTime.split(",");
        String[] splitF = formTime.split(",");

        String tWeek = splitT[0];
        String fWeek = splitF[0];
        if(!tWeek.equals(fWeek)) return false;

        String[] split = splitT[1].split("-");
        Integer startT = Integer.valueOf(split[0]);
        Integer endT = Integer.valueOf(split[1]);

        String[] split1 = splitF[1].split("-");
        Integer startF = Integer.valueOf(split1[0]);
        Integer endF = Integer.valueOf(split1[1]);

        if(startT >= startF && startT <= endF) return true;
        return endT >= startF && endT <= endF;
    }
}
