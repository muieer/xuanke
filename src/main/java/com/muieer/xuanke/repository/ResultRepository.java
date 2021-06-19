package com.muieer.xuanke.repository;

import com.muieer.xuanke.entiy.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Integer>, JpaSpecificationExecutor<ResultEntity> {

    //根据学号和授课号查询单条选课结果
    ResultEntity findResultEntityByPnoAndSno(Integer pno, Integer sno);


}
