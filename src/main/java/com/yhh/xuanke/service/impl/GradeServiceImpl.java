package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.entiy.GradeEntity;
import com.yhh.xuanke.repository.GradeRepository;
import com.yhh.xuanke.service.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl implements GradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeServiceImpl.class);

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public Page<GradeEntity> getGradeEntityListPage(Integer pageNum, Integer size) {

        Pageable pageable = PageRequest.of(pageNum, size);

        Page<GradeEntity> page = gradeRepository.findAll(pageable);

        return page;
    }
}
