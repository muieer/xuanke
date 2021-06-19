package com.muieer.xuanke.repository;

import com.muieer.xuanke.entiy.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//实现复杂条件查询必须继承JpaSpecificationExecutor接口
@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Integer>, JpaSpecificationExecutor<PlanEntity> {

    //选课
    @Modifying
    @Query("update PlanEntity p set p.num = p.num-1 where p.pno =?1 and p.num > 0")
    Integer reduceNumByPno(Integer pno);

    //退课
    @Modifying
    @Query("update PlanEntity p set p.num = p.num+1 where p.pno =?1 and p.num < p.capacity")
    Integer increaseNumByPno(Integer pno);

}
