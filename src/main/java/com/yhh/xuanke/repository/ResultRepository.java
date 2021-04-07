package com.yhh.xuanke.repository;

import com.yhh.xuanke.entiy.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity,Integer> {

    //根据学号查询选课结果
    List<ResultEntity> findAllBySno(Integer sno);
}
