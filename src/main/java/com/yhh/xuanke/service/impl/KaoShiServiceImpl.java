package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.entiy.KaoShiEntity;
import com.yhh.xuanke.repository.KaoShiRepository;
import com.yhh.xuanke.service.KaoShiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class KaoShiServiceImpl implements KaoShiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KaoShiServiceImpl.class);

    @Autowired
    private KaoShiRepository kaoShiRepository;

    @Override
    public Page<KaoShiEntity> getKaoShiEntityListPage(Integer pageNum, Integer size) {

        Pageable pageable = PageRequest.of(pageNum, size);

        Page<KaoShiEntity> page = kaoShiRepository.findAll(pageable);

        return page;
    }
}
