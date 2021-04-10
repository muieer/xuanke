package com.yhh.xuanke.service;

import com.yhh.xuanke.entiy.PlanEntity;
import org.springframework.data.domain.Page;


public interface ChooseService {

//    List<PlanEntity> getPlanEntityList();

//    ListDTO<PlanEntity> getPlanEntityListDTO(Integer pageNum, Integer size);

    Page<PlanEntity> getPlanEntityListPage(Integer pageNum, Integer size);

    void doChoose(Integer pno);
}
