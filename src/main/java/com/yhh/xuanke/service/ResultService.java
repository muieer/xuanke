package com.yhh.xuanke.service;

import com.yhh.xuanke.entiy.ResultEntity;
import org.springframework.data.domain.Page;


public interface ResultService {

//    List<ResultEntity> getResultListBySno(Integer sno);

//    ListDTO<ResultEntity> getResultListBySno(Integer pageNum, Integer size, Integer sno);

    Page<ResultEntity> getResultListBySno(Integer pageNum, Integer size, Integer sno);

    void noChoose(Integer pno);
}
