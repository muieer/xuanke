package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.service.ChooseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChooseServiceImplTest {

    @Autowired
    private ChooseService chooseService;

    @Test
    void getPlanEntityList() {
        System.out.println(chooseService.getPlanEntityList());
    }
}