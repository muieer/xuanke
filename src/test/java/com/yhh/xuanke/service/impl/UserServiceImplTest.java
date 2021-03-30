package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void findUser() {
        System.out.println(userService.findUserById(170521));
    }
}