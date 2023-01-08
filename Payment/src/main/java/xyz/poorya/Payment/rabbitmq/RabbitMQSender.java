package xyz.poorya.Payment.rabbitmq;

import xyz.poorya.Payment.dto.TransactionResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey.transaction-response}")
    private String routingKeyTransactionResponse ;
    @Value("${rabbitmq.routingkey.transaction}")
    private String routingKeyTransaction;
    @Autowired
    private MessageConverter messageConverter;

    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());

    public void send(TransactionResponseDto transactionResponseDto) {
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend(exchange , routingKeyTransactionResponse, transactionResponseDto);
        logger.info(routingKeyTransactionResponse);
    }

}