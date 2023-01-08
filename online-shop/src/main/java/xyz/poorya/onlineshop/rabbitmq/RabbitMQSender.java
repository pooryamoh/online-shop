package xyz.poorya.onlineshop.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.poorya.onlineshop.dto.TransactionDto;
import xyz.poorya.onlineshop.dto.UserQueueDto;

@Service
public class RabbitMQSender {
    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey.user}")
    private String routingKeyUser;
    @Value("${rabbitmq.routingkey.transaction}")
    private String routingKeyTransaction;
    @Autowired
    private MessageConverter messageConverter;

    public void send(UserQueueDto userQueueDto) {
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend(exchange, routingKeyUser, userQueueDto);
        logger.info("Sending Message to the Queue : " + userQueueDto.toString());
    }

    public void send(TransactionDto transactionDto) {
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend(exchange, routingKeyTransaction, transactionDto);
    }
}