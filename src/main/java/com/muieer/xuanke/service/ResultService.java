package com.muieer.xuanke.service;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.dto.ResultDTO;
import com.muieer.xuanke.entiy.ResultEntity;


public interface ResultService {

//    List<ResultEntity> getResultListBySno(Integer sno);

//    ListDTO<ResultEntity> getResultListBySno(Integer pageNum, Integer size, Integer sno);

    ListDTO<ResultEntity> getResultListPageBySno(Integer pageNum, Integer size, Integer sno);

    ResultDTO<String> noChoose(Integer pno);

    ResultEntity findResultEntityByPnoAndSno(Integer pno, Integer sno);
}
