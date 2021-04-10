package com.yhh.xuanke.repository;

import com.yhh.xuanke.entiy.ClazzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClazzRepository extends JpaRepository<ClazzEntity, String> {

}
