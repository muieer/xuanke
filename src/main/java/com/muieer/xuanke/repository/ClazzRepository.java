package com.muieer.xuanke.repository;

import com.muieer.xuanke.entiy.ClazzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzRepository extends JpaRepository<ClazzEntity, String> {

}
