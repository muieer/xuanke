package com.muieer.xuanke.repository;

import com.muieer.xuanke.entiy.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, Integer>, JpaSpecificationExecutor<GradeEntity> {

}
