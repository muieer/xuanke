package com.yhh.xuanke.service;

import com.yhh.xuanke.entiy.ResultEntity;

import java.util.List;

public interface ResultService {

    List<ResultEntity> getResultListBySno(Integer sno);

    void noChoose(Integer rno);
}
