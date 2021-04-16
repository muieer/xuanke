package com.yhh.xuanke.service;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.PlanEntity;
import org.springframework.data.domain.Page;


public interface ChooseService {

//    List<PlanEntity> getPlanEntityList();

//    ListDTO<PlanEntity> getPlanEntityListDTO(Integer pageNum, Integer size);

    ListDTO<PlanEntity> getPlanEntityListPage(Integer pageNum, Integer size);

    ResultDTO<String> doChoose(Integer pno);

    void executeChoose(Integer sno, Integer pno);
}
