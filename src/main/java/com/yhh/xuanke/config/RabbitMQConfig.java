package com.yhh.xuanke.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate() {

        rabbitTemplate.setEncoding("UTF-8");

        //开启returncallback yml 需要配置publisher-returns
        rabbitTemplate.setReturnCallback((message,  replyCode, replyText, exchange, routingKey)->{
            String correlationId = message.getMessageProperties().getCorrelationId();
            LOGGER.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}",
                    correlationId, replyCode, replyText, exchange, routingKey);
        });

        //开启消息确认 yml 需要配置 publisher-confirm
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
            if (ack) {
                LOGGER.info("消息发送到交换机成功,correlationId:{}", correlationData.getId());
            } else {
                LOGGER.info("消息发送到交换机失败,原因:{}", cause);
            }
        });

        return rabbitTemplate;
    }

    @Bean("chooseExchange")
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("amq.direct").durable(true).build();
    }

    @Bean("choosePlan")
    public Queue directQueue(){
        return new Queue("choosePlan", true, true, true);
    }

    @Bean
    public Binding directBinding(@Qualifier("choosePlan")Queue queue, @Qualifier("chooseExchange")Exchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("choosePlan_routingKey").noargs();
    }
}
