package com.yhh.xuanke.rabbitmq;

import com.yhh.xuanke.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Integer sno, Integer pno){

        String msg = sno+"-"+pno;
        CorrelationData correlationData = new CorrelationData(UUIDUtil.uuid());
        LOGGER.info("生产者发送消息 {}", msg);

        rabbitTemplate.convertAndSend("amq.direct", "choosePlan_routingKey", msg, correlationData);
    }
}
