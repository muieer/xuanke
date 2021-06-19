package com.muieer.xuanke.service;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.entiy.ClazzEntity;
import com.muieer.xuanke.entiy.PlanEntity;

public interface ClazzService {

    ListDTO<ClazzEntity> getClazzEntityListPage(Integer pageNum, Integer size);

    ListDTO<PlanEntity> getClazzOfPlanEntityPage(String cno, Integer pageNum, Integer size);
}
