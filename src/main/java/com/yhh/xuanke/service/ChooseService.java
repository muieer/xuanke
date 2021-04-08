package com.yhh.xuanke.service;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.entiy.PlanEntity;

import java.util.List;

public interface ChooseService {

//    List<PlanEntity> getPlanEntityList();

    ListDTO<PlanEntity> getPlanEntityListDTO(Integer pageNum, Integer size);

    void doChoose(Integer pno);
}
