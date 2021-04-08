package com.yhh.xuanke.service.impl;

import com.google.common.base.Preconditions;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.repository.ResultRepository;
import com.yhh.xuanke.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private PlanRepository planRepository;

    @Override
    public List<ResultEntity> getResultListBySno(Integer sno) {

        return resultRepository.findAllBySno(sno);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void noChoose(Integer rno) {

        Optional<ResultEntity> optional = resultRepository.findById(rno);
        Preconditions.checkArgument(optional.isPresent(), "没有该条选课记录");
        ResultEntity resultEntity = optional.get();

        //选课结果表删除掉该条选课记录
        resultRepository.delete(resultEntity);

        //授课计划余量加一
        Integer a = planRepository.increaseNumByPno(resultEntity.getPno());
        Preconditions.checkArgument(a != 0, "余量不能大于容量");
    }
}
