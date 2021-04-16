package com.yhh.xuanke.rabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MQSenderTest {

    @Autowired
    private MQSender mqSender;

    @Test
    void send() {
        mqSender.send(1,1);

    }
}