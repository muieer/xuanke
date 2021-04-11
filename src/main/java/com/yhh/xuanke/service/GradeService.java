package com.yhh.xuanke.service;

import com.yhh.xuanke.entiy.GradeEntity;
import org.springframework.data.domain.Page;

public interface GradeService {

    Page<GradeEntity> getGradeEntityListPage(Integer pageNum, Integer size);
}
