package com.yhh.xuanke.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.base.Preconditions;
import com.yhh.xuanke.common.CodeMsg;
import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.PlanEntity;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.exception.GlobalException;
import com.yhh.xuanke.rabbitmq.MQSender;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.repository.ResultRepository;
import com.yhh.xuanke.service.ChooseService;
import com.yhh.xuanke.redis.RedisService;
import com.yhh.xuanke.service.ResultService;
import com.yhh.xuanke.utils.StudentIDUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


    //实现分页逻辑
    @Override
    public ListDTO<PlanEntity> getPlanEntityListPage(Integer pageNum, Integer size) {

        //
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
    public ResultDTO<String> doChoose(Integer pno) {

        // 1.本地标记，判断是否有余量
        Boolean over = caffeineCache.get(pno, (k) -> {
            Boolean flag = caffeineCache.getIfPresent(k);
            if (flag == null) return false;
            return flag;
        });

        //无余量直接返回
        if (over) {
            LOGGER.info("通过本地标记判断已无余量");
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }

        //todo 待解决上课时间冲突问题
        Integer sno = StudentIDUtils.getStudentIDFromMap();
        // 先读取此节课的选课结果
        // 优先从redis中加载
        ResultEntity entity = resultService.findResultEntityByPnoAndSno(pno, sno);
        if (entity != null) {
            throw new GlobalException(CodeMsg.CHOOSE_REPEAT);
        }

        //如果key不存在，会写入并返回-1,需先判断
        if(!redisService.hasKey("forPlan")){
            try {
                //重新加载，防止键过期读错数据
                afterPropertiesSet();
                LOGGER.info("redis缓存课程余量数据过期，重新加载");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Long num = redisService.hdecr("forPlan", String.valueOf(pno), 1);
        LOGGER.info("redis中读取pno={} 的授课计划余量为{}", pno, num);
        if(num < 0){
            //没余量，写入本地缓存中
            caffeineCache.put(pno, true);
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }

        //选课请求发送给mq
        mqSender.send(sno, pno);

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
        redisService.setToHash("forResult", sno+"-"+pno, entity, 30, TimeUnit.MINUTES);
        //选课结果发生变化，删掉redis中旧数据
        redisService.del("forResultList::" + sno);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        //系统启动时就将授课计划对应的余量加载进redis中
        List<PlanEntity> list = planRepository.findAll();

        for(PlanEntity entity: list){
            redisService.setToHash("forPlan", String.valueOf(entity.getPno()),
                    entity.getNum(), 12, TimeUnit.HOURS);
        }

    }

    //判断上课时间是否冲突
   /* private boolean isTimeRepeat(Integer pno, Integer sno){

    }*/
}
