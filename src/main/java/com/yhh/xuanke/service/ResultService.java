package com.yhh.xuanke.service;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.ResultEntity;


public interface ResultService {

//    List<ResultEntity> getResultListBySno(Integer sno);

//    ListDTO<ResultEntity> getResultListBySno(Integer pageNum, Integer size, Integer sno);

    ListDTO<ResultEntity> getResultListPageBySno(Integer pageNum, Integer size, Integer sno);

    ResultDTO<String> noChoose(Integer pno);

    ResultEntity findResultEntityByPnoAndSno(Integer pno, Integer sno);
}
