package com.yhh.xuanke.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResultRepositoryTest {

    @Autowired
    private ResultRepository repository;


    @Test
    void findResultEntityByPnoAndSno() {
        System.out.println(repository.findResultEntityByPnoAndSno(1,17052135));
    }
}