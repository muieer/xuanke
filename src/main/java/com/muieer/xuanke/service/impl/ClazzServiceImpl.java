package com.muieer.xuanke.service.impl;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.entiy.ClazzEntity;
import com.muieer.xuanke.entiy.PlanEntity;
import com.muieer.xuanke.redis.RedisService;
import com.muieer.xuanke.repository.ClazzRepository;
import com.muieer.xuanke.repository.PlanRepository;
import com.muieer.xuanke.service.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ClazzServiceImpl implements ClazzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzServiceImpl.class);

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RedisService redisService;

    //得到必修课课程列表
    @Override
    @Cacheable(cacheNames = "forClazz", key = "#pageNum", sync = true, cacheManager = "publicInfo")  //名称就是key，必需指定
    public ListDTO<ClazzEntity> getClazzEntityListPage(Integer pageNum, Integer size) {

        Pageable pageable = PageRequest.of(pageNum,size);

        Page<ClazzEntity> page = clazzRepository.findAll(pageable);

//        LOGGER.info("必修课课程数据从数据库加载");

        return new ListDTO<ClazzEntity>(page.stream().collect(Collectors.toList()), pageNum, size, page.getTotalPages());
    }

    //得到该必修课对应的授课计划
    @Override
    public ListDTO<PlanEntity> getClazzOfPlanEntityPage(String cno, Integer pageNum, Integer size) {

        ListDTO<PlanEntity> listDTO = (ListDTO<PlanEntity>) redisService.getFromHash("forMajor", cno+"-"+pageNum);
        if(listDTO!=null) return listDTO;

        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("pno"));

        //复杂条件查询,只查询余量不为0的课程
        Page<PlanEntity> page = planRepository.findAll((Specification<PlanEntity>)(root, query, builder)->{
            List<Predicate> predicates = new ArrayList<>();

            //得到该课程对应的授课计划
            predicates.add(builder.equal(root.get("cno"), cno));

            //余量需要大于0
            predicates.add(builder.greaterThan(root.get("num"), 0));


            return builder.and(predicates.toArray(new Predicate[0]));

        }, pageable);

        listDTO = new ListDTO<PlanEntity>(page.stream().collect(Collectors.toList()), pageNum, size, page.getTotalPages());
        redisService.setToHash("forMajor", cno+"-"+pageNum, listDTO, 1, TimeUnit.MINUTES);

        return listDTO;

    }
}
