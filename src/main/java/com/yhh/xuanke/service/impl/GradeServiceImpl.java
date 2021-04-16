package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.entiy.GradeEntity;
import com.yhh.xuanke.redis.RedisService;
import com.yhh.xuanke.repository.GradeRepository;
import com.yhh.xuanke.service.GradeService;
import com.yhh.xuanke.utils.StudentIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeServiceImpl.class);

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private RedisService redisService;

    @Override
//    @Cacheable(cacheNames = "forGrade", key = "#pageNum",  cacheManager = "privateInfo")
    public ListDTO<GradeEntity> getGradeEntityListPage(Integer pageNum, Integer size) {

        Integer sno = StudentIDUtils.getStudentIDFromMap();

        ListDTO<GradeEntity> listDTO = (ListDTO<GradeEntity>) redisService.getFromHash("forGrade", sno+"-"+pageNum);
        if(listDTO != null){
            return listDTO;
        }

        Pageable pageable = PageRequest.of(pageNum, size);

        Page<GradeEntity> page = gradeRepository.findAll((Specification<GradeEntity>) (root, query, builder)->{

            List<Predicate> predicates = new ArrayList<>();

            //查询对应学生的成绩表
            predicates.add(builder.equal(root.get("sno"), StudentIDUtils.getStudentIDFromMap()));

            return builder.and(predicates.toArray(new Predicate[0]));
        },pageable);

        listDTO = new ListDTO<>(page.stream().collect(Collectors.toList()), pageNum, size, page.getTotalPages());
        redisService.setToHash("forGrade", sno+"-"+pageNum, listDTO, 30, TimeUnit.MINUTES);

        return listDTO;
    }
}
