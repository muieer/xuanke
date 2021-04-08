package com.yhh.xuanke.service;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.entiy.ResultEntity;

import java.util.List;

public interface ResultService {

//    List<ResultEntity> getResultListBySno(Integer sno);

    ListDTO<ResultEntity> getResultListBySno(Integer pageNum, Integer size, Integer sno);

    void noChoose(Integer rno);
}
