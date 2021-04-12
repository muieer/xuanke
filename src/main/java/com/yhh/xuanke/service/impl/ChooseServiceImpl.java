package com.yhh.xuanke.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.base.Preconditions;
import com.yhh.xuanke.common.CodeMsg;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.PlanEntity;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.exception.GlobalException;
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
import java.util.Optional;

@Service
public class ChooseServiceImpl implements ChooseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseServiceImpl.class);

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ResultRepository resultRepository;

    //本地缓存
    @Autowired
    private Cache<Integer, Boolean> caffeineCache;

    /*@Override
    public List<PlanEntity> getPlanEntityList() {



        List<PlanEntity> planEntities = planRepository.findAll();
        return planEntities;
    }*/


    //实现分页逻辑
    @Override
    public Page<PlanEntity> getPlanEntityListPage(Integer pageNum, Integer size) {

        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("pno"));

        //复杂条件查询,只查询余量不为0的课程
        Page<PlanEntity> page = planRepository.findAll((Specification<PlanEntity>)(root, query, builder)->{
            List<Predicate> predicates = new ArrayList<>();

            //余量需要大于0
//todo            predicates.add(builder.greaterThan(root.get("num"), 0));

            //只筛选选修课,代码大于0
            predicates.add(builder.greaterThan(root.get("naturecode"),0));

            return builder.and(predicates.toArray(new Predicate[0]));

        }, pageable);

        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO<String> doChoose(Integer pno) {

        // 1.本地标记，判断是否有余量
        Boolean over = caffeineCache.get(pno, (k)->{
            Boolean flag = caffeineCache.getIfPresent(k);
            if(flag == null) return false;
            return flag;
        });

        LOGGER.info("是否本地缓存中读取：{}", over);
        //无余量直接返回
        if(over){
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }


        //todo 待解决上课时间冲突问题

        Integer sno = StudentIDUtils.getStudentIDFromMap();
        //先读取此节课的选课结果
        ResultEntity entity = resultRepository.findResultEntityByPnoAndSno(pno, sno);
        if(entity != null){
            throw new GlobalException(CodeMsg.CHOOSE_REPEAT);
        }
//        Preconditions.checkArgument(entity==null, "重复选课！");

        //没选课的话会得到null，需new，否则会发生空指针异常
        entity = new ResultEntity();
        entity.setPno(pno);
        entity.setSno(sno);
        entity.setCreateTime(new Date());
        //如果课程已经存在，再次插入不会成功，变成更新语句,事先判断此课是否已经选择
        resultRepository.saveAndFlush(entity);

        //a大于0，说明更新成功，有余量
        Integer a = planRepository.reduceNumByPno(pno);
        if(a == 0){
            //没余量，写入本地缓存中
            caffeineCache.put(pno, true);
            throw new GlobalException(CodeMsg.PlAN_OVER);
        }

//        Preconditions.checkArgument(a != 0, "此节课已经没有剩余数量可选！");

        return new ResultDTO<String>(CodeMsg.CHOOSE_END.getMsg());
    }

}
