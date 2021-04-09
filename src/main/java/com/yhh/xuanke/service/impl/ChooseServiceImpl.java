package com.yhh.xuanke.service.impl;

import com.google.common.base.Preconditions;
import com.yhh.xuanke.entiy.PlanEntity;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.repository.ResultRepository;
import com.yhh.xuanke.service.ChooseService;
import com.yhh.xuanke.utils.StudentIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class ChooseServiceImpl implements ChooseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseServiceImpl.class);

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ResultRepository resultRepository;

    /*@Override
    public List<PlanEntity> getPlanEntityList() {



        List<PlanEntity> planEntities = planRepository.findAll();
        return planEntities;
    }*/


    //实现分页逻辑
    @Override
    public Page<PlanEntity> getPlanEntityListDTO(Integer pageNum, Integer size) {
        //
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("pno"));
        //复杂条件查询,只查询余量不为0的课程
        Page<PlanEntity> page = planRepository.findAll((Specification<PlanEntity>)(root, query, builder)->{
            List<Predicate> predicates = new ArrayList<>();

            //余量需要大于0
            predicates.add(builder.greaterThan(root.get("num"), 0));

            return builder.and(predicates.toArray(new Predicate[0]));

        }, pageable);

        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doChoose(Integer pno) {

        //写入选课结果
        ResultEntity resultEntity = new ResultEntity();
        Integer sno = StudentIDUtils.getStudentIDFromMap();
        resultEntity.setPno(pno);
        resultEntity.setSno(sno);

        //todo 添加的时间不是东八区
        resultEntity.setCreateTime(new Date());
        resultRepository.save(resultEntity);

        Integer a = planRepository.reduceNumByPno(pno);
        Preconditions.checkArgument(a != 0, "此节课已经没有剩余数量可选");

        /*//余量减一
        try{
            Integer a = planRepository.reduceNumByPno(pno);

            //这一步已经发生异常了，为啥不回滚
            if(a==0) throw new Exception("减库存失败");
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }*/


    }
}
