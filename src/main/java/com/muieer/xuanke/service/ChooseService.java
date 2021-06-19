package com.muieer.xuanke.service;

import com.muieer.xuanke.dto.ExposerDTO;
import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.dto.ResultDTO;
import com.muieer.xuanke.entiy.PlanEntity;


public interface ChooseService {

//    List<PlanEntity> getPlanEntityList();

//    ListDTO<PlanEntity> getPlanEntityListDTO(Integer pageNum, Integer size);

    ListDTO<PlanEntity> getPlanEntityListPage(Integer pageNum, Integer size);

    ResultDTO<String> doChoose(Integer pno, String md5);

    void executeChoose(Integer sno, Integer pno);

    ExposerDTO exposer(Integer pno);
}
