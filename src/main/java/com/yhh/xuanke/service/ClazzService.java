package com.yhh.xuanke.service;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.entiy.ClazzEntity;
import com.yhh.xuanke.entiy.PlanEntity;
import org.springframework.data.domain.Page;

public interface ClazzService {

    ListDTO<ClazzEntity> getClazzEntityListPage(Integer pageNum, Integer size);

    ListDTO<PlanEntity> getClazzOfPlanEntityPage(String cno, Integer pageNum, Integer size);
}
