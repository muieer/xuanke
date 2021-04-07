package com.yhh.xuanke.service;

import com.yhh.xuanke.entiy.PlanEntity;

import java.util.List;

public interface ChooseService {

    List<PlanEntity> getPlanEntityList();

    void doChoose(Integer pno);
}
