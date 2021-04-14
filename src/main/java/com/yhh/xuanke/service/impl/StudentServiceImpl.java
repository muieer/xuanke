package com.yhh.xuanke.service.impl;

import com.google.common.base.Preconditions;
import com.yhh.xuanke.entiy.StudentEntity;
import com.yhh.xuanke.repository.StudentRepository;
import com.yhh.xuanke.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Cacheable(cacheNames = "forStudent", key = "#sno", cacheManager = "publicInfo")
    public StudentEntity findStudentById(Integer sno) {

        Optional<StudentEntity> optional = studentRepository.findById(sno);
        Preconditions.checkArgument(optional.isPresent(), "用户不存在！");
        return optional.get();
    }
}
