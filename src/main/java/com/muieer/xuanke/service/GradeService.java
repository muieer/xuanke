package com.muieer.xuanke.service;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.entiy.GradeEntity;

public interface GradeService {

    ListDTO<GradeEntity> getGradeEntityListPage(Integer pageNum, Integer size);
}
