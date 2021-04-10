package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.entiy.ClazzEntity;
import com.yhh.xuanke.entiy.PlanEntity;
import com.yhh.xuanke.repository.ClazzRepository;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.service.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzServiceImpl.class);

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private PlanRepository planRepository;

    //得到必修课课程
    @Override
    public Page<ClazzEntity> getClazzEntityListPage(Integer pageNum, Integer size) {

        Pageable pageable = PageRequest.of(pageNum,size);

        Page<ClazzEntity> page = clazzRepository.findAll(pageable);

        return page;
    }

    //得到该必修课对应的授课计划
    @Override
    public Page<PlanEntity> getClazzOfPlanEntityPage(String cno, Integer pageNum, Integer size) {

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

        return page;
    }
}
