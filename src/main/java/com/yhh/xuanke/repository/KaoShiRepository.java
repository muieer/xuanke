package com.yhh.xuanke.repository;

import com.yhh.xuanke.entiy.KaoShiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaoShiRepository extends JpaRepository<KaoShiEntity, Integer> {

}
