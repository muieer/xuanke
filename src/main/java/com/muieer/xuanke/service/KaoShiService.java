package com.muieer.xuanke.service;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.entiy.KaoShiEntity;

public interface KaoShiService {

    ListDTO<KaoShiEntity> getKaoShiEntityListPage(Integer pageNum, Integer size);
}
