package com.yhh.xuanke.service;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.entiy.KaoShiEntity;
import org.springframework.data.domain.Page;

public interface KaoShiService {

    ListDTO<KaoShiEntity> getKaoShiEntityListPage(Integer pageNum, Integer size);
}
