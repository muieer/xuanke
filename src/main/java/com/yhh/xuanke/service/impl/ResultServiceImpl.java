package com.yhh.xuanke.service.impl;

import com.google.common.base.Preconditions;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.repository.ResultRepository;
import com.yhh.xuanke.service.ResultService;
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
import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private PlanRepository planRepository;

    /*@Override
    public List<ResultEntity> getResultListBySno(Integer sno) {

        return resultRepository.findAllBySno(sno);
    }*/

    @Override
    public Page<ResultEntity> getResultListBySno(Integer pageNum, Integer size, Integer sno) {

        //分页查询
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by("sno"));
        //jpa复杂查询
        Page<ResultEntity> page = resultRepository.findAll((Specification<ResultEntity>) (root, query, builder)->{
            List<Predicate> predicates = new ArrayList<>();

            //根据学号查询
            if(sno != null){
                predicates.add(builder.equal(root.get("sno"), sno));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void noChoose(Integer pno) {

        ResultEntity resultEntity = resultRepository.findResultEntityByPnoAndSno(pno, StudentIDUtils.getStudentIDFromMap());
        Preconditions.checkArgument(resultEntity!=null, "没有该条选课记录");

        //选课结果表删除掉该条选课记录
        resultRepository.delete(resultEntity);

        //授课计划余量加一
        Integer a = planRepository.increaseNumByPno(resultEntity.getPno());
        Preconditions.checkArgument(a != 0, "余量不能大于容量");
    }
}
